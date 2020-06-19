package com.ody.usbservicelib;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ody.usbservicelib.usbserialdrivers.SerialInputStream;
import com.ody.usbservicelib.usbserialdrivers.SerialPortBuilder;
import com.ody.usbservicelib.usbserialdrivers.SerialPortCallback;
import com.ody.usbservicelib.usbserialdrivers.UsbSerialDevice;
import com.ody.usbservicelib.usbserialdrivers.UsbSerialInterface;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class DeviceService extends Service implements SerialPortCallback {

    public static final String TAG = "UsbService";
    public static boolean SERVICE_CONNECTED = false;

    private IBinder binder = new UsbBinder();

    private Context context;
    private UsbManager usbManager;
    private UsbDevice device;
    private UsbDeviceConnection connection;

    //permission strings
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    public static final String ACTION_USB_PERMISSION_GRANTED = "com.ody.usbservice.USB_PERMISSION_GRANTED";
    public static final String ACTION_USB_PERMISSION_NOT_GRANTED = "com.ody.usbservice.USB_PERMISSION_NOT_GRANTED";
    public static final String ACTION_USB_DISCONNECTED = "com.ody.usbservice.USB_DISCONNECTED";
    public static final String ACTION_USB_CONNECTED = "com.ody.usbservice.USB_CONNECTED";
    public static final String ACTION_USB_NOT_SUPPORTED = "com.ody.usbservice.USB_NOT_SUPPORTED";
    public static final String ACTION_NO_USB = "com.ody.usbservice.NO_USB";

    public static final int SYNC_READ = 3;
    private static final int BAUD_RATE = 9600; // BaudRate. Change this value if you need

    private List<UsbSerialDevice> serialPorts;

    private SerialPortBuilder builder;
    private Handler writeHandler;
    private WriteThread writeThread;

    private ReadThreadCOM readThreadCOM1, readThreadCOM2;

    private Handler mHandler;
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = arg1.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) // User accepted our USB connection. Try to open the device as a serial port
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_GRANTED);

                    //device information
                    if (device != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("device_vendorId", device.getVendorId());
                        bundle.putInt("device_productId", device.getProductId());
                        bundle.putInt("device_Id", device.getDeviceId());
                        bundle.putString("device_Name", device.getDeviceName());
                        intent.putExtras(bundle);
                    }
                    arg0.sendBroadcast(intent);

                    connection = usbManager.openDevice(device);
                } else // User not accepted our USB connection. Send an Intent to the Main Activity
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_NOT_GRANTED);
                    arg0.sendBroadcast(intent);
                }
            } else if (arg1.getAction().equals(ACTION_USB_ATTACHED)) {
                device = null;
                Intent intent = new Intent(ACTION_USB_CONNECTED);
                arg0.sendBroadcast(intent);
            } else if (arg1.getAction().equals(ACTION_USB_DETACHED)) {
                // Usb device was disconnected. send an intent to the Main Activity
                Intent intent = new Intent(ACTION_USB_DISCONNECTED);
                arg0.sendBroadcast(intent);
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onSerialPortsDetected(List<UsbSerialDevice> serialPorts) {
        this.serialPorts = serialPorts;

        if(serialPorts.size() == 0)
            return;

        if (writeThread == null) {
            writeThread = new WriteThread();
            writeThread.start();
        }

        int index = 0;

        if (readThreadCOM1 == null && index <= serialPorts.size()-1
                && serialPorts.get(index).isOpen()) {
            readThreadCOM1 = new ReadThreadCOM(index,
                    serialPorts.get(index).getInputStream());
            readThreadCOM1.start();
        }
    }

    public class UsbBinder extends Binder {
        public DeviceService getService() {
            return DeviceService.this;
        }
    }

    public void destroy() {
        unregisterReceiver(usbReceiver);
        DeviceService.SERVICE_CONNECTED = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        DeviceService.SERVICE_CONNECTED = true;
        setFilter();
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        builder = SerialPortBuilder.createSerialPortBuilder(this);

        boolean ret = builder.openSerialPorts(context, BAUD_RATE,
                UsbSerialInterface.DATA_BITS_8,
                UsbSerialInterface.STOP_BITS_1,
                UsbSerialInterface.PARITY_NONE,
                UsbSerialInterface.FLOW_CONTROL_OFF);

        if(!ret)
            Toast.makeText(context, "No Usb serial ports available", Toast.LENGTH_SHORT).show();
    }

    public void write(byte[] data, int port){
        if(writeThread != null) {
            byte[] clear = new byte[]{0x0C};
            byte[] allByteArray = new byte[clear.length + data.length];
            ByteBuffer buff = ByteBuffer.wrap(allByteArray);
            buff.put(clear);
            buff.put(data);

            writeHandler.obtainMessage(0, port, 0, buff.array()).sendToTarget();
        }
    }

    private void setFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(ACTION_USB_DETACHED);
        filter.addAction(ACTION_USB_ATTACHED);
        registerReceiver(usbReceiver, filter);
    }

    private void requestUserPermission() {
        Log.d(TAG, String.format("requestUserPermission(%X:%X)", device.getVendorId(), device.getProductId()));
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(device, mPendingIntent);
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void connect() {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                if (device.getVendorId() != 2965 && !usbManager.hasPermission(device)) {//sunmi builtin port
                    requestUserPermission();
                } else {
                    device = null;
                }
            }
        }
    }

    public void connect(String deviceID) {
        try {
            int venId = Integer.parseInt(deviceID);
            HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
            if (!usbDevices.isEmpty()) {
                for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                    device = entry.getValue();
                    if (device.getVendorId() == venId) {//sunmi builtin port
                        requestUserPermission();
                        break;
                    } else {
                        device = null;
                    }
                }
            }
        } catch (Exception ignored) {
            //ignored
        }
    }

    private class ReadThreadCOM extends Thread {
        private int port;
        private AtomicBoolean keep = new AtomicBoolean(true);
        private SerialInputStream inputStream;

        public ReadThreadCOM(int port, SerialInputStream serialInputStream){
            this.port = port;
            this.inputStream = serialInputStream;
        }

        @Override
        public void run() {
            while(keep.get()){
                if(inputStream == null)
                    return;
                int value = inputStream.read();
                if(value != -1) {
                    String str = toASCII(value);
                    mHandler.obtainMessage(SYNC_READ, port, 0, str).sendToTarget();
                }
            }
        }

        public void setKeep(boolean keep){
            this.keep.set(keep);
        }
    }

    private static String toASCII(int value) {
        int length = 4;
        StringBuilder builder = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            builder.append((char) ((value >> (8 * i)) & 0xFF));
        }
        return builder.toString();
    }

    private class WriteThread extends Thread{

        @Override
        //@SuppressLint("HandlerLeak")
        public void run() {
            Looper.prepare();
            writeHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //lets break it
                    int requestedDevice = msg.arg1;
                    byte[] data = (byte[]) msg.obj;
                    for (UsbSerialDevice port: serialPorts ) {
                        int vendorID = port.getDeviceVID();
                        String portName = port.getPortName();
                        if (!"".equals(portName) && requestedDevice == vendorID){
                            UsbSerialDevice serialDevice = port;
                            serialDevice.getOutputStream().write(data);
                        }
                    }
                }
            };
            Looper.loop();
        }
    }
}

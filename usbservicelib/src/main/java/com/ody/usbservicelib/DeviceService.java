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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;


public class DeviceService extends Service implements SerialPortCallback {

    public static final String TAG = "UsbService";
    public static boolean SERVICE_CONNECTED = false;

    private IBinder binder = new UsbBinder();

    private Context context;
    private UsbManager usbManager;
    private UsbDevice _device;
    private UsbDeviceConnection connection;

    //permission strings
    private static final String ACTION_USB_PERMISSION = "com.ody.usbservice.USB_PERMISSION";
    public static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DETACHED = "android.hardware.usb.laction.USB_DEVICE_DETACHED";
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
    private ReadThreadCOM readThread;

    private Handler mHandler;
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = arg1.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) // User accepted our USB connection. Try to open the device as a serial port
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_GRANTED);
                    UsbDevice usbDevice = arg1.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    //device information
                    if (usbDevice != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("device_vendorId", usbDevice.getVendorId());
                        bundle.putInt("device_productId", usbDevice.getProductId());
                        bundle.putInt("device_Id", usbDevice.getDeviceId());
                        bundle.putString("device_Name", usbDevice.getDeviceName());
                        intent.putExtras(bundle);
                    }
                    arg0.sendBroadcast(intent);
                } else // User not accepted our USB connection. Send an Intent to the Main Activity
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_NOT_GRANTED);
                    arg0.sendBroadcast(intent);
                }
            } else if (arg1.getAction().equals(ACTION_USB_ATTACHED)) {

                boolean ret = builder.openSerialPorts(context, BAUD_RATE,
                        UsbSerialInterface.DATA_BITS_8,
                        UsbSerialInterface.STOP_BITS_1,
                        UsbSerialInterface.PARITY_NONE,
                        UsbSerialInterface.FLOW_CONTROL_OFF);
                if (!ret)
                    Log.d(TAG, "onReceive: Couldnt open the device");

            } else if (arg1.getAction().equals(ACTION_USB_DETACHED)) {

                UsbDevice usbDevice = arg1.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                boolean ret = builder.disconnectDevice(usbDevice);

                if(ret)
                    Log.d(TAG, "onReceive: sb device disconnected");
                else
                    Log.d(TAG, "onReceive: Usb device wasnt a serial port");

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

        if (serialPorts.size() == 0)
            return;
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

        if (!ret)
            Log.d(TAG, "onCreate: No Usb serial ports available");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (builder != null)
            builder.unregisterListeners(context);
        DeviceService.SERVICE_CONNECTED = false;
    }

    public void write(byte[] data, int port) {
        try{
            if (writeThread == null) {
                writeThread = new WriteThread();
                writeThread.start();
            }
            Thread.sleep(300);
            if (writeThread != null) {
                byte[] clear = new byte[]{0x0C};
                byte[] allByteArray = new byte[clear.length + data.length];
                ByteBuffer buff = ByteBuffer.wrap(allByteArray);
                buff.put(clear);
                buff.put(data);
                writeHandler.obtainMessage(0, port, 0, buff.array()).sendToTarget();
            }
        }catch (Exception ignore){
            //ignore
            Log.e(TAG, "write: caught", ignore);
        }
    }

    public void read(int deviceID){
        if (readThread == null ) {
            for (UsbSerialDevice serialDevice: serialPorts){
                if (serialDevice.getDeviceVID() == deviceID){
                    readThread = new ReadThreadCOM(0,
                            serialDevice.getInputStream());
                    readThread.start();
                    break;
                }
            }
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
        Log.d(TAG, String.format("requestUserPermission(%X:%X)", _device.getVendorId(), _device.getProductId()));
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(_device, mPendingIntent);
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void connect() {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                _device = entry.getValue();
                if (_device.getVendorId() != 2965 && !usbManager.hasPermission(_device)) {//sunmi builtin port
                    requestUserPermission();
                } else {
                    _device = null;
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
                    _device = entry.getValue();
                    if (_device.getVendorId() == venId) {//sunmi builtin port
                        requestUserPermission();
                        break;
                    } else {
                        _device = null;
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

        public ReadThreadCOM(int port, SerialInputStream serialInputStream) {
            this.port = port;
            this.inputStream = serialInputStream;
        }

        @Override
        public void run() {
            while (keep.get()) {
                try {
                    Thread.sleep(200);
                    byte[] rbuf = new byte[40];
                    StringBuffer sbHex = new StringBuffer();
                    if (inputStream == null)
                        return;
                    int value = inputStream.read(rbuf);
                    if (value >= 13) {
                        for (int j = 0; j < value; j++) {
                            sbHex.append((char) (rbuf[j] & 0x000000FF));
                        }
                        final String regex = "([\\d]{3}.[\\d]{3})(?!\\s+.(.).\\1)\\b";
                        Pattern p = Pattern.compile(regex, CASE_INSENSITIVE);
                        Matcher matcher = p.matcher(sbHex.toString());
                        if (matcher.find()) {
                            mHandler.obtainMessage(SYNC_READ, port, 0, matcher.group()).sendToTarget();
                        }
                    }
                } catch (Exception ignore) {
                    Log.e(TAG, "run: caught this one", ignore);
                    //ignore
                }
            }
        }

        public void setKeep(boolean keep) {
            this.keep.set(keep);
        }
    }

    private class WriteThread extends Thread {
        @Override
        //@SuppressLint("HandlerLeak")
        public void run() {
            Looper.prepare();
            writeHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //lets break it
                    int requestedDevice = msg.arg1;
                    byte[] data = (byte[]) msg.obj;
                    if (serialPorts != null) {
                        for (UsbSerialDevice port : serialPorts) {
                            int vendorID = port.getDeviceVID();
                            String portName = port.getPortName();
                            if (!"".equals(portName) && requestedDevice == vendorID) {
                                UsbSerialDevice serialDevice = port;
                                serialDevice.getOutputStream().write(data);
                            }
                        }
                    }
                }
            };
            Looper.loop();
        }
    }
}

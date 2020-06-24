package com.ody.usbservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.usbservicelib.DeviceService;
import com.ody.usbservicelib.usbserialdrivers.UsbSerialDevice;

import java.lang.ref.WeakReference;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static DeviceService usbDeviceService;
    private static MyHandler mHandler;
    Context _context;
    static TextView display;
    int readDeviceId = 1659;
    final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case DeviceService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    //Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    //Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    //Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_CONNECTED: // USB DISCONNECTED
                    //Toast.makeText(context, "USB connected", Toast.LENGTH_SHORT).show();
                    usbDeviceService.connect();
                    break;
            }
        }
    };

    static final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbDeviceService = ((DeviceService.UsbBinder) arg1).getService();
            usbDeviceService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbDeviceService = null;
        }
    };
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;
        setFilters();  // Start listening notifications from UsbService
        startService(DeviceService.class, usbConnection, null);

        editText = (EditText) findViewById(R.id.edt_content);
        display = (TextView) findViewById(R.id.tv_display);
        Button sendButton = (Button) findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                if (usbDeviceService != null) { // if UsbService was correctly binded, Send data
                    usbDeviceService.write(data.getBytes(), 1900);
                    usbDeviceService.read(1659);
                }
            }
        });

        mHandler = new MyHandler(this);
    }

    public void writeSerial(String text){
        if(usbDeviceService != null){
            usbDeviceService.write(text.getBytes(),1900);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DeviceService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(DeviceService.ACTION_NO_USB);
        filter.addAction(DeviceService.ACTION_USB_DISCONNECTED);
        filter.addAction(DeviceService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(DeviceService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!usbDeviceService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(_context, service);
        _context.bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DeviceService.SYNC_READ:
                    String buffer = (String) msg.obj;
                    display.setText(buffer);

                    break;
            }
        }
    }
}

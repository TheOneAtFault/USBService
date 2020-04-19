package com.ody.usbservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.ody.usbservicelib.DeviceService;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static DeviceService usbDeviceService;
    Context _context;
    static final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case DeviceService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case DeviceService.ACTION_USB_CONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB connected", Toast.LENGTH_SHORT).show();
                    usbDeviceService.connect();
                    break;
            }
        }
    };

    static final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbDeviceService = ((DeviceService.UsbBinder) arg1).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbDeviceService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;
        setFilters();  // Start listening notifications from UsbService
        startService(DeviceService.class, usbConnection, null);

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
        filter.addAction(DeviceService.ACTION_USB_DISCONNECTED);
        filter.addAction(DeviceService.ACTION_USB_CONNECTED);
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
}

package com.ody.usbservicelib.usbserialdrivers;

import java.util.List;


public interface SerialPortCallback {
    void onSerialPortsDetected(List<UsbSerialDevice> serialPorts);
}

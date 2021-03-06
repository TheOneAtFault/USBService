package com.ody.usbservicelib.deviceids;

import static com.ody.usbservicelib.deviceids.Helpers.createDevice;
import static com.ody.usbservicelib.deviceids.Helpers.createTable;

public class CP2130Ids
{
    private static final long[] cp2130Devices = createTable(
            createDevice(0x10C4, 0x87a0)
    );

    public static boolean isDeviceSupported(int vendorId, int productId)
    {
        return Helpers.exists(cp2130Devices, vendorId, productId);
    }
}

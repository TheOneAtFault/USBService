package com.ody.usbservicelib.deviceids;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;

import static com.ody.usbservicelib.deviceids.Helpers.createDevice;
import static com.ody.usbservicelib.deviceids.Helpers.createTable;

public class FTDISioIds
{
    private FTDISioIds()
    {

    }

    /* Different products and vendors using FTDI chipsets
     * From current FTDI SIO linux driver:
     * https://github.com/torvalds/linux/blob/164c09978cebebd8b5fc198e9243777dbaecdfa0/drivers/usb/serial/ftdi_sio_ids.h
     * */
    private static final long[] ftdiDevices = createTable(
            createDevice(0x0403, 0x6001),
            createDevice(0x0403, 0x6006),
            createDevice(0x0403, 0x6010),
            createDevice(0x0403, 0x6011),
            createDevice(0x0403, 0x6014),
            createDevice(0x0403, 0x6015),
            createDevice(0x0403, 0x8372),
            createDevice(0x0403, 0xfbfa),
            createDevice(0x0403, 0x6002),
            createDevice(0x0403, 0x9e90),
            createDevice(0x0403, 0x9f80),
            createDevice(0x0403, 0xa6d0),
            createDevice(0x0403, 0xabb8),
            createDevice(0x0403, 0xabb9),
            createDevice(0x0403, 0xb810),
            createDevice(0x0403, 0xb811),
            createDevice(0x0403, 0xb812),
            createDevice(0x0403, 0xbaf8),
            createDevice(0x0403, 0xbcd8),
            createDevice(0x0403, 0xbcd9),
            createDevice(0x0403, 0xbcda),
            createDevice(0x0403, 0xbdc8),
            createDevice(0x0403, 0xbfd8),
            createDevice(0x0403, 0xbfd9),
            createDevice(0x0403, 0xbfda),
            createDevice(0x0403, 0xbfdb),
            createDevice(0x0403, 0xbfdc),
            createDevice(0x0403, 0xbfdd),
            createDevice(0x0403, 0xc1e0),
            createDevice(0x0403, 0xc7d0),
            createDevice(0x0403, 0xc850),
            createDevice(0x0403, 0xc991),
            createDevice(0x0403, 0xcaa0),
            createDevice(0x0403, 0xcc48),
            createDevice(0x0403, 0xcc49),
            createDevice(0x0403, 0xcc4a),
            createDevice(0x0403, 0xcff8),
            createDevice(0x0403, 0xd010),
            createDevice(0x0403, 0xd011),
            createDevice(0x0403, 0xd012),
            createDevice(0x0403, 0xd013),
            createDevice(0x0403, 0xd014),
            createDevice(0x0403, 0xd015),
            createDevice(0x0403, 0xd016),
            createDevice(0x0403, 0xd017),
            createDevice(0x0403, 0xd070),
            createDevice(0x0403, 0xd071),
            createDevice(0x0403, 0xd678),
            createDevice(0x0403, 0xd738),
            createDevice(0x0403, 0xd739),
            createDevice(0x0403, 0xd780),
            createDevice(0x0403, 0xf070),
            createDevice(0x0403, 0xd388),
            createDevice(0x0403, 0xd389),
            createDevice(0x0403, 0xd38a),
            createDevice(0x0403, 0xd38b),
            createDevice(0x0403, 0xd38c),
            createDevice(0x0403, 0xd38d),
            createDevice(0x0403, 0xd38e),
            createDevice(0x0403, 0xd38f),
            createDevice(0x0403, 0xd491),
            createDevice(0x0403, 0xda70),
            createDevice(0x0403, 0xda71),
            createDevice(0x0403, 0xda72),
            createDevice(0x0403, 0xda73),
            createDevice(0x0403, 0xda74),
            createDevice(0x0403, 0xdaf8),
            createDevice(0x0403, 0xdaf9),
            createDevice(0x0403, 0xdafa),
            createDevice(0x0403, 0xdafb),
            createDevice(0x0403, 0xdafc),
            createDevice(0x0403, 0xdafd),
            createDevice(0x0403, 0xdafe),
            createDevice(0x0403, 0xdaff),
            createDevice(0x0403, 0xdc00),
            createDevice(0x0403, 0xdc01),
            createDevice(0x0403, 0xdd20),
            createDevice(0x0403, 0xdf28),
            createDevice(0x0403, 0xdf30),
            createDevice(0x0403, 0xdf32),
            createDevice(0x0403, 0xdf31),
            createDevice(0x0403, 0xdf33),
            createDevice(0x0403, 0xdf35),
            createDevice(0x0403, 0xe050),
            createDevice(0x0403, 0xc006),
            createDevice(0x0403, 0xe000),
            createDevice(0x0403, 0xe001),
            createDevice(0x0403, 0xe002),
            createDevice(0x0403, 0xe004),
            createDevice(0x0403, 0xe006),
            createDevice(0x0403, 0xe008),
            createDevice(0x0403, 0xe009),
            createDevice(0x0403, 0xe00a),
            createDevice(0x0403, 0xe0e8),
            createDevice(0x0403, 0xe0e9),
            createDevice(0x0403, 0xe0ea),
            createDevice(0x0403, 0xe0eb),
            createDevice(0x0403, 0xe0ec),
            createDevice(0x0403, 0xe0ee),
            createDevice(0x0403, 0xe0ef),
            createDevice(0x0403, 0xe0f0),
            createDevice(0x0403, 0xe0f1),
            createDevice(0x0403, 0xe0f2),
            createDevice(0x0403, 0xe0f3),
            createDevice(0x0403, 0xe0f4),
            createDevice(0x0403, 0xe0f5),
            createDevice(0x0403, 0xe0f6),
            createDevice(0x0403, 0xe0f7),
            createDevice(0x0403, 0xe40b),
            createDevice(0x0403, 0xf068),
            createDevice(0x0403, 0xf069),
            createDevice(0x0403, 0xf06a),
            createDevice(0x0403, 0xf06b),
            createDevice(0x0403, 0xf06c),
            createDevice(0x0403, 0xf06d),
            createDevice(0x0403, 0xf06e),
            createDevice(0x0403, 0xf06f),
            createDevice(0x0403, 0xfb58),
            createDevice(0x0403, 0xfb5a),
            createDevice(0x0403, 0xfb5b),
            createDevice(0x0403, 0xfb59),
            createDevice(0x0403, 0xfb5c),
            createDevice(0x0403, 0xfb5d),
            createDevice(0x0403, 0xfb5e),
            createDevice(0x0403, 0xfb5f),
            createDevice(0x0403, 0xe520),
            createDevice(0x0403, 0xe548),
            createDevice(0x0403, 0xe6c8),
            createDevice(0x0403, 0xe700),
            createDevice(0x0403, 0xe808),
            createDevice(0x0403, 0xe809),
            createDevice(0x0403, 0xe80a),
            createDevice(0x0403, 0xe80b),
            createDevice(0x0403, 0xe80c),
            createDevice(0x0403, 0xe80d),
            createDevice(0x0403, 0xe80e),
            createDevice(0x0403, 0xe80f),
            createDevice(0x0403, 0xe888),
            createDevice(0x0403, 0xe889),
            createDevice(0x0403, 0xe88a),
            createDevice(0x0403, 0xe88b),
            createDevice(0x0403, 0xe88c),
            createDevice(0x0403, 0xe88d),
            createDevice(0x0403, 0xe88e),
            createDevice(0x0403, 0xe88f),
            createDevice(0x0403, 0xea90),
            createDevice(0x0403, 0xebe0),
            createDevice(0x0403, 0xec88),
            createDevice(0x0403, 0xec89),
            createDevice(0x0403, 0xed22),
            createDevice(0x0403, 0xed74),
            createDevice(0x0403, 0xed73),
            createDevice(0x0403, 0xed72),
            createDevice(0x0403, 0xed71),
            createDevice(0x0403, 0xee18),
            createDevice(0x0403, 0xeee8),
            createDevice(0x0403, 0xeee9),
            createDevice(0x0403, 0xeeea),
            createDevice(0x0403, 0xeeeb),
            createDevice(0x0403, 0xeeec),
            createDevice(0x0403, 0xeeed),
            createDevice(0x0403, 0xeeee),
            createDevice(0x0403, 0xeeef),
            createDevice(0x0403, 0xef50),
            createDevice(0x0403, 0xef51),
            createDevice(0x0403, 0xf0c0),
            createDevice(0x0403, 0xf0c8),
            createDevice(0x0403, 0xf0e9),
            createDevice(0x0403, 0xf0ee),
            createDevice(0x0403, 0xf208),
            createDevice(0x0403, 0xf2d0),
            createDevice(0x0403, 0xf3c0),
            createDevice(0x0403, 0xf3c1),
            createDevice(0x0403, 0xf3c2),
            createDevice(0x0403, 0xf448),
            createDevice(0x0403, 0xf449),
            createDevice(0x0403, 0xf44a),
            createDevice(0x0403, 0xf44b),
            createDevice(0x0403, 0xf44c),
            createDevice(0x0403, 0xf460),
            createDevice(0x0403, 0xf680),
            createDevice(0x0403, 0xf850),
            createDevice(0x0403, 0xf9d0),
            createDevice(0x0403, 0xf9d1),
            createDevice(0x0403, 0xf9d2),
            createDevice(0x0403, 0xf9d3),
            createDevice(0x0403, 0xf9d4),
            createDevice(0x0403, 0xf9d5),
            createDevice(0x0403, 0xfa00),
            createDevice(0x0403, 0xfa01),
            createDevice(0x0403, 0xfa02),
            createDevice(0x0403, 0xfa03),
            createDevice(0x0403, 0xfa04),
            createDevice(0x0403, 0xfa05),
            createDevice(0x0403, 0xfa06),
            createDevice(0x0403, 0xfa78),
            createDevice(0x0403, 0xfad0),
            createDevice(0x0403, 0xfaf0),
            createDevice(0x0403, 0xfc70),
            createDevice(0x0403, 0xfc71),
            createDevice(0x0403, 0xfc72),
            createDevice(0x0403, 0xfc73),
            createDevice(0x0403, 0xfc82),
            createDevice(0x0403, 0xfc8a),
            createDevice(0x0403, 0xfc8b),
            createDevice(0x0403, 0xfc60),
            createDevice(0x0403, 0xfd60),
            createDevice(0x0403, 0xff20),
            createDevice(0x0403, 0xf857),
            createDevice(0x0403, 0xfa10),
            createDevice(0x0403, 0xfa88),
            createDevice(0x0403, 0xfb99),
            createDevice(0x0403, 0xfc08),
            createDevice(0x0403, 0xfc09),
            createDevice(0x0403, 0xfc0a),
            createDevice(0x0403, 0xfc0b),
            createDevice(0x0403, 0xfc0c),
            createDevice(0x0403, 0xfc0d),
            createDevice(0x0403, 0xfc0e),
            createDevice(0x0403, 0xfc0f),
            createDevice(0x0403, 0xfe38),
            createDevice(0x0403, 0xff00),
            createDevice(0x0403, 0xff38),
            createDevice(0x0403, 0xff39),
            createDevice(0x0403, 0xff3a),
            createDevice(0x0403, 0xff3b),
            createDevice(0x0403, 0xff3c),
            createDevice(0x0403, 0xff3d),
            createDevice(0x0403, 0xff3e),
            createDevice(0x0403, 0xff3f),
            createDevice(0x0403, 0xffa8),
            createDevice(0x0403, 0xfa33),
            createDevice(0x0403, 0x8a98),
            createDevice(0x03eb, 0x2109),
            createDevice(0x0456, 0xf000),
            createDevice(0x0456, 0xf001),
            createDevice(0x0584, 0xb020),
            createDevice(0x0647, 0x0100),
            createDevice(0x06CE, 0x8311),
            createDevice(0x06D3, 0x0284),
            createDevice(0x0856, 0xac01),
            createDevice(0x0856, 0xac02),
            createDevice(0x0856, 0xac03),
            createDevice(0x0856, 0xac11),
            createDevice(0x0856, 0xac12),
            createDevice(0x0856, 0xac16),
            createDevice(0x0856, 0xac17),
            createDevice(0x0856, 0xac18),
            createDevice(0x0856, 0xac19),
            createDevice(0x0856, 0xac25),
            createDevice(0x0856, 0xac26),
            createDevice(0x0856, 0xac27),
            createDevice(0x0856, 0xac33),
            createDevice(0x0856, 0xac34),
            createDevice(0x0856, 0xac49),
            createDevice(0x0856, 0xac50),
            createDevice(0x0856, 0xba02),
            createDevice(0x093c, 0x0601),
            createDevice(0x093c, 0x0701),
            createDevice(0x0acd, 0x0300),
            createDevice(0x0b39, 0x0103),
            createDevice(0x0b39, 0x0421),
            createDevice(0x0c26, 0x0004),
            createDevice(0x0c26, 0x0018),
            createDevice(0x0c26, 0x0009),
            createDevice(0x0c26, 0x000a),
            createDevice(0x0c26, 0x000b),
            createDevice(0x0c26, 0x000c),
            createDevice(0x0c26, 0x000d),
            createDevice(0x0c26, 0x0010),
            createDevice(0x0c26, 0x0011),
            createDevice(0x0c26, 0x0012),
            createDevice(0x0c26, 0x0013),
            createDevice(0x0c33, 0x0010),
            createDevice(0x0c52, 0x2101),
            createDevice(0x0c52, 0x2101),
            createDevice(0x0c52, 0x2102),
            createDevice(0x0c52, 0x2103),
            createDevice(0x0c52, 0x2104),
            createDevice(0x0c52, 0x9020),
            createDevice(0x0c52, 0x2211),
            createDevice(0x0c52, 0x2221),
            createDevice(0x0c52, 0x2212),
            createDevice(0x0c52, 0x2222),
            createDevice(0x0c52, 0x2213),
            createDevice(0x0c52, 0x2223),
            createDevice(0x0c52, 0x2411),
            createDevice(0x0c52, 0x2421),
            createDevice(0x0c52, 0x2431),
            createDevice(0x0c52, 0x2441),
            createDevice(0x0c52, 0x2412),
            createDevice(0x0c52, 0x2422),
            createDevice(0x0c52, 0x2432),
            createDevice(0x0c52, 0x2442),
            createDevice(0x0c52, 0x2413),
            createDevice(0x0c52, 0x2423),
            createDevice(0x0c52, 0x2433),
            createDevice(0x0c52, 0x2443),
            createDevice(0x0c52, 0x2811),
            createDevice(0x0c52, 0x2821),
            createDevice(0x0c52, 0x2831),
            createDevice(0x0c52, 0x2841),
            createDevice(0x0c52, 0x2851),
            createDevice(0x0c52, 0x2861),
            createDevice(0x0c52, 0x2871),
            createDevice(0x0c52, 0x2881),
            createDevice(0x0c52, 0x2812),
            createDevice(0x0c52, 0x2822),
            createDevice(0x0c52, 0x2832),
            createDevice(0x0c52, 0x2842),
            createDevice(0x0c52, 0x2852),
            createDevice(0x0c52, 0x2862),
            createDevice(0x0c52, 0x2872),
            createDevice(0x0c52, 0x2882),
            createDevice(0x0c52, 0x2813),
            createDevice(0x0c52, 0x2823),
            createDevice(0x0c52, 0x2833),
            createDevice(0x0c52, 0x2843),
            createDevice(0x0c52, 0x2853),
            createDevice(0x0c52, 0x2863),
            createDevice(0x0c52, 0x2873),
            createDevice(0x0c52, 0x2883),
            createDevice(0x0c52, 0xa02a),
            createDevice(0x0c52, 0xa02b),
            createDevice(0x0c52, 0xa02c),
            createDevice(0x0c52, 0xa02d),
            createDevice(0x0c6c, 0x04b2),
            createDevice(0x0c7d, 0x0005),
            createDevice(0x0d3a, 0x0300),
            createDevice(0x0d46, 0x2020),
            createDevice(0x0d46, 0x2021),
            createDevice(0x0dcd, 0x0001),
            createDevice(0x0f94, 0x0001),
            createDevice(0x0f94, 0x0005),
            createDevice(0x0fd8, 0x0001),
            createDevice(0x103e, 0x03e8),
            createDevice(0x104d, 0x3000),
            createDevice(0x104d, 0x3002),
            createDevice(0x104d, 0x3006),
            createDevice(0x1209, 0x1002),
            createDevice(0x1209, 0x1006),
            createDevice(0x128d, 0x0001),
            createDevice(0x1342, 0x0202),
            createDevice(0x1457, 0x5118),
            createDevice(0x15ba, 0x0003),
            createDevice(0x15ba, 0x002b),
            createDevice(0x1781, 0x0c30),
            createDevice(0x2100, 0x9001),
            createDevice(0x2100, 0x9e50),
            createDevice(0x2100, 0x9e51),
            createDevice(0x2100, 0x9e52),
            createDevice(0x2100, 0x9e53),
            createDevice(0x2100, 0x9e54),
            createDevice(0x2100, 0x9e55),
            createDevice(0x2100, 0x9e56),
            createDevice(0x2100, 0x9e57),
            createDevice(0x2100, 0x9e58),
            createDevice(0x2100, 0x9e59),
            createDevice(0x2100, 0x9e5a),
            createDevice(0x2100, 0x9e5b),
            createDevice(0x2100, 0x9e5c),
            createDevice(0x2100, 0x9e5d),
            createDevice(0x2100, 0x9e5e),
            createDevice(0x2100, 0x9e5f),
            createDevice(0x2100, 0x9e60),
            createDevice(0x2100, 0x9e61),
            createDevice(0x2100, 0x9e62),
            createDevice(0x2100, 0x9e63),
            createDevice(0x2100, 0x9e64),
            createDevice(0x2100, 0x9e65),
            createDevice(0x2100, 0x9e65),
            createDevice(0x2100, 0x9e66),
            createDevice(0x2100, 0x9e67),
            createDevice(0x2100, 0x9e68),
            createDevice(0x2100, 0x9e69),
            createDevice(0x2100, 0x9e6a),
            createDevice(0x0403, 0xe0a0),
            createDevice(0x0403, 0xe0a1),
            createDevice(0x1a72, 0x1000),
            createDevice(0x1a72, 0x1001),
            createDevice(0x1a72, 0x1002),
            createDevice(0x1a72, 0x1005),
            createDevice(0x1a72, 0x1007),
            createDevice(0x1a72, 0x1008),
            createDevice(0x1a72, 0x1009),
            createDevice(0x1a72, 0x100d),
            createDevice(0x1a72, 0x100e),
            createDevice(0x1a72, 0x100f),
            createDevice(0x1a72, 0x1011),
            createDevice(0x1a72, 0x1012),
            createDevice(0x1a72, 0x1013),
            createDevice(0x1a72, 0x1014),
            createDevice(0x1a72, 0x1015),
            createDevice(0x1a72, 0x1016),
            createDevice(0x165c, 0x0002),
            createDevice(0x1a79, 0x6001),
            createDevice(0x1b3d, 0x0100),
            createDevice(0x1b3d, 0x0101),
            createDevice(0x1b3d, 0x0102),
            createDevice(0x1b3d, 0x0103),
            createDevice(0x1b3d, 0x0104),
            createDevice(0x1b3d, 0x0105),
            createDevice(0x1b3d, 0x0106),
            createDevice(0x1b3d, 0x0107),
            createDevice(0x1b3d, 0x0108),
            createDevice(0x1b3d, 0x0109),
            createDevice(0x1b3d, 0x010a),
            createDevice(0x1b3d, 0x010b),
            createDevice(0x1b3d, 0x010c),
            createDevice(0x1b3d, 0x010d),
            createDevice(0x1b3d, 0x010e),
            createDevice(0x1b3d, 0x010f),
            createDevice(0x1b3d, 0x0110),
            createDevice(0x1b3d, 0x0111),
            createDevice(0x1b3d, 0x0112),
            createDevice(0x1b3d, 0x0113),
            createDevice(0x1b3d, 0x0114),
            createDevice(0x1b3d, 0x0115),
            createDevice(0x1b3d, 0x0116),
            createDevice(0x1b3d, 0x0117),
            createDevice(0x1b3d, 0x0118),
            createDevice(0x1b3d, 0x0119),
            createDevice(0x1b3d, 0x011a),
            createDevice(0x1b3d, 0x011b),
            createDevice(0x1b3d, 0x011c),
            createDevice(0x1b3d, 0x011d),
            createDevice(0x1b3d, 0x011e),
            createDevice(0x1b3d, 0x011f),
            createDevice(0x1b3d, 0x0120),
            createDevice(0x1b3d, 0x0121),
            createDevice(0x1b3d, 0x0122),
            createDevice(0x1b3d, 0x0123),
            createDevice(0x1b3d, 0x0124),
            createDevice(0x1b3d, 0x0125),
            createDevice(0x1b3d, 0x0126),
            createDevice(0x1b3d, 0x0127),
            createDevice(0x1b3d, 0x0128),
            createDevice(0x1b3d, 0x0129),
            createDevice(0x1b3d, 0x012a),
            createDevice(0x1b3d, 0x012b),
            createDevice(0x1b3d, 0x012c),
            createDevice(0x1b3d, 0x012e),
            createDevice(0x1b3d, 0x012f),
            createDevice(0x1b3d, 0x0130),
            //Matrix orbital not all
            createDevice(0x1b91, 0x0064),
            createDevice(0x1bc9, 0x6001),
            createDevice(0x1c0c, 0x0102),
            createDevice(0x1cf1, 0x0001),
            createDevice(0x1cf1, 0x0041),
            createDevice(0x0483, 0x3746),
            createDevice(0x0483, 0x3747),
            createDevice(0x5050, 0x0100),
            createDevice(0x5050, 0x0101),
            createDevice(0x5050, 0x0102),
            createDevice(0x5050, 0x0103),
            createDevice(0x5050, 0x0104),
            createDevice(0x5050, 0x0105),
            createDevice(0x5050, 0x0106),
            createDevice(0x5050, 0x0107),
            createDevice(0x5050, 0x0300),
            createDevice(0x5050, 0x0301),
            createDevice(0x5050, 0x0400),
            createDevice(0x5050, 0x0500),
            createDevice(0x5050, 0x0700),
            createDevice(0x5050, 0x0800),
            createDevice(0x5050, 0x0900),
            createDevice(0x5050, 0x0a00),
            createDevice(0x5050, 0x0b00),
            createDevice(0x5050, 0x0c00),
            createDevice(0x5050, 0x0d00),
            createDevice(0x5050, 0x0e00),
            createDevice(0x5050, 0x0f00),
            createDevice(0x5050, 0x1000),
            createDevice(0x5050, 0x8000),
            createDevice(0x5050, 0x8001),
            createDevice(0x5050, 0x8002),
            createDevice(0x5050, 0x8003),
            createDevice(0x5050, 0x8004),
            createDevice(0x5050, 0x8005),
            createDevice(0x9e88, 0x9e8f),
            createDevice(0xdeee, 0x0300),
            createDevice(0xdeee, 0x02ff),
            createDevice(0xdeee, 0x0302),
            createDevice(0xdeee, 0x0303),
            createDevice(0x0403, 0x9378),
            createDevice(0x0403, 0x0379),
            createDevice(0x0403, 0x937a),
            createDevice(0x0403, 0x937c),
            createDevice(0x0403, 0x9868),
            createDevice(0x0403, 0xbca0),
            createDevice(0x0403, 0xbca1),
            createDevice(0x0403, 0xbca2),
            createDevice(0x0403, 0xbca4),
            createDevice(0x0403, 0xe729),
            createDevice(0x0403, 0xd578),
            createDevice(0x0403, 0xff18),
            createDevice(0x0403, 0xff1c),
            createDevice(0x0403, 0xff1d),
            createDevice(0x0403, 0x20b7),
            createDevice(0x0403, 0x0713),
            createDevice(0x0403, 0xf608),
            createDevice(0x0403, 0xf60b),
            createDevice(0x0403, 0xf7c0),
            createDevice(0x0403, 0x8a28),
            createDevice(0x0403, 0xa951),
            createDevice(0x0403, 0x8e08),
            createDevice(0x0403, 0x0011),
            createDevice(0x0403, 0x87d0),
            createDevice(0x05d1, 0x1001),
            createDevice(0x05d1, 0x1002),
            createDevice(0x05d1, 0x1003),
            createDevice(0x05d1, 0x1004),
            createDevice(0x05d1, 0x1011),
            createDevice(0x05d1, 0x1013),
            createDevice(0x05d1, 0x2001),
            createDevice(0x05d1, 0x2002),
            createDevice(0x05d1, 0x2003),
            createDevice(0x05d1, 0x2011),
            createDevice(0x05d1, 0x2012),
            createDevice(0x05d1, 0x2021),
            createDevice(0x05d1, 0x2022),
            createDevice(0x05d1, 0x2023),
            createDevice(0x05d1, 0x2024),
            createDevice(0x05d1, 0x3011),
            createDevice(0x05d1, 0x3012),
            createDevice(0x05d1, 0x5001),
            createDevice(0x05d1, 0x6001),
            createDevice(0x05d1, 0x7001),
            createDevice(0x05d1, 0x8001),
            createDevice(0x05d1, 0x8002),
            createDevice(0x05d1, 0x8003),
            createDevice(0x05d1, 0x8004),
            createDevice(0x05d1, 0x9001),
            createDevice(0x05d1, 0x9002),
            createDevice(0x05d1, 0x9003),
            createDevice(0x05d1, 0x9004),
            createDevice(0x05d1, 0x9005),
            createDevice(0x05d1, 0x9006),
            createDevice(0x05d1, 0x9007),
            createDevice(0x05d1, 0x9008),
            createDevice(0x0403, 0x0) //fake FTDI reprogrammed by driver
    );

    private static boolean isMicrochipFtdi(UsbDevice dev) {
        if (dev.getVendorId() != 0x04d8 || dev.getProductId() != 0x000a)
            return false;
        for (int i = 0; i < dev.getInterfaceCount(); i++) {
            UsbInterface intf = dev.getInterface(i);
            if (intf.getInterfaceClass() == 0xff && intf.getInterfaceSubclass() == 0xff &&
                    intf.getInterfaceProtocol() == 0x00)
                return true;
        }
        return false;
    }

    public static boolean isDeviceSupported(UsbDevice dev) {
        return Helpers.exists(ftdiDevices, dev.getVendorId(), dev.getProductId()) ||
                isMicrochipFtdi(dev);

    }

    static boolean isDeviceIdSupported(int vendorId, int productId) {
        return Helpers.exists(ftdiDevices, vendorId, productId);
    }
}

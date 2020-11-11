package com.hx.mediaselect.constract;

public class Setting {
    public static boolean isShowCamera = false;
    public static int mostSelectCount = 1;
    public static int dstWidth = -1;
    public static int dstHeight = -1;
    public static String fileProviderAuthority = null;

    public static void clear() {
        isShowCamera = false;
        mostSelectCount = 1;
        fileProviderAuthority = null;
        dstWidth = -1;
        dstHeight = -1;
    }
}

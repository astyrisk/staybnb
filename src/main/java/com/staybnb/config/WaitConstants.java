package com.staybnb.config;

public final class WaitConstants {
    private WaitConstants() {}

    public static final int MEDIUM_WAIT = ConfigProperties.getInt("medium.wait.seconds", 10);

    public static final int MOBILE_WIDTH          = ConfigProperties.getInt("mobile.width", 375);
    public static final int WIDE_DESKTOP_WIDTH    = 1920;
    public static final int WIDE_DESKTOP_HEIGHT   = 1080;
    public static final int MEDIUM_DESKTOP_WIDTH  = 1025;
    public static final int MEDIUM_DESKTOP_HEIGHT = 1080;
    public static final int TABLET_TEST_WIDTH     = 770;
    public static final int TABLET_TEST_HEIGHT    = 1024;
}

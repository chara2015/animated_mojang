package org.lwjgl;

import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/Sys.class */
public class Sys {
    private static final long TIMER_RESOLUTION = 1000000000;
    private static final long TIMER_OFFSET = TimeUtil.getNanoTime();
    private static final String VERSION = Version.getVersion();

    private Sys() {
    }

    public static String getVersion() {
        return VERSION;
    }

    public static long getTimerResolution() {
        return TIMER_RESOLUTION;
    }

    public static long getTime() {
        return (System.nanoTime() - TIMER_OFFSET) & Long.MAX_VALUE;
    }

    public static void initialize() {
    }

    public static boolean openURL(String url) {
        OperatingSystem platform = OperatingSystem.getPlatform();
        platform.openUrl(url);
        return true;
    }
}

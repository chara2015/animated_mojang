package net.labymod.core.main.debug.jvm;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/jvm/Memory.class */
public final class Memory {
    public String getMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long diff = totalMemory - freeMemory;
        return String.format(Locale.ROOT, "% 2d%% %03d/%03dMB", Long.valueOf((diff * 100) / maxMemory), Long.valueOf(bytesToMegabytes(diff)), Long.valueOf(bytesToMegabytes(maxMemory)));
    }

    public String getAllocated() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long percentage = (totalMemory * 100) / maxMemory;
        return String.format(Locale.ROOT, "% 2d%% %03dMB", Long.valueOf(percentage), Long.valueOf(bytesToMegabytes(totalMemory)));
    }

    private long bytesToMegabytes(long value) {
        return (value / 1024) / 1024;
    }
}

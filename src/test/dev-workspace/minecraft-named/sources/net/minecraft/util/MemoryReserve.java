package net.minecraft.util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/MemoryReserve.class */
public class MemoryReserve {
    private static byte[] reserve;

    public static void allocate() {
        reserve = new byte[10485760];
    }

    public static void release() {
        if (reserve != null) {
            reserve = null;
            try {
                System.gc();
                System.gc();
                System.gc();
            } catch (Throwable th) {
            }
        }
    }
}

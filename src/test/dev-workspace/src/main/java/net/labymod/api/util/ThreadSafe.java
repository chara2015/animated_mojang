package net.labymod.api.util;

import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ThreadSafe.class */
public class ThreadSafe {
    public static void executeOnRenderThread(Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }

    public static boolean isNotOnRenderThread() {
        return !isRenderThread();
    }

    public static boolean isRenderThread() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            return true;
        }
        return minecraft.isOnRenderThread();
    }

    public static void ensureRenderThread(BooleanSupplier supplier) {
        if (supplier.getAsBoolean()) {
            ensureRenderThread();
        }
    }

    public static void ensureRenderThread() {
        if (!isRenderThread()) {
            throw new IllegalThreadStateException("Method called from wrong thread, use Laby.labyAPI().minecraft().executeOnRenderThread()");
        }
    }

    public static void ensureThread(BooleanSupplier tester, String method) {
        if (!tester.getAsBoolean()) {
            throw new IllegalThreadStateException("Method called from wrong thread, use " + method);
        }
    }
}

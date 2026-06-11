package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import org.lwjgl.system.Callback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/CallbackUtil.class */
public final class CallbackUtil {
    private CallbackUtil() {
    }

    public static void free(Callback callback) {
        if (callback == null) {
            return;
        }
        callback.free();
    }
}

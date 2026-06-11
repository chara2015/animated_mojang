package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXARBGetProcAddress;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXARBGetProcAddressErrorContext.class */
public final class GLXARBGetProcAddressErrorContext {
    public static long nglXGetProcAddressARB(long p0) {
        long returnType = GLXARBGetProcAddress.nglXGetProcAddressARB(p0);
        LabyDebugContext.glError("nglXGetProcAddressARB", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glXGetProcAddressARB(ByteBuffer p0) {
        long returnType = GLXARBGetProcAddress.glXGetProcAddressARB(p0);
        LabyDebugContext.glError("glXGetProcAddressARB", "p0", p0);
        return returnType;
    }

    public static long glXGetProcAddressARB(CharSequence p0) {
        long returnType = GLXARBGetProcAddress.glXGetProcAddressARB(p0);
        LabyDebugContext.glError("glXGetProcAddressARB", "p0", p0);
        return returnType;
    }
}

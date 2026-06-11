package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLX14;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLX14ErrorContext.class */
public final class GLX14ErrorContext {
    public static long nglXGetProcAddress(long p0) {
        long returnType = GLX14.nglXGetProcAddress(p0);
        LabyDebugContext.glError("nglXGetProcAddress", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glXGetProcAddress(ByteBuffer p0) {
        long returnType = GLX14.glXGetProcAddress(p0);
        LabyDebugContext.glError("glXGetProcAddress", "p0", p0);
        return returnType;
    }

    public static long glXGetProcAddress(CharSequence p0) {
        long returnType = GLX14.glXGetProcAddress(p0);
        LabyDebugContext.glError("glXGetProcAddress", "p0", p0);
        return returnType;
    }
}

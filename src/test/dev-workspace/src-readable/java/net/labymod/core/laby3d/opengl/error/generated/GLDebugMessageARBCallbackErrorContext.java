package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageARBCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLDebugMessageARBCallbackErrorContext.class */
public final class GLDebugMessageARBCallbackErrorContext {
    public static GLDebugMessageARBCallback create(long p0) {
        GLDebugMessageARBCallback returnType = GLDebugMessageARBCallback.create(p0);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageARBCallback createSafe(long p0) {
        GLDebugMessageARBCallback returnType = GLDebugMessageARBCallback.createSafe(p0);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageARBCallback create(GLDebugMessageARBCallbackI p0) {
        GLDebugMessageARBCallback returnType = GLDebugMessageARBCallback.create(p0);
        LabyDebugContext.glError("create", "p0", p0);
        return returnType;
    }

    public static String getMessage(int p0, long p1) {
        String returnType = GLDebugMessageARBCallback.getMessage(p0, p1);
        LabyDebugContext.glError("getMessage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }
}

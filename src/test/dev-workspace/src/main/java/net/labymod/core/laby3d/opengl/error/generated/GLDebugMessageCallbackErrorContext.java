package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLDebugMessageCallbackErrorContext.class */
public final class GLDebugMessageCallbackErrorContext {
    public static GLDebugMessageCallback create(long p0) {
        GLDebugMessageCallback returnType = GLDebugMessageCallback.create(p0);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageCallback createSafe(long p0) {
        GLDebugMessageCallback returnType = GLDebugMessageCallback.createSafe(p0);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageCallback create(GLDebugMessageCallbackI p0) {
        GLDebugMessageCallback returnType = GLDebugMessageCallback.create(p0);
        LabyDebugContext.glError("create", "p0", p0);
        return returnType;
    }

    public static String getMessage(int p0, long p1) {
        String returnType = GLDebugMessageCallback.getMessage(p0, p1);
        LabyDebugContext.glError("getMessage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }
}

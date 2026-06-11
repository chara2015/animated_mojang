package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLDebugMessageAMDCallback;
import org.lwjgl.opengl.GLDebugMessageAMDCallbackI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLDebugMessageAMDCallbackErrorContext.class */
public final class GLDebugMessageAMDCallbackErrorContext {
    public static GLDebugMessageAMDCallback create(long p0) {
        GLDebugMessageAMDCallback returnType = GLDebugMessageAMDCallback.create(p0);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageAMDCallback createSafe(long p0) {
        GLDebugMessageAMDCallback returnType = GLDebugMessageAMDCallback.createSafe(p0);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLDebugMessageAMDCallback create(GLDebugMessageAMDCallbackI p0) {
        GLDebugMessageAMDCallback returnType = GLDebugMessageAMDCallback.create(p0);
        LabyDebugContext.glError("create", "p0", p0);
        return returnType;
    }

    public static String getMessage(int p0, long p1) {
        String returnType = GLDebugMessageAMDCallback.getMessage(p0, p1);
        LabyDebugContext.glError("getMessage", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }
}

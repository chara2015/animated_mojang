package net.labymod.core.laby3d.opengl.error.generated;

import java.util.function.IntFunction;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.FunctionProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLErrorContext.class */
public final class GLErrorContext {
    public static void create() {
        GL.create();
        LabyDebugContext.glError("create", new Object[0]);
    }

    public static void create(String p0) {
        GL.create(p0);
        LabyDebugContext.glError("create", "p0", p0);
    }

    public static void create(FunctionProvider p0) {
        GL.create(p0);
        LabyDebugContext.glError("create", "p0", p0);
    }

    public static void destroy() {
        GL.destroy();
        LabyDebugContext.glError("destroy", new Object[0]);
    }

    public static FunctionProvider getFunctionProvider() {
        FunctionProvider returnType = GL.getFunctionProvider();
        LabyDebugContext.glError("getFunctionProvider", new Object[0]);
        return returnType;
    }

    public static void setCapabilities(GLCapabilities p0) {
        GL.setCapabilities(p0);
        LabyDebugContext.glError("setCapabilities", "p0", p0);
    }

    public static GLCapabilities getCapabilities() {
        GLCapabilities returnType = GL.getCapabilities();
        LabyDebugContext.glError("getCapabilities", new Object[0]);
        return returnType;
    }

    public static WGLCapabilities getCapabilitiesWGL() {
        WGLCapabilities returnType = GL.getCapabilitiesWGL();
        LabyDebugContext.glError("getCapabilitiesWGL", new Object[0]);
        return returnType;
    }

    public static GLXCapabilities getCapabilitiesGLX() {
        GLXCapabilities returnType = GL.getCapabilitiesGLX();
        LabyDebugContext.glError("getCapabilitiesGLX", new Object[0]);
        return returnType;
    }

    public static GLCapabilities createCapabilities() {
        GLCapabilities returnType = GL.createCapabilities();
        LabyDebugContext.glError("createCapabilities", new Object[0]);
        return returnType;
    }

    public static GLCapabilities createCapabilities(IntFunction p0) {
        GLCapabilities returnType = GL.createCapabilities(p0);
        LabyDebugContext.glError("createCapabilities", "p0", p0);
        return returnType;
    }

    public static GLCapabilities createCapabilities(boolean p0) {
        GLCapabilities returnType = GL.createCapabilities(p0);
        LabyDebugContext.glError("createCapabilities", "p0", Boolean.valueOf(p0));
        return returnType;
    }

    public static GLCapabilities createCapabilities(boolean p0, IntFunction p1) {
        GLCapabilities returnType = GL.createCapabilities(p0, p1);
        LabyDebugContext.glError("createCapabilities", "p0", Boolean.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static WGLCapabilities createCapabilitiesWGL() {
        WGLCapabilities returnType = GL.createCapabilitiesWGL();
        LabyDebugContext.glError("createCapabilitiesWGL", new Object[0]);
        return returnType;
    }

    public static GLXCapabilities createCapabilitiesGLX(long p0) {
        GLXCapabilities returnType = GL.createCapabilitiesGLX(p0);
        LabyDebugContext.glError("createCapabilitiesGLX", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLXCapabilities createCapabilitiesGLX(long p0, int p1) {
        GLXCapabilities returnType = GL.createCapabilitiesGLX(p0, p1);
        LabyDebugContext.glError("createCapabilitiesGLX", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }
}

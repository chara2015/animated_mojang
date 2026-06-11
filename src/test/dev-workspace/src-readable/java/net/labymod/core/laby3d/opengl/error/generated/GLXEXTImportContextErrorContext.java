package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXEXTImportContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXEXTImportContextErrorContext.class */
public final class GLXEXTImportContextErrorContext {
    public static long glXGetCurrentDisplayEXT() {
        long returnType = GLXEXTImportContext.glXGetCurrentDisplayEXT();
        LabyDebugContext.glError("glXGetCurrentDisplayEXT", new Object[0]);
        return returnType;
    }

    public static int nglXQueryContextInfoEXT(long p0, long p1, int p2, long p3) {
        int returnType = GLXEXTImportContext.nglXQueryContextInfoEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("nglXQueryContextInfoEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
        return returnType;
    }

    public static int glXQueryContextInfoEXT(long p0, long p1, int p2, IntBuffer p3) {
        int returnType = GLXEXTImportContext.glXQueryContextInfoEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryContextInfoEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }

    public static long glXGetContextIDEXT(long p0) {
        long returnType = GLXEXTImportContext.glXGetContextIDEXT(p0);
        LabyDebugContext.glError("glXGetContextIDEXT", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long glXImportContextEXT(long p0, long p1) {
        long returnType = GLXEXTImportContext.glXImportContextEXT(p0, p1);
        LabyDebugContext.glError("glXImportContextEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static void glXFreeContextEXT(long p0, long p1) {
        GLXEXTImportContext.glXFreeContextEXT(p0, p1);
        LabyDebugContext.glError("glXFreeContextEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static int glXQueryContextInfoEXT(long p0, long p1, int p2, int[] p3) {
        int returnType = GLXEXTImportContext.glXQueryContextInfoEXT(p0, p1, p2, p3);
        LabyDebugContext.glError("glXQueryContextInfoEXT", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
        return returnType;
    }
}

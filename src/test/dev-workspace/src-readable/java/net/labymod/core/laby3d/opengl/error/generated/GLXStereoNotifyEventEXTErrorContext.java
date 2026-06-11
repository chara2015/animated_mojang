package net.labymod.core.laby3d.opengl.error.generated;

import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GLXStereoNotifyEventEXT;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GLXStereoNotifyEventEXTErrorContext.class */
public final class GLXStereoNotifyEventEXTErrorContext {
    public static GLXStereoNotifyEventEXT create(long p0) {
        GLXStereoNotifyEventEXT returnType = GLXStereoNotifyEventEXT.create(p0);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLXStereoNotifyEventEXT createSafe(long p0) {
        GLXStereoNotifyEventEXT returnType = GLXStereoNotifyEventEXT.createSafe(p0);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static GLXStereoNotifyEventEXT.Buffer create(long p0, int p1) {
        GLXStereoNotifyEventEXT.Buffer returnType = GLXStereoNotifyEventEXT.create(p0, p1);
        LabyDebugContext.glError("create", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static GLXStereoNotifyEventEXT.Buffer createSafe(long p0, int p1) {
        GLXStereoNotifyEventEXT.Buffer returnType = GLXStereoNotifyEventEXT.createSafe(p0, p1);
        LabyDebugContext.glError("createSafe", "p0", Long.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static int ntype(long p0) {
        int returnType = GLXStereoNotifyEventEXT.ntype(p0);
        LabyDebugContext.glError("ntype", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long nserial(long p0) {
        long returnType = GLXStereoNotifyEventEXT.nserial(p0);
        LabyDebugContext.glError("nserial", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nsend_event(long p0) {
        int returnType = GLXStereoNotifyEventEXT.nsend_event(p0);
        LabyDebugContext.glError("nsend_event", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long ndisplay(long p0) {
        long returnType = GLXStereoNotifyEventEXT.ndisplay(p0);
        LabyDebugContext.glError("ndisplay", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nextension(long p0) {
        int returnType = GLXStereoNotifyEventEXT.nextension(p0);
        LabyDebugContext.glError("nextension", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nevtype(long p0) {
        int returnType = GLXStereoNotifyEventEXT.nevtype(p0);
        LabyDebugContext.glError("nevtype", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static long nwindow(long p0) {
        long returnType = GLXStereoNotifyEventEXT.nwindow(p0);
        LabyDebugContext.glError("nwindow", "p0", Long.valueOf(p0));
        return returnType;
    }

    public static int nstereo_tree(long p0) {
        int returnType = GLXStereoNotifyEventEXT.nstereo_tree(p0);
        LabyDebugContext.glError("nstereo_tree", "p0", Long.valueOf(p0));
        return returnType;
    }
}

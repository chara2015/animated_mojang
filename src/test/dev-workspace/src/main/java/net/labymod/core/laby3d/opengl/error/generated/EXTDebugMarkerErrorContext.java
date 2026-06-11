package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.EXTDebugMarker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/EXTDebugMarkerErrorContext.class */
public final class EXTDebugMarkerErrorContext {
    public static void nglInsertEventMarkerEXT(int p0, long p1) {
        EXTDebugMarker.nglInsertEventMarkerEXT(p0, p1);
        LabyDebugContext.glError("nglInsertEventMarkerEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glInsertEventMarkerEXT(ByteBuffer p0) {
        EXTDebugMarker.glInsertEventMarkerEXT(p0);
        LabyDebugContext.glError("glInsertEventMarkerEXT", "p0", p0);
    }

    public static void glInsertEventMarkerEXT(CharSequence p0) {
        EXTDebugMarker.glInsertEventMarkerEXT(p0);
        LabyDebugContext.glError("glInsertEventMarkerEXT", "p0", p0);
    }

    public static void nglPushGroupMarkerEXT(int p0, long p1) {
        EXTDebugMarker.nglPushGroupMarkerEXT(p0, p1);
        LabyDebugContext.glError("nglPushGroupMarkerEXT", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glPushGroupMarkerEXT(ByteBuffer p0) {
        EXTDebugMarker.glPushGroupMarkerEXT(p0);
        LabyDebugContext.glError("glPushGroupMarkerEXT", "p0", p0);
    }

    public static void glPushGroupMarkerEXT(CharSequence p0) {
        EXTDebugMarker.glPushGroupMarkerEXT(p0);
        LabyDebugContext.glError("glPushGroupMarkerEXT", "p0", p0);
    }

    public static void glPopGroupMarkerEXT() {
        EXTDebugMarker.glPopGroupMarkerEXT();
        LabyDebugContext.glError("glPopGroupMarkerEXT", new Object[0]);
    }
}

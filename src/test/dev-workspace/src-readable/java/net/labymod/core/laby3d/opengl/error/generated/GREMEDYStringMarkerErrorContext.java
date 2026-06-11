package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GREMEDYStringMarker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GREMEDYStringMarkerErrorContext.class */
public final class GREMEDYStringMarkerErrorContext {
    public static void nglStringMarkerGREMEDY(int p0, long p1) {
        GREMEDYStringMarker.nglStringMarkerGREMEDY(p0, p1);
        LabyDebugContext.glError("nglStringMarkerGREMEDY", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glStringMarkerGREMEDY(ByteBuffer p0) {
        GREMEDYStringMarker.glStringMarkerGREMEDY(p0);
        LabyDebugContext.glError("glStringMarkerGREMEDY", "p0", p0);
    }

    public static void glStringMarkerGREMEDY(CharSequence p0) {
        GREMEDYStringMarker.glStringMarkerGREMEDY(p0);
        LabyDebugContext.glError("glStringMarkerGREMEDY", "p0", p0);
    }
}

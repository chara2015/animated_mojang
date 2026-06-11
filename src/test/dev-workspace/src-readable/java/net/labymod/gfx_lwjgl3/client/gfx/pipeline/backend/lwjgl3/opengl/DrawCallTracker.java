package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.util.logging.Logging;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/DrawCallTracker.class */
public final class DrawCallTracker {
    private static final Logging LOGGER = Logging.create((Class<?>) DrawCallTracker.class);

    public static void glDrawElements(int mode, int count, int type, long pointer) {
        int ebo = GL11.glGetInteger(GlConst.GL_ELEMENT_ARRAY_BUFFER_BINDING);
        if (ebo == 0 && isInvalidPointer(pointer)) {
            LOGGER.warn("A native crash could be avoided. Vertex array object {} has no EBO/IBO bound.", Integer.valueOf(VertexArrayObjectTracker.getCurrentVao()));
        } else {
            GL11.glDrawElements(mode, count, type, pointer);
        }
    }

    private static boolean isInvalidPointer(long pointer) {
        return pointer >= 0 && pointer < 1024;
    }
}

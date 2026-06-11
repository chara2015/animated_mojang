package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.IntBuffer;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.util.logging.Logging;
import org.lwjgl.opengl.GL30;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/VertexArrayObjectTracker.class */
public final class VertexArrayObjectTracker {
    private static final int UNSET = -1;
    private static final Logging LOGGER = Logging.create("GL30");
    private static int defaultVao = -1;
    private static int currentVao = -1;
    private static final IntSet VERTEX_ARRAY_OBJECTS = new IntOpenHashSet();

    public static int getCurrentVao() {
        return currentVao;
    }

    public static boolean isVertexArray(int array) {
        return VERTEX_ARRAY_OBJECTS.contains(array);
    }

    public static void glBindVertexArray(int array) {
        if (defaultVao == -1) {
            defaultVao = GL30.glGetInteger(GlConst.GL_VERTEX_ARRAY_BINDING);
            currentVao = defaultVao;
            LOGGER.info("Default VAO: {}", Integer.valueOf(defaultVao));
        }
        if (currentVao == array) {
            return;
        }
        GL30.glBindVertexArray(array);
        currentVao = array;
    }

    public static int glGenVertexArrays() {
        int vao = GL30.glGenVertexArrays();
        addVertexArrayObject(vao);
        return vao;
    }

    public static void glGenVertexArrays(int[] arrays) {
        GL30.glGenVertexArrays(arrays);
        for (int vaoHandle : arrays) {
            addVertexArrayObject(vaoHandle);
        }
    }

    public static void glGenVertexArrays(IntBuffer arrays) {
        GL30.glGenVertexArrays(arrays);
        int capacity = arrays.capacity();
        for (int index = 0; index < capacity; index++) {
            int vaoHandle = arrays.get(index);
            addVertexArrayObject(vaoHandle);
        }
    }

    public static void glDeleteVertexArrays(int array) {
        removeVertexArrayObject(array);
        GL30.glDeleteVertexArrays(array);
    }

    public static void glDeleteVertexArrays(int[] arrays) {
        for (int vaoHandle : arrays) {
            removeVertexArrayObject(vaoHandle);
        }
        GL30.glDeleteVertexArrays(arrays);
    }

    public static void glDeleteVertexArrays(IntBuffer arrays) {
        int capacity = arrays.capacity();
        for (int index = 0; index < capacity; index++) {
            int vaoHandle = arrays.get(index);
            removeVertexArrayObject(vaoHandle);
        }
        GL30.glDeleteVertexArrays(arrays);
    }

    private static void addVertexArrayObject(int handle) {
        if (VERTEX_ARRAY_OBJECTS.contains(handle)) {
            throw new IllegalStateException("The handle is already contained in the list (" + handle + ")");
        }
        VERTEX_ARRAY_OBJECTS.add(handle);
    }

    private static void removeVertexArrayObject(int handle) {
        if (currentVao == handle) {
            glBindVertexArray(0);
        }
        VERTEX_ARRAY_OBJECTS.remove(handle);
    }
}

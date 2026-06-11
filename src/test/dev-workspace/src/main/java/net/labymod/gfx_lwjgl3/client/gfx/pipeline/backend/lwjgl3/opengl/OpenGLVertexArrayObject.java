package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/OpenGLVertexArrayObject.class */
public final class OpenGLVertexArrayObject {
    public static int generate() {
        return generate(null);
    }

    public static int generate(@Nullable IntSupplier otherMethod) {
        if (otherMethod != null) {
            return otherMethod.getAsInt();
        }
        if (isArbVertexArrayObjectSupported()) {
            return ARBVertexArrayObject.glGenVertexArrays();
        }
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> {
                return "The glGenVertexArrays function is not available in the current context.";
            });
            return -1;
        }
        return GL30.glGenVertexArrays();
    }

    public static void bind(int id) {
        bind(id, null);
    }

    public static void bind(int id, @Nullable IntConsumer otherMethod) {
        if (otherMethod != null) {
            otherMethod.accept(id);
            return;
        }
        if (isArbVertexArrayObjectSupported()) {
            ARBVertexArrayObject.glBindVertexArray(id);
        } else if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> {
                return "The glBindVertexArray function is not available in the current context.";
            });
        } else {
            GL30.glBindVertexArray(id);
        }
    }

    public static void delete(int id) {
        delete(id, null);
    }

    public static void delete(int id, @Nullable IntConsumer otherMethod) {
        if (otherMethod != null) {
            otherMethod.accept(id);
            return;
        }
        if (isArbVertexArrayObjectSupported()) {
            ARBVertexArrayObject.glDeleteVertexArrays(id);
        } else if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> {
                return "The glDeleteVertexArrays function is not available in the current context.";
            });
        } else {
            GL30.glDeleteVertexArrays(id);
        }
    }

    private static boolean isArbVertexArrayObjectSupported() {
        GLCapabilities capabilities = GL.getCapabilities();
        return capabilities.GL_ARB_vertex_array_object;
    }
}

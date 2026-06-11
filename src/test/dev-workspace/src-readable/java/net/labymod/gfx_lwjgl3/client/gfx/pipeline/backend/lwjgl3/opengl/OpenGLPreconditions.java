package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import java.util.function.Supplier;
import net.labymod.api.Laby;
import org.lwjgl.opengl.GL;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/OpenGLPreconditions.class */
public final class OpenGLPreconditions {
    private OpenGLPreconditions() {
    }

    public static boolean isGL30Supported() {
        return GL.getCapabilities().OpenGL30;
    }

    public static void assertValidCode(Supplier<String> messageSupplier) {
        if (!Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment()) {
        } else {
            throw new IllegalStateException(messageSupplier.get());
        }
    }
}

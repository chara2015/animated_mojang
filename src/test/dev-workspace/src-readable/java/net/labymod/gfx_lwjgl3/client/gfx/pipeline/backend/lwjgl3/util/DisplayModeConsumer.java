package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.util.function.Consumer;
import org.lwjgl.opengl.DisplayMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/DisplayModeConsumer.class */
public final class DisplayModeConsumer {
    private static Consumer<DisplayMode> displayModeConsumer = displayMode -> {
    };

    public static void setDisplayModeConsumer(Consumer<DisplayMode> displayModeConsumer2) {
        displayModeConsumer = displayModeConsumer2;
    }

    public static void consume(DisplayMode displayMode) {
        displayModeConsumer.accept(displayMode);
    }
}

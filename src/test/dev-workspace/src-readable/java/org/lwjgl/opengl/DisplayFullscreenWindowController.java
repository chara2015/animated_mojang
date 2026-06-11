package org.lwjgl.opengl;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;
import org.lwjgl.LWJGLException;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/opengl/DisplayFullscreenWindowController.class */
public class DisplayFullscreenWindowController implements FullscreenWindowController {
    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public boolean isWindowFullscreen() {
        return Display.isFullscreen();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.lwjgl.LWJGLException */
    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public void setWindowFullscreen(boolean value) {
        try {
            Display.setFullscreen(value);
        } catch (LWJGLException e) {
        }
    }
}

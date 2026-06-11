package net.labymod.api.client.world.canvas;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/CanvasRenderer.class */
public interface CanvasRenderer {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/CanvasRenderer$CanvasSide.class */
    public enum CanvasSide {
        FRONT,
        BACK
    }

    void render2D(Stack stack, MutableMouse mutableMouse, Canvas canvas, CanvasSide canvasSide, float f);

    void renderOverlay2D(Stack stack, MutableMouse mutableMouse, Canvas canvas, CanvasSide canvasSide, float f);

    void dispose(Canvas canvas);

    default boolean hasOverlay() {
        return true;
    }
}

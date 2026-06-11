package net.labymod.core.client.world.canvas;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.CanvasRenderer;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/ActivityCanvasRenderer.class */
public class ActivityCanvasRenderer implements CanvasRenderer {
    private static final float SCALE = 0.25f;
    private final Activity activity;
    private boolean initialized;

    public ActivityCanvasRenderer(Activity activity) {
        this.activity = activity;
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void render2D(Stack stack, MutableMouse mouse, Canvas canvas, CanvasRenderer.CanvasSide side, float tickDelta) {
        if (!this.initialized) {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.activity.resize((int) (canvas.getWidth() / SCALE), (int) (canvas.getHeight() / SCALE));
            this.activity.load(window);
            this.initialized = true;
        }
        transform(stack, canvas, () -> {
            ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            Activity activity = this.activity;
            Objects.requireNonNull(activity);
            screenContext.runInContext(stack, mouse, tickDelta, activity::render);
        });
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void renderOverlay2D(Stack stack, MutableMouse mouse, Canvas canvas, CanvasRenderer.CanvasSide side, float tickDelta) {
        transform(stack, canvas, () -> {
            ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            Activity activity = this.activity;
            Objects.requireNonNull(activity);
            screenContext.runInContext(stack, mouse, tickDelta, activity::renderOverlay);
        });
    }

    private void transform(Stack stack, Canvas canvas, Runnable renderer) {
        stack.scale(SCALE, SCALE, 1.0f);
        Rectangle bounds = Rectangle.relative(0.0f, 0.0f, canvas.getWidth() / SCALE, canvas.getHeight() / SCALE);
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        window.transform(null, stack, bounds, renderer);
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void dispose(Canvas canvas) {
        if (this.initialized) {
            this.activity.onCloseScreen();
            this.initialized = false;
        }
    }
}

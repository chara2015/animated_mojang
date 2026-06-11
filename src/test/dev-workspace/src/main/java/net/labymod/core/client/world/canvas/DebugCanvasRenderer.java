package net.labymod.core.client.world.canvas;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.CanvasRenderer;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/DebugCanvasRenderer.class */
public class DebugCanvasRenderer implements CanvasRenderer {
    private static final Component DEBUG_TEXT = Component.text("Debug ").append(Component.text("Debug", NamedTextColor.GREEN));

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void render2D(Stack stack, MutableMouse mouse, Canvas canvas, CanvasRenderer.CanvasSide side, float tickDelta) {
        Laby.labyAPI().renderPipeline().rectangleRenderer().setupGradient(stack, ctx -> {
            ctx.renderGradientVertically(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), Color.RED.get(), Color.BLUE.get());
        });
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void renderOverlay2D(Stack stack, MutableMouse mouse, Canvas canvas, CanvasRenderer.CanvasSide side, float tickDelta) {
        ComponentRenderer renderer = Laby.references().componentRenderer();
        RenderableComponent component = RenderableComponent.of(DEBUG_TEXT, canvas.getWidth() - 10.0f, HorizontalAlignment.CENTER);
        renderer.builder().text(component).pos(canvas.getWidth() / 2.0f, (canvas.getHeight() / 2.0f) - (component.getHeight() / 2.0f)).centered(true).render(stack);
    }

    @Override // net.labymod.api.client.world.canvas.CanvasRenderer
    public void dispose(Canvas canvas) {
    }
}

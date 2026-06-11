package net.labymod.api.client.render.font;

import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.VanillaScreen;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/ComponentRenderMeta.class */
public class ComponentRenderMeta implements Rectangle {
    private final RenderableComponent hovered;
    private final float minX;
    private final float maxX;
    private final float minY;
    private final float maxY;
    private final float renderedWidth;

    public ComponentRenderMeta(RenderableComponent hovered, float minX, float maxX, float minY, float maxY, float renderedWidth) {
        this.hovered = hovered;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.renderedWidth = renderedWidth;
    }

    public Optional<RenderableComponent> getHovered() {
        return Optional.ofNullable(this.hovered);
    }

    public float getMinX() {
        return this.minX;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getMaxX() {
        return this.maxX;
    }

    public float getMinY() {
        return this.minY;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getMaxY() {
        return this.maxY;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.minX;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.minY;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.maxX;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.maxY;
    }

    public float getRenderedWidth() {
        return this.renderedWidth;
    }

    public boolean interact() {
        Optional<RenderableComponent> hovered = getHovered();
        if (hovered.isEmpty()) {
            return false;
        }
        Style style = hovered.get().style();
        String insertion = style.getInsertion();
        if (insertion != null && Laby.labyAPI().minecraft().isKeyPressed(Key.L_SHIFT)) {
            Laby.labyAPI().minecraft().chatExecutor().insertText(insertion);
            return true;
        }
        ClickEvent event = style.getClickEvent();
        if (event != null) {
            Laby.labyAPI().minecraft().chatExecutor().performAction(event);
            return true;
        }
        return false;
    }

    public void renderHover(Stack stack, MutableMouse mouse) {
        Optional<RenderableComponent> hovered = getHovered();
        if (hovered.isEmpty()) {
            return;
        }
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen();
        if (screen instanceof VanillaScreen) {
            ((VanillaScreen) screen).renderComponentHoverEffect(stack, hovered.get().style(), mouse.getX(), mouse.getY());
        }
    }
}

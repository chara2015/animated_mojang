package net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid;

import java.util.Iterator;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/window/grid/HudWidgetTilesGridWidget.class */
@AutoWidget
public class HudWidgetTilesGridWidget extends AbstractWidget<HudWidgetTypeInfoWidget> {
    private static final ModifyReason HUD_WIDGET_DRAGGING = ModifyReason.of("hudWidgetDragging");
    private static final ModifyReason GRID_UPDATE = ModifyReason.of("gridUpdate");
    private final WidgetsEditorActivity editor;
    private final LssProperty<Float> spaceBetweenEntries = new LssProperty<>(Float.valueOf(1.0f));
    private HudWidgetTypeInfoWidget draggingWidget;
    private float dragOffsetX;
    private float dragOffsetY;
    private MutableRectangle fallbackRectangle;

    public HudWidgetTilesGridWidget(WidgetsEditorActivity editor) {
        this.editor = editor;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateTiles();
    }

    public void updateTiles() {
        Bounds bounds = bounds();
        float space = this.spaceBetweenEntries.get().floatValue();
        int tilesPerRow = Math.max(MathHelper.floor(bounds.getWidth() / 80.0f), 1);
        float tileWidth = (bounds.getWidth() - (space * (tilesPerRow - 1))) / tilesPerRow;
        float x = bounds.getLeft();
        float y = bounds.getTop();
        float maxHeight = 0.0f;
        for (T child : this.children) {
            Bounds childBounds = child.bounds();
            childBounds.setX(x, GRID_UPDATE);
            childBounds.setY(y, GRID_UPDATE);
            childBounds.setWidth(tileWidth, GRID_UPDATE);
            maxHeight = Math.max(maxHeight, childBounds.getHeight());
            x += tileWidth + space;
            if (x >= bounds.getRight()) {
                x = bounds.getLeft();
                y += maxHeight + space;
                maxHeight = 0.0f;
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        HudWidgetTypeInfoWidget widget;
        if (mouseButton == MouseButton.LEFT && (widget = getTargetAt(mouse.getX(), mouse.getY())) != null && !widget.hudWidget().isEnabled()) {
            this.draggingWidget = widget;
            Bounds bounds = widget.bounds();
            this.dragOffsetX = mouse.getX() - bounds.getX();
            this.dragOffsetY = mouse.getY() - bounds.getY();
            this.fallbackRectangle = bounds.copy();
            try {
                this.editor.renderer().createHudWidget(widget.hudWidget());
            } catch (Exception e) {
                e.printStackTrace();
            }
            widget.draggable().set(true);
            widget.setDragging(true);
            return false;
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (this.draggingWidget != null) {
            double x = mouse.getXDouble() - ((double) this.dragOffsetX);
            double y = mouse.getYDouble() - ((double) this.dragOffsetY);
            this.draggingWidget.bounds().setPosition((float) x, (float) y, HUD_WIDGET_DRAGGING);
            return false;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        HudWidgetTypeInfoWidget widget;
        if (mouseButton == MouseButton.LEFT && (widget = this.draggingWidget) != null) {
            this.draggingWidget = null;
            widget.bounds().set(this.fallbackRectangle, HUD_WIDGET_DRAGGING);
            if (widget.hudWidget().isEnabled()) {
                widget.skipInterpolation();
                widget.reInitialize();
            }
            widget.setDragging(false);
            widget.draggable().set(false);
            updateTiles();
            return false;
        }
        return super.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        Bounds bounds = bounds();
        float space = this.spaceBetweenEntries.get().floatValue();
        int tilesPerRow = Math.max(MathHelper.floor(bounds.getWidth() / 80.0f), 1);
        float height = 0.0f;
        float maxHeight = 0.0f;
        for (int i = 0; i < this.children.size(); i++) {
            Widget child = (Widget) this.children.get(i);
            maxHeight = Math.max(maxHeight, child.bounds().getHeight());
            if (i % tilesPerRow == 0) {
                height += maxHeight + space;
                maxHeight = 0.0f;
            }
        }
        return height;
    }

    private HudWidgetTypeInfoWidget getTargetAt(float x, float y) {
        Iterator<HudWidgetTypeInfoWidget> it = getChildrenAt((int) x, (int) y).iterator();
        if (it.hasNext()) {
            HudWidgetTypeInfoWidget widget = it.next();
            return widget;
        }
        return null;
    }

    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }

    public HudWidgetTypeInfoWidget draggingWidget() {
        return this.draggingWidget;
    }

    public WidgetsEditorActivity editor() {
        return this.editor;
    }
}

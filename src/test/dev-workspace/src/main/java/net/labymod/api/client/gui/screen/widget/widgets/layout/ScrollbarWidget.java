package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/ScrollbarWidget.class */
@AutoWidget
public class ScrollbarWidget extends SimpleWidget {
    private final ScrollWidget scrollWidget;
    private final LssProperty<Float> scrollButtonClickOffset = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Boolean> useLssPosition = new LssProperty<>(false);
    private final LssProperty<Integer> scrollColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(0, 0, 0, 200)));
    private final LssProperty<Integer> minScrollHeight = new LssProperty<>(1);
    private final LssProperty<Integer> scrollHoverColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(30, 30, 30, 200)));
    private final LssProperty<Integer> scrollBackgroundColor = new LssProperty<>(Integer.valueOf(ColorFormat.ARGB32.pack(110, 110, 110, 100)));

    public ScrollbarWidget(ScrollWidget scrollWidget) {
        this.scrollWidget = scrollWidget;
        draggable().set(true);
        setHoverCursor(CursorTypes.RESIZE_NS, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Scrollbar";
    }

    public ScrollWidget scrollWidget() {
        return this.scrollWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHovered()) {
            float buttonOffset = (float) (mouse.getYDouble() - ((double) (bounds().getTop(BoundsType.MIDDLE) + getScrollButtonOffset())));
            if (buttonOffset >= 0.0f || buttonOffset <= getScrollButtonHeight()) {
                setDragging(true);
                setHoverCursor(CursorTypes.RESIZE_NS, true);
                this.scrollButtonClickOffset.set(Float.valueOf(buttonOffset));
                return true;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (isDragging()) {
            float offset = (mouse.getY() - bounds().getTop(BoundsType.MIDDLE)) - this.scrollButtonClickOffset.get().floatValue();
            float scale = getScrollbarHeight() / getContentHeight();
            float scrollPositionY = offset / scale;
            float previousPosition = this.scrollWidget.session().getScrollPositionY();
            this.scrollWidget.session().setScrollPositionY(scrollPositionY);
            this.scrollWidget.updateScrollbarBounds();
            this.scrollWidget.scrolled(previousPosition, this.scrollWidget.session().getScrollPositionY());
            return true;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        setDragging(false);
        setHoverCursor(CursorTypes.POINTING_HAND, true);
        return super.mouseReleased(mouse, mouseButton);
    }

    public float getScrollButtonHeight() {
        float contentHeight = getContentHeight();
        float scrollbarHeight = bounds().getHeight(BoundsType.MIDDLE);
        return Math.max(this.minScrollHeight.get().intValue(), (scrollbarHeight / contentHeight) * scrollbarHeight);
    }

    public float getScrollButtonOffset() {
        float percentage = (1.0f / this.scrollWidget.getOverflowHeight()) * getScrollPositionY();
        float maxOffset = getScrollbarHeight() - getScrollButtonHeight();
        return maxOffset * percentage;
    }

    protected float getContentHeight() {
        return this.scrollWidget.session().getContentHeight();
    }

    private float getScrollbarHeight() {
        return bounds().getHeight(BoundsType.MIDDLE);
    }

    private float getScrollPositionY() {
        return this.scrollWidget.session().getRenderScrollPositionY();
    }

    public LssProperty<Float> scrollButtonClickOffset() {
        return this.scrollButtonClickOffset;
    }

    public LssProperty<Boolean> useLssPosition() {
        return this.useLssPosition;
    }

    public LssProperty<Integer> scrollColor() {
        return this.scrollColor;
    }

    public LssProperty<Integer> scrollHoverColor() {
        return this.scrollHoverColor;
    }

    public LssProperty<Integer> scrollBackgroundColor() {
        return this.scrollBackgroundColor;
    }

    public LssProperty<Integer> minScrollHeight() {
        return this.minScrollHeight;
    }
}

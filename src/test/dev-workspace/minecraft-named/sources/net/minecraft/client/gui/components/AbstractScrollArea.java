package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/AbstractScrollArea.class */
public abstract class AbstractScrollArea extends AbstractWidget {
    public static final int SCROLLBAR_WIDTH = 6;
    private double scrollAmount;
    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("widget/scroller");
    private static final Identifier SCROLLER_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("widget/scroller_background");
    private boolean scrolling;

    protected abstract int contentHeight();

    protected abstract double scrollRate();

    public AbstractScrollArea(int $$0, int $$1, int $$2, int $$3, Component $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Override // net.minecraft.client.gui.components.events.GuiEventListener
    public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
        if (!this.visible) {
            return false;
        }
        setScrollAmount(scrollAmount() - ($$3 * scrollRate()));
        return true;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public boolean mouseDragged(MouseButtonEvent $$0, double $$1, double $$2) {
        if (!this.scrolling) {
            return super.mouseDragged($$0, $$1, $$2);
        }
        if ($$0.y() < getY()) {
            setScrollAmount(Density.SURFACE);
            return true;
        }
        if ($$0.y() > getBottom()) {
            setScrollAmount(maxScrollAmount());
            return true;
        }
        double $$3 = Math.max(1, maxScrollAmount());
        int $$4 = scrollerHeight();
        double $$5 = Math.max(1.0d, $$3 / ((double) (this.height - $$4)));
        setScrollAmount(scrollAmount() + ($$2 * $$5));
        return true;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    public void onRelease(MouseButtonEvent $$0) {
        this.scrolling = false;
    }

    public double scrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(double $$0) {
        this.scrollAmount = Mth.clamp($$0, Density.SURFACE, maxScrollAmount());
    }

    public boolean updateScrolling(MouseButtonEvent $$0) {
        this.scrolling = scrollbarVisible() && isValidClickButton($$0.buttonInfo()) && isOverScrollbar($$0.x(), $$0.y());
        return this.scrolling;
    }

    protected boolean isOverScrollbar(double $$0, double $$1) {
        return $$0 >= ((double) scrollBarX()) && $$0 <= ((double) (scrollBarX() + 6)) && $$1 >= ((double) getY()) && $$1 < ((double) getBottom());
    }

    public void refreshScrollAmount() {
        setScrollAmount(this.scrollAmount);
    }

    public int maxScrollAmount() {
        return Math.max(0, contentHeight() - this.height);
    }

    protected boolean scrollbarVisible() {
        return maxScrollAmount() > 0;
    }

    protected int scrollerHeight() {
        return Mth.clamp((int) ((this.height * this.height) / contentHeight()), 32, this.height - 8);
    }

    protected int scrollBarX() {
        return getRight() - 6;
    }

    protected int scrollBarY() {
        return Math.max(getY(), ((((int) this.scrollAmount) * (this.height - scrollerHeight())) / maxScrollAmount()) + getY());
    }

    protected void renderScrollbar(GuiGraphics $$0, int $$1, int $$2) {
        if (scrollbarVisible()) {
            int $$3 = scrollBarX();
            int $$4 = scrollerHeight();
            int $$5 = scrollBarY();
            $$0.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLLER_BACKGROUND_SPRITE, $$3, getY(), 6, getHeight());
            $$0.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLLER_SPRITE, $$3, $$5, 6, $$4);
            if (isOverScrollbar($$1, $$2)) {
                $$0.requestCursor(this.scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
            }
        }
    }
}

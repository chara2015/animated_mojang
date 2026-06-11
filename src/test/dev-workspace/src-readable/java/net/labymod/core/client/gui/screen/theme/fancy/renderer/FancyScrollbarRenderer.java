package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaScrollbarRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyScrollbarRenderer.class */
public class FancyScrollbarRenderer extends VanillaScrollbarRenderer {
    private static final ModifyReason FANCY_SCROLLBAR_RENDER_PADDING = ModifyReason.renderOnly("fancyScrollbarRenderPadding");

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaScrollbarRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(ScrollbarWidget widget, ScreenContext context) {
        Integer num;
        ScrollWidget scrollWidget = widget.scrollWidget();
        if (!scrollWidget.isScrollbarRequired()) {
            return;
        }
        Bounds bounds = widget.bounds();
        float width = bounds.getWidth();
        float radius = width - ((width / 2.0f) - 0.25f);
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitAbsoluteRoundedRect(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), widget.scrollBackgroundColor().get().intValue(), RoundedData.builder().setRadius(radius).setLowerEdgeSoftness(-0.125f).setUpperEdgeSoftness(0.5f).build());
        float scrollTop = widget.bounds().getTop() + widget.getScrollButtonOffset();
        float scrollBottom = scrollTop + widget.getScrollButtonHeight();
        float radius2 = radius - 1.0f;
        bounds.shrink(1.0f, FANCY_SCROLLBAR_RENDER_PADDING);
        float left = bounds.getLeft();
        float f = scrollTop + 1.0f;
        float right = bounds.getRight();
        float f2 = scrollBottom - 1.0f;
        if (context.mouse().isInside(bounds) || widget.isDragging()) {
            num = widget.scrollHoverColor().get();
        } else {
            num = widget.scrollColor().get();
        }
        screenCanvas.submitAbsoluteRoundedRect(left, f, right, f2, num.intValue(), RoundedData.builder().setRadius(radius2).setLowerEdgeSoftness(-0.125f).setUpperEdgeSoftness(0.5f).build());
        bounds.expand(1.0f, FANCY_SCROLLBAR_RENDER_PADDING);
    }
}

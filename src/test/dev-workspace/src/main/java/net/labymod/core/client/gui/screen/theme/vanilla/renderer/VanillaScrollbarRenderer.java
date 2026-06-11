package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaScrollbarRenderer.class */
public class VanillaScrollbarRenderer extends ThemeRenderer<ScrollbarWidget> {
    private static final int SCROLLBAR_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0, 0, 0, 255);
    private static final int SCROLLBAR_BUTTON_COLOR = ColorFormat.ARGB32.pack(255, 255, 255, 255);
    private static final int BUTTON_COLOR_SHADOW = ColorFormat.ARGB32.pack(128, 128, 128, 255);
    private static final int BUTTON_COLOR = ColorFormat.ARGB32.pack(192, 192, 192, 255);

    public VanillaScrollbarRenderer() {
        super("Scrollbar");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(ScrollbarWidget widget, ScreenContext context) {
        ScrollWidget scrollWidget = widget.scrollWidget();
        if (!scrollWidget.isScrollbarRequired()) {
            return;
        }
        Bounds bounds = widget.bounds();
        float scrollBackgroundTop = bounds.getTop(BoundsType.MIDDLE);
        float scrollBackgroundBottom = bounds.getBottom(BoundsType.MIDDLE);
        float scrollButtonTop = widget.bounds().getTop(BoundsType.MIDDLE) + widget.getScrollButtonOffset();
        float scrollButtonBottom = scrollButtonTop + widget.getScrollButtonHeight();
        float scrollLeft = bounds.getLeft(BoundsType.MIDDLE);
        float scrollRight = bounds.getRight(BoundsType.MIDDLE);
        ScreenCanvas renderState = context.canvas();
        renderState.submitAbsoluteRect(scrollLeft, scrollBackgroundTop, scrollRight, scrollBackgroundBottom, SCROLLBAR_BACKGROUND_COLOR);
        if (bounds.getWidth() == 1.0f) {
            renderState.submitAbsoluteRect(scrollLeft, scrollButtonTop, scrollRight, scrollButtonBottom, SCROLLBAR_BUTTON_COLOR);
        } else {
            renderState.submitAbsoluteRect(scrollLeft, scrollButtonTop, scrollRight, scrollButtonBottom, BUTTON_COLOR_SHADOW);
            renderState.submitAbsoluteRect(scrollLeft, scrollButtonTop, scrollRight - 1.0f, scrollButtonBottom - 1.0f, BUTTON_COLOR);
        }
    }
}

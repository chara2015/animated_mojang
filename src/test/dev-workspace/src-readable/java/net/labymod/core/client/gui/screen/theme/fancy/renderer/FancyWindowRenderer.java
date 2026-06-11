package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyWindowRenderer.class */
public class FancyWindowRenderer extends ThemeRenderer<AbstractWidget<?>> {
    public FancyWindowRenderer() {
        super("Window");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitRoundedRect(widget.bounds().rectangle(BoundsType.MIDDLE), -2013265920, RoundedData.builder().setRadius(5.0f).build());
        if (!(widget instanceof NewWindowWidget)) {
            return;
        }
        NewWindowWidget windowWidget = (NewWindowWidget) widget;
        if (windowWidget.getRescaleType() == NewWindowWidget.RescaleType.CORNER) {
            Rectangle corner = windowWidget.getCorner();
            boolean mouseOverRescale = corner.isInRectangle(context.mouse()) || windowWidget.isRescaling();
            screenCanvas.submitCircle(widget.bounds().getRight() - 7.0f, widget.bounds().getBottom() - 7.0f, 8.0f, 10.0f, 270.0f, 90.0f, mouseOverRescale ? -1 : -6710887);
        }
    }
}

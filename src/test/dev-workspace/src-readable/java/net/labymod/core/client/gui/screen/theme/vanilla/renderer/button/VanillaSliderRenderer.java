package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/button/VanillaSliderRenderer.class */
public class VanillaSliderRenderer extends VanillaButtonRenderer {
    public VanillaSliderRenderer() {
        super("Slider");
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        SliderWidget sliderWidget = (SliderWidget) widget;
        Rectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        renderTexture(context, bounds, false, false, 0);
        float offset = (bounds.getWidth() - 8.0f) * sliderWidget.getPercentage();
        MutableRectangle stateRectangle = bounds.copy();
        stateRectangle.setWidth(8.0f);
        stateRectangle.setX(bounds.getX() + offset);
        renderTexture(context, stateRectangle, true, stateRectangle.isInRectangle(context.mouse()), 0);
    }
}

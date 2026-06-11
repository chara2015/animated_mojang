package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancySlimSliderRenderer.class */
public class FancySlimSliderRenderer extends FancySliderRenderer {
    public FancySlimSliderRenderer() {
        this.name = "SlimSlider";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        ReasonableMutableRectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        Rectangle rectangle = Rectangle.relative(bounds.getX(), bounds.getCenterY() - (2 / 2.0f), bounds.getWidth(), 2);
        Rectangle border = widget.bounds().rectangle(BoundsType.BORDER);
        renderBackground(context, widget, rectangle, border, context.getTickDelta());
    }

    @Override // net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySliderRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        SliderWidget slider = (SliderWidget) widget;
        Rectangle bounds = slider.bounds().rectangle(BoundsType.MIDDLE);
        float offset = getOffset(slider, bounds, context.mouse());
        renderKnob(context, bounds.getLeft() + offset, bounds.getTop() + (8.0f / 4.0f), 8.0f, 8.0f, 4.0f);
    }
}

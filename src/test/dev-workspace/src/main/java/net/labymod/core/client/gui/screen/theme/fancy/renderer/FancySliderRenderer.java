package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancySliderRenderer.class */
public class FancySliderRenderer extends VanillaBackgroundRenderer {
    private static final int KNOB_COLOR = ColorFormat.ARGB32.pack(255, 255, 255, 150);

    public FancySliderRenderer() {
        this.name = "Slider";
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPost(widget, context);
        SliderWidget slider = (SliderWidget) widget;
        Rectangle bounds = slider.bounds().rectangle(BoundsType.MIDDLE);
        float offset = getOffset(slider, bounds, context.mouse());
        renderKnob(context, bounds.getLeft() + offset, bounds.getTop(), 8.0f, bounds.getHeight(), 4.0f);
    }

    protected float getOffset(SliderWidget slider, Rectangle bounds, MutableMouse mouse) {
        if (slider.isDragging()) {
            return MathHelper.clamp((mouse.getX() - bounds.getX()) - 4.0f, 0.0f, bounds.getWidth() - 8.0f);
        }
        return (bounds.getWidth() - 8.0f) * slider.getPercentage();
    }

    protected void renderKnob(ScreenContext context, float x, float y, float width, float height, float radius) {
        context.canvas().submitRelativeRoundedRect(x, y, width, height, KNOB_COLOR, RoundedData.builder().setRadius(radius).build());
    }
}

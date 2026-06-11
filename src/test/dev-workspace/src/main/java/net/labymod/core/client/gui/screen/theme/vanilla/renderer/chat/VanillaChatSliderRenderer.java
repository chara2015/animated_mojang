package net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.render.font.RenderableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/chat/VanillaChatSliderRenderer.class */
public class VanillaChatSliderRenderer extends ThemeRenderer<SliderWidget> {
    public VanillaChatSliderRenderer() {
        super("ChatSlider");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(SliderWidget widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        boolean hovered = widget.isHovered() || widget.isDragging();
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRect(bounds.getX(), bounds.getCenterY(), bounds.getWidth(), 1.0f, hovered ? -2130706433 : 1090519039);
        float offset = bounds.getWidth() * widget.getPercentage();
        renderState.submitRelativeRect((bounds.getX() + offset) - 2.0f, bounds.getY(), 4.0f, bounds.getHeight(), hovered ? -265080013 : -266198494);
        renderState.submitRenderableComponent(RenderableComponent.of(widget.component()), bounds.getCenterX(), bounds.getCenterY() - 4.0f, -1, 3);
    }
}

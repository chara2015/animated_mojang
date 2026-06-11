package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaSwitchRenderer.class */
public class VanillaSwitchRenderer extends VanillaButtonRenderer {
    private static final int STATE_WIDTH = 4;

    public VanillaSwitchRenderer() {
        super("Switch");
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        int iPack;
        int color;
        SwitchWidget switchWidget = (SwitchWidget) widget;
        Bounds bounds = widget.bounds();
        float buttonWidth = bounds.getWidth();
        renderTexture(context, bounds.rectangle(BoundsType.MIDDLE), false, widget.isHovered(), widget.backgroundColor().get().intValue());
        boolean enabled = switchWidget.getValue();
        float sliderX = enabled ? (bounds.getX() + bounds.getWidth()) - 8.0f : bounds.getX();
        Integer textHoverColor = switchWidget.textHoverColor().get();
        MutableRectangle mutableRectangleRelative = Rectangle.relative(sliderX, bounds.getY(), 8.0f, bounds.getHeight());
        boolean z = textHoverColor != null && widget.isHovered();
        if (enabled) {
            iPack = ColorFormat.ARGB32.pack(85, 255, 20);
        } else {
            iPack = ColorFormat.ARGB32.pack(255, 85, 20);
        }
        renderTexture(context, mutableRectangleRelative, true, z, iPack);
        if (widget.isHovered() && textHoverColor != null) {
            color = textHoverColor.intValue();
        } else {
            color = enabled ? NamedTextColor.WHITE.getValue() : NamedTextColor.GRAY.getValue();
        }
        float textX = enabled ? (buttonWidth - 4.0f) / 2.0f : ((buttonWidth - 4.0f) / 2.0f) + 6.0f;
        ScreenCanvas screenCanvas = context.canvas();
        float lineHeight = screenCanvas.getLineHeight();
        screenCanvas.submitText(switchWidget.getText(), bounds.getX() + textX, bounds.getY() + ((bounds.getHeight() - (lineHeight - 1.0f)) / 2.0f), color | (-16777216), 3);
    }
}

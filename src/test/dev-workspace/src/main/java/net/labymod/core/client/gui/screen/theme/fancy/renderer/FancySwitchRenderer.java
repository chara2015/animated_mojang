package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancySwitchRenderer.class */
public class FancySwitchRenderer extends VanillaBackgroundRenderer {
    public FancySwitchRenderer() {
        this.name = "Switch";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPre(widget, context);
        ScreenCanvas screenCanvas = context.canvas();
        SwitchWidget switchWidget = (SwitchWidget) widget;
        Rectangle rectangle = widget.bounds().rectangle(BoundsType.INNER);
        float stateWidth = rectangle.getHeight() - (2.0f * 2.0f);
        float left = rectangle.getLeft() + 2.0f;
        float right = (rectangle.getRight() - 2.0f) - stateWidth;
        long timePassed = TimeUtil.getMillis() - widget.getLastActionTime();
        float percentage = MathHelper.sigmoid(MathHelper.clamp(0.01f * timePassed, 0.0f, 1.0f));
        float directionalPercentage = switchWidget.getValue() ? 1.0f - percentage : percentage;
        float x = (left * directionalPercentage) + (right * (1.0f - directionalPercentage));
        boolean enabled = ((double) directionalPercentage) < 0.5d;
        screenCanvas.submitAbsoluteRoundedRect(x, rectangle.getTop() + 2.0f, x + stateWidth, rectangle.getBottom() - 2.0f, enabled ? -13202129 : -7524552, RoundedData.builder().setRadius(4.0f).build());
        float centerX = x + (stateWidth / 2.0f);
        float centerY = rectangle.getCenterY();
        if (enabled) {
            screenCanvas.submitAbsoluteRoundedRect(centerX - 0.75f, centerY - 3.0f, centerX + 0.75f, centerY + 3.0f, -3285300, RoundedData.builder().setRadius(1).build());
        } else {
            screenCanvas.submitCircle(centerX, centerY, 2.5f, -3285300);
        }
    }
}

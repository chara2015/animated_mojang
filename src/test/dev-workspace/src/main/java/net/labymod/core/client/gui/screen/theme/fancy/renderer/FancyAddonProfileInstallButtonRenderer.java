package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/renderer/FancyAddonProfileInstallButtonRenderer.class */
public class FancyAddonProfileInstallButtonRenderer extends FancyButtonRenderer {
    private static final int BUTTON_COLOR = ColorFormat.ARGB32.pack(0, 255, 0, 180);

    public FancyAddonProfileInstallButtonRenderer() {
        this.name = "AddonProfileInstallButton";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        LssVariable variable = widget.getVariable("--download-percentage");
        if (variable == null) {
            super.renderPre(widget, context);
            return;
        }
        String value = variable.value();
        try {
            float percentage = Float.parseFloat(value);
            Rectangle outer = widget.bounds().rectangle(BoundsType.OUTER);
            float outerWidth = outer.getWidth();
            float widthPerPercentage = outerWidth / 100.0f;
            float x = outer.getX();
            float y = outer.getY();
            float width = x + (widthPerPercentage * percentage);
            BorderRadius borderRadius = widget.getBorderRadius();
            RoundedData roundedData = null;
            if (borderRadius != null) {
                float leftTop = borderRadius.getLeftTop();
                float leftBottom = borderRadius.getLeftBottom();
                float finalLeftTop = leftTop / (leftTop * 3.0f);
                float finalLeftBottom = leftBottom / (leftBottom * 3.0f);
                roundedData = RoundedData.builder().applyBorderRadius(borderRadius).setLeftTopRadius(MathHelper.clamp(finalLeftTop * percentage, 0.0f, leftTop)).setRightTopRadius(0.0f).setLeftBottomRadius(MathHelper.clamp(finalLeftBottom * percentage, 0.0f, leftBottom)).setRightBottomRadius(0.0f).build();
            }
            ScreenCanvas screenCanvas = context.canvas();
            if (roundedData == null) {
                screenCanvas.submitAbsoluteRect(x, y, width, y + outer.getHeight(), BUTTON_COLOR);
            } else {
                screenCanvas.submitAbsoluteRoundedRect(x, y, width, y + outer.getHeight(), BUTTON_COLOR, roundedData);
            }
            super.renderPre(widget, context);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            super.renderPre(widget, context);
        }
    }
}

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/selector/HueSelectorWidget.class */
@AutoWidget
public class HueSelectorWidget extends SelectorWidget {
    public HueSelectorWidget(ColorData colorData) {
        super(colorData);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void renderSelector(Orientation orientation, ScreenContext context) {
        ScreenCanvas renderState = context.canvas();
        Bounds bounds = bounds();
        float width = bounds.getWidth();
        float step = width / 360.0f;
        int index = 0;
        if (orientation == Orientation.HORIZONTAL) {
            float f = 0.0f;
            while (true) {
                float x = f;
                if (x < width) {
                    renderState.submitAbsoluteGradientRect(GradientDirection.LEFT_TO_RIGHT, bounds.getLeft() + x, bounds.getTop(), bounds.getLeft() + x + step, bounds.getBottom(), Color.HSBtoRGB(0.0027777778f * (index - 1), 1.0f, 1.0f), Color.HSBtoRGB(0.0027777778f * index, 1.0f, 1.0f));
                    index++;
                    f = x + step;
                } else {
                    return;
                }
            }
        } else {
            float f2 = 0.0f;
            while (true) {
                float x2 = f2;
                if (x2 < width) {
                    renderState.submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, bounds.getLeft() + x2, bounds.getTop(), bounds.getLeft() + x2 + step, bounds.getBottom(), Color.HSBtoRGB(0.0027777778f * (index - 1), 1.0f, 1.0f), Color.HSBtoRGB(0.0027777778f * index, 1.0f, 1.0f));
                    index++;
                    f2 = x2 + step;
                } else {
                    return;
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void update(float posX, float posY) {
        updateMarkerPosition(posX, bounds().getHeight() / 2.0f);
        float hue = MathHelper.clamp((posX * 360.0f) / bounds().getWidth(), 0.0f, 360.0f);
        colorData().setHue((int) hue);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void updateMarkerPosition() {
        float mouseX = (colorData().getColor().getHue() / 360.0f) * bounds().getWidth();
        updateMarkerPosition(mouseX, bounds().getHeight() / 2.0f);
    }
}

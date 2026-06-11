package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/selector/ShadeSelectorWidget.class */
@AutoWidget
public class ShadeSelectorWidget extends SelectorWidget {
    private static final int WHITE_LEFT = ColorFormat.ARGB32.pack(255, 255, 255, 255);
    private static final int WHITE_RIGHT = ColorFormat.ARGB32.pack(255, 255, 255, 0);
    private static final int BLACK_TOP = ColorFormat.ARGB32.pack(0, 0, 0, 0);
    private static final int BLACK_BOTTOM = ColorFormat.ARGB32.pack(0, 0, 0, 255);

    public ShadeSelectorWidget(ColorData colorData) {
        super(colorData);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void renderSelector(Orientation orientation, ScreenContext context) {
        ScreenCanvas renderState = context.canvas();
        renderState.submitRect(bounds(), Color.HSBtoRGB(colorData().getColor().getHue(), 100, 100));
        renderState.submitGradientRect(GradientDirection.LEFT_TO_RIGHT, bounds(), WHITE_LEFT, WHITE_RIGHT);
        renderState.submitGradientRect(GradientDirection.TOP_TO_BOTTOM, bounds(), BLACK_TOP, BLACK_BOTTOM);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void update(float posX, float posY) {
        updateMarkerPosition(posX, posY);
        float saturation = MathHelper.clamp((posX * 100.0f) / bounds().getWidth(), 0.0f, 100.0f);
        float brightness = MathHelper.clamp((posY * 100.0f) / bounds().getHeight(), 0.0f, 100.0f);
        colorData().setSaturation((int) saturation);
        colorData().setBrightness((int) brightness);
        colorData().applySB();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget
    public void updateMarkerPosition() {
        Color color = colorData().getColor();
        float posX = (color.getSaturation() / 100.0f) * bounds().getWidth();
        float posY = (color.getBrightness() / 100.0f) * bounds().getHeight();
        updateMarkerPosition(posX, posY);
    }
}

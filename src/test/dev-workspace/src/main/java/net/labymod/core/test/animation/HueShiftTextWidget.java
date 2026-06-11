package net.labymod.core.test.animation;

import net.labymod.api.client.animation.KeyframeAnimation;
import net.labymod.api.client.animation.easing.Easings;
import net.labymod.api.client.animation.keyframe.AnimationChannel;
import net.labymod.api.client.animation.keyframe.Keyframe;
import net.labymod.api.client.animation.playback.LoopMode;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/HueShiftTextWidget.class */
@AutoWidget
public class HueShiftTextWidget extends SimpleWidget {
    private final String text;
    private final KeyframeAnimation animation;
    private final float fontSize;
    private final float saturation;
    private final float brightness;
    private final float spread;
    private final String[] charStrings;

    public HueShiftTextWidget(String text, long cycleDuration, float spread, float fontSize, float saturation, float brightness) {
        this.text = text;
        this.fontSize = fontSize;
        this.saturation = saturation;
        this.brightness = brightness;
        this.spread = spread;
        AnimationChannel<Float> hueOffset = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(cycleDuration, Float.valueOf(1.0f), Easings.LINEAR));
        this.animation = new KeyframeAnimation().addChannel("hue", hueOffset).loopMode(LoopMode.LOOP);
        this.charStrings = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            this.charStrings[i] = String.valueOf(text.charAt(i));
        }
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.animation.update();
        Float hueBase = (Float) this.animation.evaluate("hue");
        if (hueBase == null) {
            hueBase = Float.valueOf(0.0f);
        }
        ScreenCanvas canvas = context.canvas();
        float centerX = bounds().getCenterX(BoundsType.INNER);
        float centerY = bounds().getCenterY(BoundsType.INNER);
        float totalWidth = 0.0f;
        float[] charWidths = new float[this.text.length()];
        for (int i = 0; i < this.text.length(); i++) {
            charWidths[i] = canvas.getTextWidth(this.charStrings[i]) * this.fontSize;
            totalWidth += charWidths[i];
        }
        float x = centerX - (totalWidth / 2.0f);
        int length = this.text.length();
        for (int i2 = 0; i2 < length; i2++) {
            float hue = (hueBase.floatValue() + ((i2 / length) * this.spread)) % 1.0f;
            int color = Color.HSBtoRGB(hue, this.saturation, this.brightness);
            canvas.submitText(this.charStrings[i2], x, centerY, color, this.fontSize, 1);
            x += charWidths[i2];
        }
        super.renderWidget(context);
    }
}

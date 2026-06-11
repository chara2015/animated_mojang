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
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/WaveTextWidget.class */
@AutoWidget
public class WaveTextWidget extends SimpleWidget {
    private final String text;
    private final KeyframeAnimation animation;
    private final float waveAmplitude;
    private final float fontSize;
    private final int baseColor;
    private final String[] charStrings;
    private final String[] channelKeys;

    public WaveTextWidget(String text, float waveAmplitude, long waveDuration, long staggerDelay, float fontSize, int baseColor) {
        this.text = text;
        this.waveAmplitude = waveAmplitude;
        this.fontSize = fontSize;
        this.baseColor = baseColor;
        long totalDuration = waveDuration + (staggerDelay * ((long) (text.length() - 1)));
        this.animation = new KeyframeAnimation().loopMode(LoopMode.LOOP);
        for (int i = 0; i < text.length(); i++) {
            long offset = staggerDelay * ((long) i);
            AnimationChannel<Float> channel = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset + (waveDuration / 4), Float.valueOf(-1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + (waveDuration / 2), Float.valueOf(0.0f), Easings.EASE_IN)).addKeyframe(new Keyframe(offset + ((waveDuration * 3) / 4), Float.valueOf(0.3f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + waveDuration, Float.valueOf(0.0f), Easings.EASE_IN)).addKeyframe(new Keyframe(totalDuration, Float.valueOf(0.0f)));
            this.animation.addChannel("ch" + i, channel);
        }
        this.charStrings = new String[text.length()];
        this.channelKeys = new String[text.length()];
        for (int i2 = 0; i2 < text.length(); i2++) {
            this.charStrings[i2] = String.valueOf(text.charAt(i2));
            this.channelKeys[i2] = "ch" + i2;
        }
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.animation.update();
        ScreenCanvas canvas = context.canvas();
        float startX = bounds().getCenterX(BoundsType.INNER);
        float baseY = bounds().getCenterY(BoundsType.INNER);
        float totalWidth = 0.0f;
        float[] charWidths = new float[this.text.length()];
        for (int i = 0; i < this.text.length(); i++) {
            charWidths[i] = canvas.getTextWidth(this.charStrings[i]) * this.fontSize;
            totalWidth += charWidths[i];
        }
        float x = startX - (totalWidth / 2.0f);
        for (int i2 = 0; i2 < this.text.length(); i2++) {
            Float wave = (Float) this.animation.evaluate(this.channelKeys[i2]);
            float offsetY = wave != null ? wave.floatValue() * this.waveAmplitude : 0.0f;
            float hueShift = i2 / this.text.length();
            int charColor = shiftHue(this.baseColor, hueShift);
            canvas.submitText(this.charStrings[i2], x, baseY + offsetY, charColor, this.fontSize, 1);
            x += charWidths[i2];
        }
        super.renderWidget(context);
    }

    private int shiftHue(int argb, float shift) {
        int r = ColorFormat.ARGB32.red(argb);
        int g = ColorFormat.ARGB32.green(argb);
        int b = ColorFormat.ARGB32.blue(argb);
        int[] hsb = Color.RGBtoHSB(r, g, b);
        float hue = ((hsb[0] / 360.0f) + shift) % 1.0f;
        int rgb = Color.HSBtoRGB(hue, hsb[1] / 100.0f, hsb[2] / 100.0f);
        return ColorFormat.ARGB32.withAlpha(rgb, ColorFormat.ARGB32.alpha(argb));
    }
}

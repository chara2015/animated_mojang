package net.labymod.core.test.animation;

import net.labymod.api.client.animation.KeyframeAnimation;
import net.labymod.api.client.animation.easing.Easings;
import net.labymod.api.client.animation.keyframe.AnimationChannel;
import net.labymod.api.client.animation.keyframe.Keyframe;
import net.labymod.api.client.animation.playback.LoopMode;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/EqualizerWidget.class */
@AutoWidget
public class EqualizerWidget extends SimpleWidget {
    private static final float BAR_WIDTH = 4.0f;
    private static final float BAR_GAP = 2.0f;
    private final int barCount;
    private final float maxHeight;
    private final KeyframeAnimation animation = new KeyframeAnimation().loopMode(LoopMode.LOOP);
    private final String[] heightKeys;

    public EqualizerWidget(int barCount, float maxHeight) {
        this.barCount = barCount;
        this.maxHeight = maxHeight;
        for (int i = 0; i < barCount; i++) {
            long baseDuration = 300 + ((i * 137) % 400);
            float minHeight = 0.1f + (((i * 31) % 20) / 100.0f);
            float peak1 = 0.5f + (((i * 53) % 50) / 100.0f);
            float peak2 = 0.3f + (((i * 79) % 60) / 100.0f);
            AnimationChannel<Float> height = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(minHeight))).addKeyframe(new Keyframe(baseDuration / 4, Float.valueOf(peak1), Easings.EASE_OUT)).addKeyframe(new Keyframe(baseDuration / 2, Float.valueOf(minHeight + 0.1f), Easings.EASE_IN)).addKeyframe(new Keyframe((baseDuration * 3) / 4, Float.valueOf(peak2), Easings.EASE_OUT)).addKeyframe(new Keyframe(baseDuration, Float.valueOf(minHeight), Easings.EASE_IN));
            this.animation.addChannel("h" + i, height);
        }
        this.heightKeys = new String[barCount];
        for (int i2 = 0; i2 < barCount; i2++) {
            this.heightKeys[i2] = "h" + i2;
        }
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.animation.update();
        float totalWidth = (this.barCount * BAR_WIDTH) + ((this.barCount - 1) * BAR_GAP);
        float startX = bounds().getCenterX(BoundsType.INNER) - (totalWidth / BAR_GAP);
        float bottomY = bounds().getCenterY(BoundsType.INNER) + (this.maxHeight / BAR_GAP);
        for (int i = 0; i < this.barCount; i++) {
            Float heightFactor = (Float) this.animation.evaluate(this.heightKeys[i]);
            if (heightFactor == null) {
                heightFactor = Float.valueOf(0.1f);
            }
            float barHeight = heightFactor.floatValue() * this.maxHeight;
            float x = startX + (i * 6.0f);
            float y = bottomY - barHeight;
            float hue = 0.33f * (1.0f - heightFactor.floatValue());
            int color = ColorFormat.ARGB32.withAlpha(Color.HSBtoRGB(hue, 0.85f, 0.95f), 230);
            context.canvas().submitRelativeRect(x, y, BAR_WIDTH, barHeight, color);
        }
        super.renderWidget(context);
    }
}

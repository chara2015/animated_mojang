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
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/LoadingDotsWidget.class */
@AutoWidget
public class LoadingDotsWidget extends SimpleWidget {
    private final int dotCount;
    private final float radius;
    private final float dotSize;
    private final KeyframeAnimation animation = new KeyframeAnimation().loopMode(LoopMode.LOOP);
    private final int baseColor;
    private final String[] scaleKeys;
    private final String[] alphaKeys;

    public LoadingDotsWidget(int dotCount, float radius, float dotSize, long cycleDuration, int baseColor) {
        this.dotCount = dotCount;
        this.radius = radius;
        this.dotSize = dotSize;
        this.baseColor = baseColor;
        long stagger = cycleDuration / ((long) dotCount);
        for (int i = 0; i < dotCount; i++) {
            long offset = stagger * ((long) i);
            AnimationChannel<Float> scale = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.4f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.4f))).addKeyframe(new Keyframe(offset + (stagger / 2), Float.valueOf(1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + stagger, Float.valueOf(0.4f), Easings.EASE_IN)).addKeyframe(new Keyframe(cycleDuration, Float.valueOf(0.4f)));
            AnimationChannel<Float> alpha = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.3f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.3f))).addKeyframe(new Keyframe(offset + (stagger / 2), Float.valueOf(1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + stagger, Float.valueOf(0.3f), Easings.EASE_IN)).addKeyframe(new Keyframe(cycleDuration, Float.valueOf(0.3f)));
            this.animation.addChannel("s" + i, scale);
            this.animation.addChannel("a" + i, alpha);
        }
        this.scaleKeys = new String[dotCount];
        this.alphaKeys = new String[dotCount];
        for (int i2 = 0; i2 < dotCount; i2++) {
            this.scaleKeys[i2] = "s" + i2;
            this.alphaKeys[i2] = "a" + i2;
        }
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.animation.update();
        float cx = bounds().getCenterX(BoundsType.INNER);
        float cy = bounds().getCenterY(BoundsType.INNER);
        int r = ColorFormat.ARGB32.red(this.baseColor);
        int g = ColorFormat.ARGB32.green(this.baseColor);
        int b = ColorFormat.ARGB32.blue(this.baseColor);
        for (int i = 0; i < this.dotCount; i++) {
            double angle = ((6.283185307179586d * ((double) i)) / ((double) this.dotCount)) - 1.5707963267948966d;
            float dotX = cx + (((float) Math.cos(angle)) * this.radius);
            float dotY = cy + (((float) Math.sin(angle)) * this.radius);
            Float scale = (Float) this.animation.evaluate(this.scaleKeys[i]);
            Float alpha = (Float) this.animation.evaluate(this.alphaKeys[i]);
            if (scale == null) {
                scale = Float.valueOf(0.4f);
            }
            if (alpha == null) {
                alpha = Float.valueOf(0.3f);
            }
            float size = this.dotSize * scale.floatValue();
            int color = ColorFormat.ARGB32.pack(r, g, b, (int) (alpha.floatValue() * 255.0f));
            context.canvas().submitCircle(dotX, dotY, size / 2.0f, color);
        }
        super.renderWidget(context);
    }
}

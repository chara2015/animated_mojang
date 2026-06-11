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
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/FireWidget.class */
@AutoWidget
public class FireWidget extends SimpleWidget {
    private static final int PARTICLE_COUNT = 16;
    private final KeyframeAnimation animation = new KeyframeAnimation().loopMode(LoopMode.LOOP);
    private final String[] riseKeys;
    private final String[] driftKeys;
    private final String[] alphaKeys;
    private final String[] sizeKeys;

    public FireWidget() {
        for (int i = 0; i < 16; i++) {
            long lifetime = ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_DURATION + ((i * 67) % 300);
            long totalCycle = lifetime + 200;
            long offset = ((long) (i * 131)) % totalCycle;
            AnimationChannel<Float> rise = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset + lifetime, Float.valueOf(-1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(totalCycle, Float.valueOf(0.0f)));
            float sway = (((i * 43) % 20) - 10) / 10.0f;
            AnimationChannel<Float> drift = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset + lifetime, Float.valueOf(sway), Easings.EASE_IN_OUT)).addKeyframe(new Keyframe(totalCycle, Float.valueOf(0.0f)));
            AnimationChannel<Float> alpha = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset + (lifetime / 5), Float.valueOf(1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + lifetime, Float.valueOf(0.0f), Easings.EASE_IN)).addKeyframe(new Keyframe(totalCycle, Float.valueOf(0.0f)));
            AnimationChannel<Float> size = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset, Float.valueOf(0.0f))).addKeyframe(new Keyframe(offset + (lifetime / 5), Float.valueOf(1.0f), Easings.EASE_OUT)).addKeyframe(new Keyframe(offset + lifetime, Float.valueOf(0.3f), Easings.EASE_IN)).addKeyframe(new Keyframe(totalCycle, Float.valueOf(0.0f)));
            this.animation.addChannel("r" + i, rise);
            this.animation.addChannel("d" + i, drift);
            this.animation.addChannel("a" + i, alpha);
            this.animation.addChannel("z" + i, size);
        }
        this.riseKeys = new String[16];
        this.driftKeys = new String[16];
        this.alphaKeys = new String[16];
        this.sizeKeys = new String[16];
        for (int i2 = 0; i2 < 16; i2++) {
            this.riseKeys[i2] = "r" + i2;
            this.driftKeys[i2] = "d" + i2;
            this.alphaKeys[i2] = "a" + i2;
            this.sizeKeys[i2] = "z" + i2;
        }
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        int r;
        int g;
        int b;
        this.animation.update();
        float cx = bounds().getCenterX(BoundsType.INNER);
        float baseY = bounds().getBottom(BoundsType.INNER) - 8.0f;
        for (int i = 0; i < 16; i++) {
            Float rise = (Float) this.animation.evaluate(this.riseKeys[i]);
            Float drift = (Float) this.animation.evaluate(this.driftKeys[i]);
            Float alpha = (Float) this.animation.evaluate(this.alphaKeys[i]);
            Float size = (Float) this.animation.evaluate(this.sizeKeys[i]);
            if (rise != null && alpha != null && alpha.floatValue() > 0.01f) {
                if (drift == null) {
                    drift = Float.valueOf(0.0f);
                }
                if (size == null) {
                    size = Float.valueOf(0.0f);
                }
                float progress = -rise.floatValue();
                float x = cx + (drift.floatValue() * 12.0f);
                float y = baseY + (rise.floatValue() * 35.0f);
                float radius = 2.0f + (size.floatValue() * 3.0f);
                if (progress < 0.3f) {
                    float t = progress / 0.3f;
                    r = 255;
                    g = (int) (255.0f - (40.0f * t));
                    b = (int) (200.0f - (200.0f * t));
                } else if (progress < 0.7f) {
                    r = 255;
                    g = (int) (215.0f - (135.0f * ((progress - 0.3f) / 0.4f)));
                    b = 0;
                } else {
                    float t2 = (progress - 0.7f) / 0.3f;
                    r = (int) (255.0f - (55.0f * t2));
                    g = (int) (80.0f - (80.0f * t2));
                    b = 0;
                }
                int color = ColorFormat.ARGB32.pack(r, g, b, (int) (alpha.floatValue() * 220.0f));
                context.canvas().submitCircle(x, y, radius, color);
            }
        }
        super.renderWidget(context);
    }
}

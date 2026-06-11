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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/DNAHelixWidget.class */
@AutoWidget
public class DNAHelixWidget extends SimpleWidget {
    private static final int DOT_PAIRS = 10;
    private static final float DOT_RADIUS = 2.5f;
    private final KeyframeAnimation animation;
    private final float amplitude;
    private final float verticalSpacing;

    public DNAHelixWidget(float amplitude, float verticalSpacing, long rotateDuration) {
        this.amplitude = amplitude;
        this.verticalSpacing = verticalSpacing;
        AnimationChannel<Float> phase = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0L, Float.valueOf(0.0f))).addKeyframe(new Keyframe(rotateDuration, Float.valueOf(6.2831855f), Easings.LINEAR));
        this.animation = new KeyframeAnimation().addChannel("phase", phase).loopMode(LoopMode.LOOP);
        this.animation.play();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.animation.update();
        Float phase = (Float) this.animation.evaluate("phase");
        if (phase == null) {
            phase = Float.valueOf(0.0f);
        }
        float cx = bounds().getCenterX(BoundsType.INNER);
        float startY = bounds().getCenterY(BoundsType.INNER) - ((9.0f * this.verticalSpacing) / 2.0f);
        for (int i = 0; i < 10; i++) {
            float y = startY + (i * this.verticalSpacing);
            float dotPhase = phase.floatValue() + (i * 0.6f);
            float sinPhase = (float) Math.sin(dotPhase);
            float cosPhase = (float) Math.cos(dotPhase);
            float x1 = cx + (sinPhase * this.amplitude);
            float x2 = cx - (sinPhase * this.amplitude);
            float depth1 = (cosPhase + 1.0f) / 2.0f;
            float depth2 = ((-cosPhase) + 1.0f) / 2.0f;
            int rungAlpha = (int) (60.0d + (40.0d * Math.abs(Math.sin(dotPhase))));
            context.canvas().submitLine(x1, y, x2, y, 1.0f, ColorFormat.ARGB32.pack(150, 150, 180, rungAlpha));
            float r1 = DOT_RADIUS * (0.6f + (0.4f * depth1));
            int a1 = (int) (120.0f + (135.0f * depth1));
            context.canvas().submitCircle(x1, y, r1, ColorFormat.ARGB32.pack(80, 200, 255, a1));
            float r2 = DOT_RADIUS * (0.6f + (0.4f * depth2));
            int a2 = (int) (120.0f + (135.0f * depth2));
            context.canvas().submitCircle(x2, y, r2, ColorFormat.ARGB32.pack(255, 100, 200, a2));
        }
        super.renderWidget(context);
    }
}

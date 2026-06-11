package net.labymod.api.client.animation.easing;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/easing/Easings.class */
public final class Easings {
    public static final EasingFunction LINEAR = t -> {
        return t;
    };
    public static final EasingFunction STEP = t -> {
        return t < 1.0d ? 0.0d : 1.0d;
    };
    public static final EasingFunction EASE_IN = fromCubicBezier(CubicBezier.EASE_IN);
    public static final EasingFunction EASE_OUT = fromCubicBezier(CubicBezier.EASE_OUT);
    public static final EasingFunction EASE_IN_OUT = fromCubicBezier(CubicBezier.EASE_IN_OUT);
    public static final EasingFunction BOUNCE = fromCubicBezier(CubicBezier.BOUNCE);

    private Easings() {
    }

    public static EasingFunction fromCubicBezier(CubicBezier bezier) {
        Objects.requireNonNull(bezier);
        return bezier::curve;
    }

    public static EasingFunction cubicBezier(double p1x, double p1y, double p2x, double p2y) {
        return fromCubicBezier(new CubicBezier(p1x, p1y, p2x, p2y));
    }
}

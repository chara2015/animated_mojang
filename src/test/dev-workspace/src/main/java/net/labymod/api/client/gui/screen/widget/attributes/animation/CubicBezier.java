package net.labymod.api.client.gui.screen.widget.attributes.animation;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/CubicBezier.class */
public class CubicBezier {
    public static final CubicBezier LINEAR = new CubicBezier("linear", 0.0d, 0.0d, 1.0d, 1.0d);
    public static final CubicBezier EASE_IN_OUT = new CubicBezier("ease-in-out", 0.5d, 0.0d, 0.5d, 1.0d);
    public static final CubicBezier EASE_IN = new CubicBezier("ease-in", 0.5d, 0.0d, 1.0d, 1.0d);
    public static final CubicBezier EASE_OUT = new CubicBezier("ease-out", 0.0d, 0.0d, 0.5d, 1.0d);
    public static final CubicBezier BOUNCE = new CubicBezier("bounce", 0.34d, 1.36d, 0.51d, 1.16d);
    private static final CubicBezier[] VALUES = {LINEAR, EASE_IN_OUT, EASE_IN, EASE_OUT};
    private static final double EPSILON = 1.0E-6d;
    private final String name;
    private final double cx;
    private final double bx;
    private final double ax;
    private final double cy;
    private final double by;
    private final double ay;

    public CubicBezier(double p0, double p1, double p2, double p3) {
        this("custom", p0, p1, p2, p3);
    }

    public CubicBezier(String name, double p1x, double p1y, double p2x, double p2y) {
        this.name = name;
        this.cx = 3.0d * p1x;
        this.bx = (3.0d * (p2x - p1x)) - this.cx;
        this.ax = (1.0d - this.cx) - this.bx;
        this.cy = 3.0d * p1y;
        this.by = (3.0d * (p2y - p1y)) - this.cy;
        this.ay = (1.0d - this.cy) - this.by;
    }

    public double curve(double t) {
        return solve(t, EPSILON);
    }

    private double solve(double x, double epsilon) {
        return sampleCurveY(solveCurveX(x, epsilon));
    }

    private double solveCurveX(double x, double epsilon) {
        double t2 = x;
        double d = 0.0d;
        while (true) {
            double i = d;
            if (i >= 8.0d) {
                break;
            }
            double x2 = sampleCurveX(t2) - x;
            if (Math.abs(x2) < epsilon) {
                return t2;
            }
            double d2 = sampleCurveDerivativeX(t2);
            if (Math.abs(d2) < epsilon) {
                break;
            }
            t2 -= x2 / d2;
            d = i + 1.0d;
        }
        double t0 = 0.0d;
        double t1 = 1.0d;
        double t22 = x;
        if (t22 < 0.0d) {
            return 0.0d;
        }
        if (t22 > 1.0d) {
            return 1.0d;
        }
        while (t0 < t1) {
            double x22 = sampleCurveX(t22);
            if (Math.abs(x22 - x) < epsilon) {
                return t22;
            }
            if (x > x22) {
                t0 = t22;
            } else {
                t1 = t22;
            }
            t22 = ((t1 - t0) * 0.5d) + t0;
        }
        return t22;
    }

    private double sampleCurveX(double t) {
        return ((((this.ax * t) + this.bx) * t) + this.cx) * t;
    }

    private double sampleCurveY(double t) {
        return ((((this.ay * t) + this.by) * t) + this.cy) * t;
    }

    private double sampleCurveDerivativeX(double t) {
        return (((3.0d * this.ax * t) + (2.0d * this.bx)) * t) + this.cx;
    }

    public double interpolate(double fromValue, double toValue, long fromMillis, long toMillis, long currentMillis) {
        double progressPercentage = MathHelper.clamp((currentMillis - fromMillis) / (toMillis - fromMillis), 0.0d, 1.0d);
        return fromValue + ((toValue - fromValue) * curve(progressPercentage));
    }

    public float interpolate(float fromValue, float toValue, long fromMillis, long toMillis, long currentMillis) {
        double progressPercentage = MathHelper.clamp((currentMillis - fromMillis) / (toMillis - fromMillis), 0.0d, 1.0d);
        return fromValue + ((toValue - fromValue) * ((float) curve(progressPercentage)));
    }

    public FloatVector3 interpolateVector(FloatVector3 fromVector, FloatVector3 toVector, long fromMillis, long toMillis, long currentMillis) {
        FloatVector3 fromVector2 = new FloatVector3(fromVector);
        FloatVector3 toVector2 = new FloatVector3(toVector);
        double progressPercentage = MathHelper.clamp((currentMillis - fromMillis) / (toMillis - fromMillis), 0.0d, 1.0d);
        return fromVector2.add(toVector2.sub(fromVector2).multiply((float) curve(progressPercentage)));
    }

    public String getName() {
        return this.name;
    }

    public static CubicBezier[] named() {
        return VALUES;
    }

    @Nullable
    public static CubicBezier of(String value) {
        if (value == null) {
            return null;
        }
        for (CubicBezier cubicBezier : VALUES) {
            if (value.equals(cubicBezier.getName())) {
                return cubicBezier;
            }
        }
        return null;
    }
}

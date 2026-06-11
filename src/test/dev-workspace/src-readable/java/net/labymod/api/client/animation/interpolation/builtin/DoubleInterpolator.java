package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.CatmullRomInterpolator;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/DoubleInterpolator.class */
public class DoubleInterpolator implements CatmullRomInterpolator<Double> {
    public static final DoubleInterpolator INSTANCE = new DoubleInterpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public Double interpolate(Double from, Double to, double t, Double result) {
        return Double.valueOf(from.doubleValue() + ((to.doubleValue() - from.doubleValue()) * t));
    }

    @Override // net.labymod.api.client.animation.interpolation.CatmullRomInterpolator
    public Double catmullRom(Double p0, Double p1, Double p2, Double p3, double t, Double result) {
        return Double.valueOf(MathHelper.catmullrom((float) t, p0.floatValue(), p1.floatValue(), p2.floatValue(), p3.floatValue()));
    }
}

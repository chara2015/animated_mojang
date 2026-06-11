package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.CatmullRomInterpolator;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/FloatInterpolator.class */
public class FloatInterpolator implements CatmullRomInterpolator<Float> {
    public static final FloatInterpolator INSTANCE = new FloatInterpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public Float interpolate(Float from, Float to, double t, Float result) {
        return Float.valueOf((float) (((double) from.floatValue()) + (((double) (to.floatValue() - from.floatValue())) * t)));
    }

    @Override // net.labymod.api.client.animation.interpolation.CatmullRomInterpolator
    public Float catmullRom(Float p0, Float p1, Float p2, Float p3, double t, Float result) {
        return Float.valueOf(MathHelper.catmullrom((float) t, p0.floatValue(), p1.floatValue(), p2.floatValue(), p3.floatValue()));
    }
}

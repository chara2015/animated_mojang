package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.TypeInterpolator;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/FloatVector2Interpolator.class */
public class FloatVector2Interpolator implements TypeInterpolator<FloatVector2> {
    public static final FloatVector2Interpolator INSTANCE = new FloatVector2Interpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public FloatVector2 interpolate(FloatVector2 from, FloatVector2 to, double t, FloatVector2 result) {
        float x = (float) (((double) from.getX()) + (((double) (to.getX() - from.getX())) * t));
        float y = (float) (((double) from.getY()) + (((double) (to.getY() - from.getY())) * t));
        if (result != null) {
            result.set(x, y);
            return result;
        }
        return new FloatVector2(x, y);
    }
}

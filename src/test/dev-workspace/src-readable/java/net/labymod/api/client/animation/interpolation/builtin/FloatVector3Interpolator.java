package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.CatmullRomInterpolator;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/FloatVector3Interpolator.class */
public class FloatVector3Interpolator implements CatmullRomInterpolator<FloatVector3> {
    public static final FloatVector3Interpolator INSTANCE = new FloatVector3Interpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public FloatVector3 interpolate(FloatVector3 from, FloatVector3 to, double t, FloatVector3 result) {
        float x = (float) (((double) from.getX()) + (((double) (to.getX() - from.getX())) * t));
        float y = (float) (((double) from.getY()) + (((double) (to.getY() - from.getY())) * t));
        float z = (float) (((double) from.getZ()) + (((double) (to.getZ() - from.getZ())) * t));
        if (result != null) {
            result.set(x, y, z);
            return result;
        }
        return new FloatVector3(x, y, z);
    }

    @Override // net.labymod.api.client.animation.interpolation.CatmullRomInterpolator
    public FloatVector3 catmullRom(FloatVector3 p0, FloatVector3 p1, FloatVector3 p2, FloatVector3 p3, double t, FloatVector3 result) {
        float ft = (float) t;
        float x = MathHelper.catmullrom(ft, p0.getX(), p1.getX(), p2.getX(), p3.getX());
        float y = MathHelper.catmullrom(ft, p0.getY(), p1.getY(), p2.getY(), p3.getY());
        float z = MathHelper.catmullrom(ft, p0.getZ(), p1.getZ(), p2.getZ(), p3.getZ());
        if (result != null) {
            result.set(x, y, z);
            return result;
        }
        return new FloatVector3(x, y, z);
    }
}

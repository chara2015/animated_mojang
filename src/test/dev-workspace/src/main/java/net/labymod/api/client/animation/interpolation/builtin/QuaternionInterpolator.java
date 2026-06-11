package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.TypeInterpolator;
import net.labymod.api.util.math.Quaternion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/QuaternionInterpolator.class */
public class QuaternionInterpolator implements TypeInterpolator<Quaternion> {
    public static final QuaternionInterpolator INSTANCE = new QuaternionInterpolator();

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public Quaternion interpolate(Quaternion from, Quaternion to, double t, Quaternion result) {
        float s0;
        float s1;
        float ax = from.getX();
        float ay = from.getY();
        float az = from.getZ();
        float aw = from.getW();
        float bx = to.getX();
        float by = to.getY();
        float bz = to.getZ();
        float bw = to.getW();
        float dot = (ax * bx) + (ay * by) + (az * bz) + (aw * bw);
        if (dot < 0.0f) {
            bx = -bx;
            by = -by;
            bz = -bz;
            bw = -bw;
            dot = -dot;
        }
        if (dot > 0.9995f) {
            s0 = (float) (1.0d - t);
            s1 = (float) t;
        } else {
            float angle = (float) Math.acos(dot);
            float sinAngle = (float) Math.sin(angle);
            s0 = (float) (Math.sin((1.0d - t) * ((double) angle)) / ((double) sinAngle));
            s1 = (float) (Math.sin(t * ((double) angle)) / ((double) sinAngle));
        }
        float rx = (s0 * ax) + (s1 * bx);
        float ry = (s0 * ay) + (s1 * by);
        float rz = (s0 * az) + (s1 * bz);
        float rw = (s0 * aw) + (s1 * bw);
        if (result != null) {
            result.set(rx, ry, rz, rw);
            return result;
        }
        return new Quaternion(rx, ry, rz, rw);
    }
}

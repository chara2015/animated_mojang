package net.labymod.api.util.math.position;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/position/Position.class */
public interface Position {
    double getX();

    void setX(double d);

    double getY();

    void setY(double d);

    double getZ();

    void setZ(double d);

    default double distanceSquared(Position other) {
        return MathHelper.distanceSquared(getX(), getY(), getZ(), other.getX(), other.getY(), other.getZ());
    }

    default double distanceSquared(double otherX, double otherY, double otherZ) {
        return MathHelper.distanceSquared(getX(), getY(), getZ(), otherX, otherY, otherZ);
    }

    default FloatVector3 toFloatVector3() {
        return new FloatVector3((float) getX(), (float) getY(), (float) getZ());
    }

    default DoubleVector3 toDoubleVector3() {
        return new DoubleVector3(getX(), getY(), getZ());
    }

    default double lerpX(Position previous, float delta) {
        return MathHelper.lerp(getX(), previous.getX(), delta);
    }

    default double lerpY(Position previous, float delta) {
        return MathHelper.lerp(getY(), previous.getY(), delta);
    }

    default double lerpZ(Position previous, float delta) {
        return MathHelper.lerp(getZ(), previous.getZ(), delta);
    }
}

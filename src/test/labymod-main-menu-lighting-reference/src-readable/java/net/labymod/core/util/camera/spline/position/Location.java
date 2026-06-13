package net.labymod.core.util.camera.spline.position;

import net.labymod.api.util.math.vector.DoubleVector3;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/camera/spline/position/Location.class */
public class Location {
    private final DoubleVector3 position;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private double roll;

    public Location() {
        this(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
    }

    public Location(double x, double y, double z) {
        this(x, y, z, 0.0d, 0.0d, 0.0d);
    }

    public Location(double x, double y, double z, double yaw, double pitch, double roll) {
        this.position = new DoubleVector3();
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.position.set((float) x, (float) y, (float) z);
    }

    public void setX(double x) {
        this.x = x;
        this.position.setZ((float) x);
    }

    public void setY(double y) {
        this.y = y;
        this.position.setZ((float) y);
    }

    public void setZ(double z) {
        this.z = z;
        this.position.setZ((float) z);
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public void addPosition(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.position.add(x, y, z);
    }

    public void addRotation(double yaw, double pitch, double roll) {
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
    }

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.position.set(x, y, z);
    }

    public void setRotation(double yaw, double pitch, double roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public DoubleVector3 position() {
        return this.position;
    }

    public Quaternionf rotation() {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateZYX((float) this.roll, (float) this.yaw, (float) this.pitch);
        return quaternionf;
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    public double getRoll() {
        return this.roll;
    }

    public String toString() {
        double d = this.x;
        double d2 = this.y;
        double d3 = this.z;
        double d4 = this.yaw;
        double d5 = this.pitch;
        double d6 = this.roll;
        return d + ", " + d + ", " + d2 + ", " + d + ", " + d3 + ", " + d;
    }
}

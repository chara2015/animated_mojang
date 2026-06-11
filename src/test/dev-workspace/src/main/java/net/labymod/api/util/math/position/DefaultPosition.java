package net.labymod.api.util.math.position;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/position/DefaultPosition.class */
public class DefaultPosition implements Position {
    private double x;
    private double y;
    private double z;

    public DefaultPosition() {
        this(0.0d, 0.0d, 0.0d);
    }

    public DefaultPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getX() {
        return this.x;
    }

    @Override // net.labymod.api.util.math.position.Position
    public void setX(double x) {
        this.x = x;
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getY() {
        return this.y;
    }

    @Override // net.labymod.api.util.math.position.Position
    public void setY(double y) {
        this.y = y;
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getZ() {
        return this.z;
    }

    @Override // net.labymod.api.util.math.position.Position
    public void setZ(double z) {
        this.z = z;
    }
}

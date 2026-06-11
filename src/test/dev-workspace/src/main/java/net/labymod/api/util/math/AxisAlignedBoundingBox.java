package net.labymod.api.util.math;

import java.util.StringJoiner;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/AxisAlignedBoundingBox.class */
public class AxisAlignedBoundingBox {
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public AxisAlignedBoundingBox() {
        this(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    public AxisAlignedBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
    }

    public AxisAlignedBoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBoundingBox(@NotNull FloatVector3 vector) {
        this(vector, new FloatVector3(vector).add(FloatVector3.ONE));
    }

    public AxisAlignedBoundingBox(@NotNull DoubleVector3 min, @NotNull DoubleVector3 max) {
        this(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    public AxisAlignedBoundingBox(@NotNull FloatVector3 minVector, @NotNull FloatVector3 maxVector) {
        this(minVector.getX(), minVector.getY(), minVector.getZ(), maxVector.getX(), maxVector.getY(), maxVector.getZ());
    }

    @NotNull
    public AxisAlignedBoundingBox move(double x, double y, double z) {
        return new AxisAlignedBoundingBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    @NotNull
    public AxisAlignedBoundingBox move(@NotNull DoubleVector3 vector) {
        return move((float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
    }

    @NotNull
    public AxisAlignedBoundingBox move(@NotNull FloatVector3 vector) {
        return move(vector.getX(), vector.getY(), vector.getZ());
    }

    @NotNull
    public AxisAlignedBoundingBox move(@NotNull IntVector3 vector) {
        return move(vector.getX(), vector.getY(), vector.getZ());
    }

    public void moveSelf(float x, float y, float z) {
        set(getMinX() + ((double) x), getMinY() + ((double) y), getMinZ() + ((double) z), getMaxX() + ((double) x), getMaxY() + ((double) y), getMaxZ() + ((double) z));
    }

    public void moveSelf(@NotNull FloatVector3 other) {
        moveSelf(other.getX(), other.getY(), other.getZ());
    }

    public void moveSelf(@NotNull IntVector3 other) {
        moveSelf(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public AxisAlignedBoundingBox minX(double minX) {
        return new AxisAlignedBoundingBox(minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    @NotNull
    public AxisAlignedBoundingBox minY(double minY) {
        return new AxisAlignedBoundingBox(this.minX, minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    @NotNull
    public AxisAlignedBoundingBox minZ(double minZ) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, minZ, this.maxX, this.maxY, this.maxZ);
    }

    @NotNull
    public AxisAlignedBoundingBox maxX(double maxX) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, maxX, this.maxY, this.maxZ);
    }

    @NotNull
    public AxisAlignedBoundingBox maxY(double maxY) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, this.maxX, maxY, this.maxZ);
    }

    @NotNull
    public AxisAlignedBoundingBox maxZ(double maxZ) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, maxZ);
    }

    public void set(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void set(AxisAlignedBoundingBox other) {
        set(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    public AxisAlignedBoundingBox expandTowards(FloatVector3 vector) {
        return expandTowards(vector.getX(), vector.getY(), vector.getZ());
    }

    public AxisAlignedBoundingBox expandTowards(double x, double y, double z) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;
        if (x < 0.0d) {
            minX += x;
        } else {
            maxX += x;
        }
        if (y < 0.0d) {
            minY += y;
        } else {
            maxY += y;
        }
        if (z < 0.0d) {
            minZ += z;
        } else {
            maxZ += z;
        }
        return new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public double getXSize() {
        return this.maxX - this.minX;
    }

    public double getYSize() {
        return this.maxY - this.minY;
    }

    public double getZSize() {
        return this.maxZ - this.minZ;
    }

    public DoubleVector3 getCenter() {
        return new DoubleVector3(MathHelper.lerp(this.minX, this.maxX, 0.5f), MathHelper.lerp(this.minY, this.maxY, 0.5f), MathHelper.lerp(this.minZ, this.maxZ, 0.5f));
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    public double getXWidth() {
        return this.maxX - this.minX;
    }

    public double getZWidth() {
        return this.maxZ - this.minZ;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public boolean intersects(AxisAlignedBoundingBox other) {
        return intersects(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    public boolean intersects(FloatVector3 min, FloatVector3 max) {
        return intersects(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()), Math.min(min.getZ(), max.getZ()), Math.max(min.getX(), max.getX()), Math.max(min.getY(), max.getY()), Math.max(min.getZ(), max.getZ()));
    }

    public boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
    }

    public AxisAlignedBoundingBox inflate(double delta) {
        return inflate(delta, delta, delta);
    }

    public AxisAlignedBoundingBox inflate(double deltaX, double deltaY, double deltaZ) {
        double minX = this.minX - deltaX;
        double minY = this.minY - deltaY;
        double minZ = this.minZ - deltaZ;
        double maxX = this.maxX + deltaX;
        double maxY = this.maxY + deltaY;
        double maxZ = this.maxZ + deltaZ;
        return new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean rayIntersects(FloatVector3 origin, FloatVector3 direction) {
        double tmin = Double.NEGATIVE_INFINITY;
        double tmax = Double.POSITIVE_INFINITY;
        double[] o = {origin.getX(), origin.getY(), origin.getZ()};
        double[] d = {direction.getX(), direction.getY(), direction.getZ()};
        double[] aabbMin = {this.minX, this.minY, this.minZ};
        double[] aabbMax = {this.maxX, this.maxY, this.maxZ};
        for (int i = 0; i < 3; i++) {
            if (Math.abs(d[i]) >= 1.0E-6d) {
                double t1 = (aabbMin[i] - o[i]) / d[i];
                double t2 = (aabbMax[i] - o[i]) / d[i];
                if (t1 > t2) {
                    t1 = t2;
                    t2 = t1;
                }
                tmin = Math.max(tmin, t1);
                tmax = Math.min(tmax, t2);
                if (tmin > tmax) {
                    return false;
                }
            } else if (o[i] < aabbMin[i] || o[i] > aabbMax[i]) {
                return false;
            }
        }
        return tmax >= 0.0d;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public double collide(Axis axis, AxisAlignedBoundingBox other, double value) throws MatchException {
        switch (axis) {
            case X:
                return collideX(other, value);
            case Y:
                return collideY(other, value);
            case Z:
                return collideZ(other, value);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private double collideX(AxisAlignedBoundingBox other, double x) {
        if (isCollidingY(other)) {
            return x;
        }
        if (isCollidingZ(other)) {
            return x;
        }
        if (x > 0.0d && other.getMaxX() <= getMinX()) {
            double maxX = getMinX() - other.getMaxX();
            if (maxX < x) {
                x = maxX;
            }
        }
        if (x < 0.0d && other.getMinX() >= getMaxX()) {
            double maxX2 = getMaxX() - other.getMinX();
            if (maxX2 > x) {
                x = maxX2;
            }
        }
        return x;
    }

    private double collideY(AxisAlignedBoundingBox other, double y) {
        if (isCollidingX(other)) {
            return y;
        }
        if (isCollidingZ(other)) {
            return y;
        }
        if (y > 0.0d && other.getMaxY() <= getMinY()) {
            double maxY = getMinY() - other.getMaxY();
            if (maxY < y) {
                y = maxY;
            }
        }
        if (y < 0.0d && other.getMinY() >= getMaxY()) {
            double maxY2 = getMaxY() - other.getMinY();
            if (maxY2 > y) {
                y = maxY2;
            }
        }
        return y;
    }

    private double collideZ(AxisAlignedBoundingBox other, double z) {
        if (isCollidingY(other)) {
            return z;
        }
        if (isCollidingX(other)) {
            return z;
        }
        if (z > 0.0d && other.getMaxZ() <= getMinZ()) {
            double maxZ = getMinZ() - other.getMaxZ();
            if (maxZ < z) {
                z = maxZ;
            }
        }
        if (z < 0.0d && other.getMinZ() >= getMaxZ()) {
            double maxZ2 = getMaxZ() - other.getMinZ();
            if (maxZ2 > z) {
                z = maxZ2;
            }
        }
        return z;
    }

    private boolean isCollidingX(AxisAlignedBoundingBox other) {
        return other.getMaxX() <= getMinX() || other.getMinX() >= getMaxX();
    }

    private boolean isCollidingY(AxisAlignedBoundingBox other) {
        return other.getMaxY() <= getMinY() || other.getMinY() >= getMaxY();
    }

    private boolean isCollidingZ(AxisAlignedBoundingBox other) {
        return other.getMaxZ() <= getMinZ() || other.getMinZ() >= getMaxZ();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AxisAlignedBoundingBox box = (AxisAlignedBoundingBox) o;
        return Double.compare(this.minX, box.minX) == 0 && Double.compare(this.minY, box.minY) == 0 && Double.compare(this.minZ, box.minZ) == 0 && Double.compare(this.maxX, box.maxX) == 0 && Double.compare(this.maxY, box.maxY) == 0 && Double.compare(this.maxZ, box.maxZ) == 0;
    }

    public int hashCode() {
        int result = Double.hashCode(this.minX);
        return (31 * ((31 * ((31 * ((31 * ((31 * result) + Double.hashCode(this.minY))) + Double.hashCode(this.minZ))) + Double.hashCode(this.maxX))) + Double.hashCode(this.maxY))) + Double.hashCode(this.maxZ);
    }

    public String toString() {
        return new StringJoiner(", ", AxisAlignedBoundingBox.class.getSimpleName() + "[", "]").add("minX=" + this.minX).add("minY=" + this.minY).add("minZ=" + this.minZ).add("maxX=" + this.maxX).add("maxY=" + this.maxY).add("maxZ=" + this.maxZ).toString();
    }
}

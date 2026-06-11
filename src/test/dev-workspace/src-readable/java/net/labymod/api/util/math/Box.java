package net.labymod.api.util.math;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/Box.class */
public final class Box {
    private final float minX;
    private final float minY;
    private final float minZ;
    private final float maxX;
    private final float maxY;
    private final float maxZ;

    public Box(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public String toString() {
        return "Box{minX=" + this.minX + ", minY=" + this.minY + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", maxZ=" + this.maxZ + "} ";
    }

    public float minX() {
        return this.minX;
    }

    public float minY() {
        return this.minY;
    }

    public float minZ() {
        return this.minZ;
    }

    public float maxX() {
        return this.maxX;
    }

    public float maxY() {
        return this.maxY;
    }

    public float maxZ() {
        return this.maxZ;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Box that = (Box) obj;
        return Float.floatToIntBits(this.minX) == Float.floatToIntBits(that.minX) && Float.floatToIntBits(this.minY) == Float.floatToIntBits(that.minY) && Float.floatToIntBits(this.minZ) == Float.floatToIntBits(that.minZ) && Float.floatToIntBits(this.maxX) == Float.floatToIntBits(that.maxX) && Float.floatToIntBits(this.maxY) == Float.floatToIntBits(that.maxY) && Float.floatToIntBits(this.maxZ) == Float.floatToIntBits(that.maxZ);
    }

    public int hashCode() {
        int result = this.minX != 0.0f ? Float.floatToIntBits(this.minX) : 0;
        return (31 * ((31 * ((31 * ((31 * ((31 * result) + (this.minY != 0.0f ? Float.floatToIntBits(this.minY) : 0))) + (this.minZ != 0.0f ? Float.floatToIntBits(this.minZ) : 0))) + (this.maxX != 0.0f ? Float.floatToIntBits(this.maxX) : 0))) + (this.maxY != 0.0f ? Float.floatToIntBits(this.maxY) : 0))) + (this.maxZ != 0.0f ? Float.floatToIntBits(this.maxZ) : 0);
    }
}

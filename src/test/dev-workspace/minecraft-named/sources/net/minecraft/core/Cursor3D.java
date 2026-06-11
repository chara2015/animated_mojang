package net.minecraft.core;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/Cursor3D.class */
public class Cursor3D {
    public static final int TYPE_INSIDE = 0;
    public static final int TYPE_FACE = 1;
    public static final int TYPE_EDGE = 2;
    public static final int TYPE_CORNER = 3;
    private final int originX;
    private final int originY;
    private final int originZ;
    private final int width;
    private final int height;
    private final int depth;
    private final int end;
    private int index;
    private int x;
    private int y;
    private int z;

    public Cursor3D(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        this.originX = $$0;
        this.originY = $$1;
        this.originZ = $$2;
        this.width = ($$3 - $$0) + 1;
        this.height = ($$4 - $$1) + 1;
        this.depth = ($$5 - $$2) + 1;
        this.end = this.width * this.height * this.depth;
    }

    public boolean advance() {
        if (this.index == this.end) {
            return false;
        }
        this.x = this.index % this.width;
        int $$0 = this.index / this.width;
        this.y = $$0 % this.height;
        this.z = $$0 / this.height;
        this.index++;
        return true;
    }

    public int nextX() {
        return this.originX + this.x;
    }

    public int nextY() {
        return this.originY + this.y;
    }

    public int nextZ() {
        return this.originZ + this.z;
    }

    public int getNextType() {
        int $$0 = 0;
        if (this.x == 0 || this.x == this.width - 1) {
            $$0 = 0 + 1;
        }
        if (this.y == 0 || this.y == this.height - 1) {
            $$0++;
        }
        if (this.z == 0 || this.z == this.depth - 1) {
            $$0++;
        }
        return $$0;
    }
}

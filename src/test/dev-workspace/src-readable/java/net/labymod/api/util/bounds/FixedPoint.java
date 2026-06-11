package net.labymod.api.util.bounds;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/FixedPoint.class */
public class FixedPoint implements Point {
    private final int x;
    private final int y;

    FixedPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override // net.labymod.api.util.bounds.Point
    public int getX() {
        return this.x;
    }

    @Override // net.labymod.api.util.bounds.Point
    public int getY() {
        return this.y;
    }
}

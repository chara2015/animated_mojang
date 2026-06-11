package net.labymod.api.util.bounds;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/Point.class */
public interface Point {
    int getX();

    int getY();

    default boolean matches(Point point) {
        return getX() == point.getX() && getY() == point.getY();
    }

    static Point fixed(int x, int y) {
        return new FixedPoint(x, y);
    }

    default Point add(int dx, int dy) {
        return fixed(getX() + dx, getY() + dy);
    }

    default Point subtract(int dx, int dy) {
        return add(-dx, -dy);
    }
}

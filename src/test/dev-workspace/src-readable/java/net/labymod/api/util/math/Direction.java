package net.labymod.api.util.math;

import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/Direction.class */
public enum Direction {
    DOWN(0, 1, Axis.Y, new Vector3f(0.0f, -1.0f, 0.0f)),
    UP(1, 0, Axis.Y, new Vector3f(0.0f, 1.0f, 0.0f)),
    NORTH(2, 3, Axis.Z, new Vector3f(0.0f, 0.0f, -1.0f)),
    SOUTH(3, 2, Axis.Z, new Vector3f(0.0f, 0.0f, 1.0f)),
    WEST(4, 5, Axis.X, new Vector3f(-1.0f, 0.0f, 0.0f)),
    EAST(5, 4, Axis.X, new Vector3f(1.0f, 0.0f, 0.0f));

    public static final Direction[] VALUES = values();
    private final int index;
    private final int oppositeIndex;
    private final Axis axis;
    private final Vector3f normal;

    Direction(int index, int oppositeIndex, Axis axis, Vector3f normal) {
        this.index = index;
        this.oppositeIndex = oppositeIndex;
        this.axis = axis;
        this.normal = normal;
    }

    public int getIndex() {
        return this.index;
    }

    public int getOppositeIndex() {
        return this.oppositeIndex;
    }

    public Direction getOpposite() {
        return VALUES[this.oppositeIndex];
    }

    public Axis getAxis() {
        return this.axis;
    }

    public Vector3f getNormal() {
        return this.normal;
    }

    public static Direction byIndex(int index) {
        return byIndex(index, NORTH);
    }

    public static Direction byIndex(int index, Direction defaultValue) {
        for (Direction direction : VALUES) {
            if (direction.getIndex() == index) {
                return direction;
            }
        }
        return defaultValue;
    }
}

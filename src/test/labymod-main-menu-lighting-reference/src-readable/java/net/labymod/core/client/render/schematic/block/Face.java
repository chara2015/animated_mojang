package net.labymod.core.client.render.schematic.block;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/Face.class */
public enum Face {
    TOP(0, 1, 0),
    BOTTOM(0, -1, 0),
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0);

    public static final Face[] VALUES = values();
    private final int x;
    private final int y;
    private final int z;

    Face(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public float getShading() {
        if (isXAxis()) {
            return 0.6f;
        }
        return isYAxis() ? 1.0f : 0.8f;
    }

    public boolean isXAxis() {
        return this.x != 0;
    }

    public boolean isYAxis() {
        return this.y != 0;
    }

    public boolean isZAxis() {
        return this.z != 0;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Face opposite() throws MatchException {
        switch (this) {
            case TOP:
                return BOTTOM;
            case BOTTOM:
                return TOP;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}

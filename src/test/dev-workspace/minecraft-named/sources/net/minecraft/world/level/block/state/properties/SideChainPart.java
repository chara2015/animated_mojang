package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/SideChainPart.class */
public enum SideChainPart implements StringRepresentable {
    UNCONNECTED("unconnected"),
    RIGHT("right"),
    CENTER("center"),
    LEFT("left");

    private final String name;

    SideChainPart(String $$0) {
        this.name = $$0;
    }

    @Override // java.lang.Enum
    public String toString() {
        return getSerializedName();
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    public boolean isConnected() {
        return this != UNCONNECTED;
    }

    public boolean isConnectionTowards(SideChainPart $$0) {
        return this == CENTER || this == $$0;
    }

    public boolean isChainEnd() {
        return this != CENTER;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public SideChainPart whenConnectedToTheRight() throws MatchException {
        switch (this) {
            case UNCONNECTED:
            case LEFT:
                return LEFT;
            case RIGHT:
            case CENTER:
                return CENTER;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public SideChainPart whenConnectedToTheLeft() throws MatchException {
        switch (this) {
            case UNCONNECTED:
            case RIGHT:
                return RIGHT;
            case CENTER:
            case LEFT:
                return CENTER;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public SideChainPart whenDisconnectedFromTheRight() throws MatchException {
        switch (this) {
            case UNCONNECTED:
            case LEFT:
                return UNCONNECTED;
            case RIGHT:
            case CENTER:
                return RIGHT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public SideChainPart whenDisconnectedFromTheLeft() throws MatchException {
        switch (this) {
            case UNCONNECTED:
            case RIGHT:
                return UNCONNECTED;
            case CENTER:
            case LEFT:
                return LEFT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}

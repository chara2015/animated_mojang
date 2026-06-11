package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/ChestType.class */
public enum ChestType implements StringRepresentable {
    SINGLE("single"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    ChestType(String $$0) {
        this.name = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ChestType getOpposite() throws MatchException {
        switch (this) {
            case SINGLE:
                return SINGLE;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}

package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/BambooLeaves.class */
public enum BambooLeaves implements StringRepresentable {
    NONE("none"),
    SMALL("small"),
    LARGE("large");

    private final String name;

    BambooLeaves(String $$0) {
        this.name = $$0;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}

package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/WallSide.class */
public enum WallSide implements StringRepresentable {
    NONE("none"),
    LOW("low"),
    TALL("tall");

    private final String name;

    WallSide(String $$0) {
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
}

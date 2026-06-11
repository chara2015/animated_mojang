package net.minecraft.world.level.block.state.properties;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/StructureMode.class */
public enum StructureMode implements StringRepresentable {
    SAVE("save"),
    LOAD("load"),
    CORNER("corner"),
    DATA("data");


    @Deprecated
    public static final Codec<StructureMode> LEGACY_CODEC = ExtraCodecs.legacyEnum(StructureMode::valueOf);
    private final String name;
    private final Component displayName;

    StructureMode(String $$0) {
        this.name = $$0;
        this.displayName = Component.translatable("structure_block.mode_info." + $$0);
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    public Component getDisplayName() {
        return this.displayName;
    }
}

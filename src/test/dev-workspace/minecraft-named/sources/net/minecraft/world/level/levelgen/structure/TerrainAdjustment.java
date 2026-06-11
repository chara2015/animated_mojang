package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/TerrainAdjustment.class */
public enum TerrainAdjustment implements StringRepresentable {
    NONE("none"),
    BURY("bury"),
    BEARD_THIN("beard_thin"),
    BEARD_BOX("beard_box"),
    ENCAPSULATE("encapsulate");

    public static final Codec<TerrainAdjustment> CODEC = StringRepresentable.fromEnum(TerrainAdjustment::values);
    private final String id;

    TerrainAdjustment(String $$0) {
        this.id = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.id;
    }
}

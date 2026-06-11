package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings.class */
public enum LiquidSettings implements StringRepresentable {
    IGNORE_WATERLOGGING("ignore_waterlogging"),
    APPLY_WATERLOGGING("apply_waterlogging");

    public static Codec<LiquidSettings> CODEC = StringRepresentable.fromValues(LiquidSettings::values);
    private final String name;

    LiquidSettings(String $$0) {
        this.name = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}

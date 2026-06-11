package net.minecraft.world.damagesource;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/damagesource/DamageScaling.class */
public enum DamageScaling implements StringRepresentable {
    NEVER("never"),
    WHEN_CAUSED_BY_LIVING_NON_PLAYER("when_caused_by_living_non_player"),
    ALWAYS("always");

    public static final Codec<DamageScaling> CODEC = StringRepresentable.fromEnum(DamageScaling::values);
    private final String id;

    DamageScaling(String $$0) {
        this.id = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.id;
    }
}

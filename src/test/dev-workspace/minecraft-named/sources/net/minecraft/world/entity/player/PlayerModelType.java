package net.minecraft.world.entity.player;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/PlayerModelType.class */
public enum PlayerModelType implements StringRepresentable {
    SLIM("slim", "slim"),
    WIDE("wide", GameTestEnvironments.DEFAULT);

    public static final Codec<PlayerModelType> CODEC = StringRepresentable.fromEnum(PlayerModelType::values);
    private static final Function<String, PlayerModelType> NAME_LOOKUP = StringRepresentable.createNameLookup(values(), $$0 -> {
        return $$0.legacyServicesId;
    });
    public static final StreamCodec<ByteBuf, PlayerModelType> STREAM_CODEC = ByteBufCodecs.BOOL.map($$0 -> {
        return $$0.booleanValue() ? SLIM : WIDE;
    }, $$02 -> {
        return Boolean.valueOf($$02 == SLIM);
    });
    private final String id;
    private final String legacyServicesId;

    PlayerModelType(String $$0, String $$1) {
        this.id = $$0;
        this.legacyServicesId = $$1;
    }

    public static PlayerModelType byLegacyServicesName(String $$0) {
        return (PlayerModelType) Objects.requireNonNullElse(NAME_LOOKUP.apply($$0), WIDE);
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.id;
    }
}

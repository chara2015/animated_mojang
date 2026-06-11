package net.minecraft.server.jsonrpc.api;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/api/PlayerDto.class */
public final class PlayerDto extends Record {
    private final Optional<UUID> id;
    private final Optional<String> name;
    public static final MapCodec<PlayerDto> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(UUIDUtil.STRING_CODEC.optionalFieldOf(Entity.TAG_ID).forGetter((v0) -> {
            return v0.id();
        }), Codec.STRING.optionalFieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
            return v0.name();
        })).apply($$0, PlayerDto::new);
    });

    public PlayerDto(Optional<UUID> $$0, Optional<String> $$1) {
        this.id = $$0;
        this.name = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PlayerDto.class), PlayerDto.class, "id;name", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PlayerDto.class), PlayerDto.class, "id;name", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PlayerDto.class, Object.class), PlayerDto.class, "id;name", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/jsonrpc/api/PlayerDto;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<UUID> id() {
        return this.id;
    }

    public Optional<String> name() {
        return this.name;
    }

    public static PlayerDto from(GameProfile $$0) {
        return new PlayerDto(Optional.of($$0.id()), Optional.of($$0.name()));
    }

    public static PlayerDto from(NameAndId $$0) {
        return new PlayerDto(Optional.of($$0.id()), Optional.of($$0.name()));
    }

    public static PlayerDto from(ServerPlayer $$0) {
        GameProfile $$1 = $$0.getGameProfile();
        return from($$1);
    }
}

package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/PositionSource.class */
public interface PositionSource {
    public static final Codec<PositionSource> CODEC = BuiltInRegistries.POSITION_SOURCE_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.codec();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, PositionSource> STREAM_CODEC = ByteBufCodecs.registry(Registries.POSITION_SOURCE_TYPE).dispatch((v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.streamCodec();
    });

    Optional<Vec3> getPosition(Level level);

    PositionSourceType<? extends PositionSource> getType();
}

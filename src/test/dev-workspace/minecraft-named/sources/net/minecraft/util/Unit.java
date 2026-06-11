package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/Unit.class */
public enum Unit {
    INSTANCE;

    public static final Codec<Unit> CODEC = MapCodec.unitCodec(INSTANCE);
    public static final StreamCodec<ByteBuf, Unit> STREAM_CODEC = StreamCodec.unit(INSTANCE);
}

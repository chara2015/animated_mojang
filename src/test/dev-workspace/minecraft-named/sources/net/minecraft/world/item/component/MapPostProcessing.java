package net.minecraft.world.item.component;

import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/MapPostProcessing.class */
public enum MapPostProcessing {
    LOCK(0),
    SCALE(1);

    public static final IntFunction<MapPostProcessing> ID_MAP = ByIdMap.continuous((v0) -> {
        return v0.id();
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, MapPostProcessing> STREAM_CODEC = ByteBufCodecs.idMapper(ID_MAP, (v0) -> {
        return v0.id();
    });
    private final int id;

    MapPostProcessing(int $$0) {
        this.id = $$0;
    }

    public int id() {
        return this.id;
    }
}

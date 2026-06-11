package net.minecraft.util.debug;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugPoiInfo.class */
public final class DebugPoiInfo extends Record {
    private final BlockPos pos;
    private final Holder<PoiType> poiType;
    private final int freeTicketCount;
    public static final StreamCodec<RegistryFriendlyByteBuf, DebugPoiInfo> STREAM_CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, (v0) -> {
        return v0.pos();
    }, ByteBufCodecs.holderRegistry(Registries.POINT_OF_INTEREST_TYPE), (v0) -> {
        return v0.poiType();
    }, ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.freeTicketCount();
    }, (v1, v2, v3) -> {
        return new DebugPoiInfo(v1, v2, v3);
    });

    public DebugPoiInfo(BlockPos $$0, Holder<PoiType> $$1, int $$2) {
        this.pos = $$0;
        this.poiType = $$1;
        this.freeTicketCount = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugPoiInfo.class), DebugPoiInfo.class, "pos;poiType;freeTicketCount", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->freeTicketCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugPoiInfo.class), DebugPoiInfo.class, "pos;poiType;freeTicketCount", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->freeTicketCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugPoiInfo.class, Object.class), DebugPoiInfo.class, "pos;poiType;freeTicketCount", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/util/debug/DebugPoiInfo;->freeTicketCount:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public Holder<PoiType> poiType() {
        return this.poiType;
    }

    public int freeTicketCount() {
        return this.freeTicketCount;
    }

    public DebugPoiInfo(PoiRecord $$0) {
        this($$0.getPos(), $$0.getPoiType(), $$0.getFreeTickets());
    }
}

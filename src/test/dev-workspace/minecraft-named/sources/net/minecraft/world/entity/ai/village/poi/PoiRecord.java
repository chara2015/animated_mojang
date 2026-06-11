package net.minecraft.world.entity.ai.village.poi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/village/poi/PoiRecord.class */
public class PoiRecord {
    private final BlockPos pos;
    private final Holder<PoiType> poiType;
    private int freeTickets;
    private final Runnable setDirty;

    PoiRecord(BlockPos $$0, Holder<PoiType> $$1, int $$2, Runnable $$3) {
        this.pos = $$0.immutable();
        this.poiType = $$1;
        this.freeTickets = $$2;
        this.setDirty = $$3;
    }

    public PoiRecord(BlockPos $$0, Holder<PoiType> $$1, Runnable $$2) {
        this($$0, $$1, $$1.value().maxTickets(), $$2);
    }

    public Packed pack() {
        return new Packed(this.pos, this.poiType, this.freeTickets);
    }

    @VisibleForDebug
    @Deprecated
    public int getFreeTickets() {
        return this.freeTickets;
    }

    protected boolean acquireTicket() {
        if (this.freeTickets <= 0) {
            return false;
        }
        this.freeTickets--;
        this.setDirty.run();
        return true;
    }

    protected boolean releaseTicket() {
        if (this.freeTickets >= this.poiType.value().maxTickets()) {
            return false;
        }
        this.freeTickets++;
        this.setDirty.run();
        return true;
    }

    public boolean hasSpace() {
        return this.freeTickets > 0;
    }

    public boolean isOccupied() {
        return this.freeTickets != this.poiType.value().maxTickets();
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Holder<PoiType> getPoiType() {
        return this.poiType;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        return Objects.equals(this.pos, ((PoiRecord) $$0).pos);
    }

    public int hashCode() {
        return this.pos.hashCode();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/village/poi/PoiRecord$Packed.class */
    public static final class Packed extends Record {
        private final BlockPos pos;
        private final Holder<PoiType> poiType;
        private final int freeTickets;
        public static final Codec<Packed> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(BlockPos.CODEC.fieldOf("pos").forGetter((v0) -> {
                return v0.pos();
            }), RegistryFixedCodec.create(Registries.POINT_OF_INTEREST_TYPE).fieldOf(ChunkRegionIoEvent.Fields.TYPE).forGetter((v0) -> {
                return v0.poiType();
            }), Codec.INT.fieldOf("free_tickets").orElse(0).forGetter((v0) -> {
                return v0.freeTickets();
            })).apply($$0, (v1, v2, v3) -> {
                return new Packed(v1, v2, v3);
            });
        });

        public Packed(BlockPos $$0, Holder<PoiType> $$1, int $$2) {
            this.pos = $$0;
            this.poiType = $$1;
            this.freeTickets = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Packed.class), Packed.class, "pos;poiType;freeTickets", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->freeTickets:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Packed.class), Packed.class, "pos;poiType;freeTickets", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->freeTickets:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Packed.class, Object.class), Packed.class, "pos;poiType;freeTickets", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->poiType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/entity/ai/village/poi/PoiRecord$Packed;->freeTickets:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BlockPos pos() {
            return this.pos;
        }

        public Holder<PoiType> poiType() {
            return this.poiType;
        }

        public int freeTickets() {
            return this.freeTickets;
        }

        public PoiRecord unpack(Runnable $$0) {
            return new PoiRecord(this.pos, this.poiType, this.freeTickets, $$0);
        }
    }
}

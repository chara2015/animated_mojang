package net.minecraft.world.ticks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Hash;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/ticks/SavedTick.class */
public final class SavedTick<T> extends Record {
    private final T type;
    private final BlockPos pos;
    private final int delay;
    private final TickPriority priority;
    public static final Hash.Strategy<SavedTick<?>> UNIQUE_TICK_HASH = new Hash.Strategy<SavedTick<?>>() { // from class: net.minecraft.world.ticks.SavedTick.1
        public int hashCode(SavedTick<?> $$0) {
            return (31 * $$0.pos().hashCode()) + $$0.type().hashCode();
        }

        public boolean equals(SavedTick<?> $$0, SavedTick<?> $$1) {
            if ($$0 == $$1) {
                return true;
            }
            return $$0 != null && $$1 != null && $$0.type() == $$1.type() && $$0.pos().equals($$1.pos());
        }
    };

    public SavedTick(T $$0, BlockPos $$1, int $$2, TickPriority $$3) {
        this.type = $$0;
        this.pos = $$1;
        this.delay = $$2;
        this.priority = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SavedTick.class), SavedTick.class, "type;pos;delay;priority", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->type:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->delay:I", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->priority:Lnet/minecraft/world/ticks/TickPriority;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SavedTick.class), SavedTick.class, "type;pos;delay;priority", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->type:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->delay:I", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->priority:Lnet/minecraft/world/ticks/TickPriority;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SavedTick.class, Object.class), SavedTick.class, "type;pos;delay;priority", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->type:Ljava/lang/Object;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->delay:I", "FIELD:Lnet/minecraft/world/ticks/SavedTick;->priority:Lnet/minecraft/world/ticks/TickPriority;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T type() {
        return this.type;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public int delay() {
        return this.delay;
    }

    public TickPriority priority() {
        return this.priority;
    }

    public static <T> Codec<SavedTick<T>> codec(Codec<T> $$0) {
        MapCodec<BlockPos> $$1 = RecordCodecBuilder.mapCodec($$02 -> {
            return $$02.group(Codec.INT.fieldOf("x").forGetter((v0) -> {
                return v0.getX();
            }), Codec.INT.fieldOf("y").forGetter((v0) -> {
                return v0.getY();
            }), Codec.INT.fieldOf("z").forGetter((v0) -> {
                return v0.getZ();
            })).apply($$02, (v1, v2, v3) -> {
                return new BlockPos(v1, v2, v3);
            });
        });
        return RecordCodecBuilder.create($$2 -> {
            return $$2.group($$0.fieldOf("i").forGetter((v0) -> {
                return v0.type();
            }), $$1.forGetter((v0) -> {
                return v0.pos();
            }), Codec.INT.fieldOf("t").forGetter((v0) -> {
                return v0.delay();
            }), TickPriority.CODEC.fieldOf("p").forGetter((v0) -> {
                return v0.priority();
            })).apply($$2, (v1, v2, v3, v4) -> {
                return new SavedTick(v1, v2, v3, v4);
            });
        });
    }

    public static <T> List<SavedTick<T>> filterTickListForChunk(List<SavedTick<T>> $$0, ChunkPos $$1) {
        long $$2 = $$1.toLong();
        return $$0.stream().filter($$12 -> {
            return ChunkPos.asLong($$12.pos()) == $$2;
        }).toList();
    }

    public ScheduledTick<T> unpack(long $$0, long $$1) {
        return new ScheduledTick<>(this.type, this.pos, $$0 + ((long) this.delay), this.priority, $$1);
    }

    public static <T> SavedTick<T> probe(T $$0, BlockPos $$1) {
        return new SavedTick<>($$0, $$1, 0, TickPriority.NORMAL);
    }
}

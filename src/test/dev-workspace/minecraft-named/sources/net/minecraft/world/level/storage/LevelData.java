package net.minecraft.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelData.class */
public interface LevelData {
    RespawnData getRespawnData();

    long getGameTime();

    long getDayTime();

    boolean isThundering();

    boolean isRaining();

    void setRaining(boolean z);

    boolean isHardcore();

    Difficulty getDifficulty();

    boolean isDifficultyLocked();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/LevelData$RespawnData.class */
    public static final class RespawnData extends Record {
        private final GlobalPos globalPos;
        private final float yaw;
        private final float pitch;
        public static final RespawnData DEFAULT = new RespawnData(GlobalPos.of(Level.OVERWORLD, BlockPos.ZERO), 0.0f, 0.0f);
        public static final MapCodec<RespawnData> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(GlobalPos.MAP_CODEC.forGetter((v0) -> {
                return v0.globalPos();
            }), Codec.floatRange(-180.0f, 180.0f).fieldOf("yaw").forGetter((v0) -> {
                return v0.yaw();
            }), Codec.floatRange(-90.0f, 90.0f).fieldOf("pitch").forGetter((v0) -> {
                return v0.pitch();
            })).apply($$0, (v1, v2, v3) -> {
                return new RespawnData(v1, v2, v3);
            });
        });
        public static final Codec<RespawnData> CODEC = MAP_CODEC.codec();
        public static final StreamCodec<ByteBuf, RespawnData> STREAM_CODEC = StreamCodec.composite(GlobalPos.STREAM_CODEC, (v0) -> {
            return v0.globalPos();
        }, ByteBufCodecs.FLOAT, (v0) -> {
            return v0.yaw();
        }, ByteBufCodecs.FLOAT, (v0) -> {
            return v0.pitch();
        }, (v1, v2, v3) -> {
            return new RespawnData(v1, v2, v3);
        });

        public RespawnData(GlobalPos $$0, float $$1, float $$2) {
            this.globalPos = $$0;
            this.yaw = $$1;
            this.pitch = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RespawnData.class), RespawnData.class, "globalPos;yaw;pitch", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->globalPos:Lnet/minecraft/core/GlobalPos;", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->yaw:F", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->pitch:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RespawnData.class), RespawnData.class, "globalPos;yaw;pitch", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->globalPos:Lnet/minecraft/core/GlobalPos;", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->yaw:F", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->pitch:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RespawnData.class, Object.class), RespawnData.class, "globalPos;yaw;pitch", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->globalPos:Lnet/minecraft/core/GlobalPos;", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->yaw:F", "FIELD:Lnet/minecraft/world/level/storage/LevelData$RespawnData;->pitch:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public GlobalPos globalPos() {
            return this.globalPos;
        }

        public float yaw() {
            return this.yaw;
        }

        public float pitch() {
            return this.pitch;
        }

        public static RespawnData of(ResourceKey<Level> $$0, BlockPos $$1, float $$2, float $$3) {
            return new RespawnData(GlobalPos.of($$0, $$1.immutable()), Mth.wrapDegrees($$2), Mth.clamp($$3, -90.0f, 90.0f));
        }

        public ResourceKey<Level> dimension() {
            return this.globalPos.dimension();
        }

        public BlockPos pos() {
            return this.globalPos.pos();
        }
    }

    default void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
        $$0.setDetail("Level spawn location", () -> {
            return CrashReportCategory.formatLocation($$1, getRespawnData().pos());
        });
        $$0.setDetail("Level time", () -> {
            return String.format(Locale.ROOT, "%d game time, %d day time", Long.valueOf(getGameTime()), Long.valueOf(getDayTime()));
        });
    }
}

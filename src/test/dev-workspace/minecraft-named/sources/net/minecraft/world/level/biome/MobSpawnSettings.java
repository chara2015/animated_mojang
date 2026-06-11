package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/MobSpawnSettings.class */
public class MobSpawnSettings {
    private static final float DEFAULT_CREATURE_SPAWN_PROBABILITY = 0.1f;
    private final float creatureGenerationProbability;
    private final Map<MobCategory, WeightedList<SpawnerData>> spawners;
    private final Map<EntityType<?>, MobSpawnCost> mobSpawnCosts;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final WeightedList<SpawnerData> EMPTY_MOB_LIST = WeightedList.of();
    public static final MobSpawnSettings EMPTY = new Builder().build();
    public static final MapCodec<MobSpawnSettings> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        RecordCodecBuilder recordCodecBuilderForGetter = Codec.floatRange(0.0f, 0.9999999f).optionalFieldOf("creature_spawn_probability", Float.valueOf(0.1f)).forGetter($$0 -> {
            return Float.valueOf($$0.creatureGenerationProbability);
        });
        Codec<MobCategory> codec = MobCategory.CODEC;
        Codec codec2 = WeightedList.codec(SpawnerData.CODEC);
        Logger logger = LOGGER;
        Objects.requireNonNull(logger);
        return $$0.group(recordCodecBuilderForGetter, Codec.simpleMap(codec, codec2.promotePartial(Util.prefix("Spawn data: ", logger::error)), StringRepresentable.keys(MobCategory.values())).fieldOf("spawners").forGetter($$02 -> {
            return $$02.spawners;
        }), Codec.simpleMap(BuiltInRegistries.ENTITY_TYPE.byNameCodec(), MobSpawnCost.CODEC, BuiltInRegistries.ENTITY_TYPE).fieldOf("spawn_costs").forGetter($$03 -> {
            return $$03.mobSpawnCosts;
        })).apply($$0, (v1, v2, v3) -> {
            return new MobSpawnSettings(v1, v2, v3);
        });
    });

    MobSpawnSettings(float $$0, Map<MobCategory, WeightedList<SpawnerData>> $$1, Map<EntityType<?>, MobSpawnCost> $$2) {
        this.creatureGenerationProbability = $$0;
        this.spawners = ImmutableMap.copyOf($$1);
        this.mobSpawnCosts = ImmutableMap.copyOf($$2);
    }

    public WeightedList<SpawnerData> getMobs(MobCategory $$0) {
        return this.spawners.getOrDefault($$0, EMPTY_MOB_LIST);
    }

    public MobSpawnCost getMobSpawnCost(EntityType<?> $$0) {
        return this.mobSpawnCosts.get($$0);
    }

    public float getCreatureProbability() {
        return this.creatureGenerationProbability;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/MobSpawnSettings$SpawnerData.class */
    public static final class SpawnerData extends Record {
        private final EntityType<?> type;
        private final int minCount;
        private final int maxCount;
        public static final MapCodec<SpawnerData> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf(ChunkRegionIoEvent.Fields.TYPE).forGetter($$0 -> {
                return $$0.type;
            }), ExtraCodecs.POSITIVE_INT.fieldOf("minCount").forGetter($$02 -> {
                return Integer.valueOf($$02.minCount);
            }), ExtraCodecs.POSITIVE_INT.fieldOf("maxCount").forGetter($$03 -> {
                return Integer.valueOf($$03.maxCount);
            })).apply($$0, (v1, v2, v3) -> {
                return new SpawnerData(v1, v2, v3);
            });
        }).validate($$02 -> {
            if ($$02.minCount > $$02.maxCount) {
                return DataResult.error(() -> {
                    return "minCount needs to be smaller or equal to maxCount";
                });
            }
            return DataResult.success($$02);
        });

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SpawnerData.class), SpawnerData.class, "type;minCount;maxCount", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->type:Lnet/minecraft/world/entity/EntityType;", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->minCount:I", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->maxCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SpawnerData.class, Object.class), SpawnerData.class, "type;minCount;maxCount", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->type:Lnet/minecraft/world/entity/EntityType;", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->minCount:I", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$SpawnerData;->maxCount:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public EntityType<?> type() {
            return this.type;
        }

        public int minCount() {
            return this.minCount;
        }

        public int maxCount() {
            return this.maxCount;
        }

        public SpawnerData(EntityType<?> $$0, int $$1, int $$2) {
            this.type = $$0.getCategory() == MobCategory.MISC ? EntityType.PIG : $$0;
            this.minCount = $$1;
            this.maxCount = $$2;
        }

        @Override // java.lang.Record
        public String toString() {
            return String.valueOf(EntityType.getKey(this.type)) + "*(" + this.minCount + "-" + this.maxCount + ")";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost.class */
    public static final class MobSpawnCost extends Record {
        private final double energyBudget;
        private final double charge;
        public static final Codec<MobSpawnCost> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter($$0 -> {
                return Double.valueOf($$0.energyBudget);
            }), Codec.DOUBLE.fieldOf("charge").forGetter($$02 -> {
                return Double.valueOf($$02.charge);
            })).apply($$0, (v1, v2) -> {
                return new MobSpawnCost(v1, v2);
            });
        });

        public MobSpawnCost(double $$0, double $$1) {
            this.energyBudget = $$0;
            this.charge = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MobSpawnCost.class), MobSpawnCost.class, "energyBudget;charge", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->energyBudget:D", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->charge:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MobSpawnCost.class), MobSpawnCost.class, "energyBudget;charge", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->energyBudget:D", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->charge:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MobSpawnCost.class, Object.class), MobSpawnCost.class, "energyBudget;charge", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->energyBudget:D", "FIELD:Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;->charge:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public double energyBudget() {
            return this.energyBudget;
        }

        public double charge() {
            return this.charge;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/MobSpawnSettings$Builder.class */
    public static class Builder {
        private final Map<MobCategory, WeightedList.Builder<SpawnerData>> spawners = Util.makeEnumMap(MobCategory.class, $$0 -> {
            return WeightedList.builder();
        });
        private final Map<EntityType<?>, MobSpawnCost> mobSpawnCosts = Maps.newLinkedHashMap();
        private float creatureGenerationProbability = 0.1f;

        public Builder addSpawn(MobCategory $$0, int $$1, SpawnerData $$2) {
            this.spawners.get($$0).add($$2, $$1);
            return this;
        }

        public Builder addMobCharge(EntityType<?> $$0, double $$1, double $$2) {
            this.mobSpawnCosts.put($$0, new MobSpawnCost($$2, $$1));
            return this;
        }

        public Builder creatureGenerationProbability(float $$0) {
            this.creatureGenerationProbability = $$0;
            return this;
        }

        public MobSpawnSettings build() {
            return new MobSpawnSettings(this.creatureGenerationProbability, (Map) this.spawners.entrySet().stream().collect(ImmutableMap.toImmutableMap((v0) -> {
                return v0.getKey();
            }, $$0 -> {
                return ((WeightedList.Builder) $$0.getValue()).build();
            })), ImmutableMap.copyOf(this.mobSpawnCosts));
        }
    }
}

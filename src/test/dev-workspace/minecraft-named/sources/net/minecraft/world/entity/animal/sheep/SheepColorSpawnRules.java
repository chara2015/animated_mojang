package net.minecraft.world.entity.animal.sheep;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/sheep/SheepColorSpawnRules.class */
public class SheepColorSpawnRules {
    private static final SheepColorSpawnConfiguration TEMPERATE_SPAWN_CONFIGURATION = new SheepColorSpawnConfiguration(weighted(builder().add(single(DyeColor.BLACK), 5).add(single(DyeColor.GRAY), 5).add(single(DyeColor.LIGHT_GRAY), 5).add(single(DyeColor.BROWN), 3).add(commonColors(DyeColor.WHITE), 82).build()));
    private static final SheepColorSpawnConfiguration WARM_SPAWN_CONFIGURATION = new SheepColorSpawnConfiguration(weighted(builder().add(single(DyeColor.GRAY), 5).add(single(DyeColor.LIGHT_GRAY), 5).add(single(DyeColor.WHITE), 5).add(single(DyeColor.BLACK), 3).add(commonColors(DyeColor.BROWN), 82).build()));
    private static final SheepColorSpawnConfiguration COLD_SPAWN_CONFIGURATION = new SheepColorSpawnConfiguration(weighted(builder().add(single(DyeColor.LIGHT_GRAY), 5).add(single(DyeColor.GRAY), 5).add(single(DyeColor.WHITE), 5).add(single(DyeColor.BROWN), 3).add(commonColors(DyeColor.BLACK), 82).build()));

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorProvider.class */
    @FunctionalInterface
    interface SheepColorProvider {
        DyeColor get(RandomSource randomSource);
    }

    private static SheepColorProvider commonColors(DyeColor $$0) {
        return weighted(builder().add(single($$0), 499).add(single(DyeColor.PINK), 1).build());
    }

    public static DyeColor getSheepColor(Holder<Biome> $$0, RandomSource $$1) {
        SheepColorSpawnConfiguration $$2 = getSheepColorConfiguration($$0);
        return $$2.colors().get($$1);
    }

    private static SheepColorSpawnConfiguration getSheepColorConfiguration(Holder<Biome> $$0) {
        if ($$0.is(BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS)) {
            return WARM_SPAWN_CONFIGURATION;
        }
        if ($$0.is(BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS)) {
            return COLD_SPAWN_CONFIGURATION;
        }
        return TEMPERATE_SPAWN_CONFIGURATION;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorSpawnConfiguration.class */
    static final class SheepColorSpawnConfiguration extends Record {
        private final SheepColorProvider colors;

        SheepColorSpawnConfiguration(SheepColorProvider $$0) {
            this.colors = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SheepColorSpawnConfiguration.class), SheepColorSpawnConfiguration.class, "colors", "FIELD:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorSpawnConfiguration;->colors:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SheepColorSpawnConfiguration.class), SheepColorSpawnConfiguration.class, "colors", "FIELD:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorSpawnConfiguration;->colors:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SheepColorSpawnConfiguration.class, Object.class), SheepColorSpawnConfiguration.class, "colors", "FIELD:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorSpawnConfiguration;->colors:Lnet/minecraft/world/entity/animal/sheep/SheepColorSpawnRules$SheepColorProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SheepColorProvider colors() {
            return this.colors;
        }
    }

    private static SheepColorProvider weighted(WeightedList<SheepColorProvider> $$0) {
        if ($$0.isEmpty()) {
            throw new IllegalArgumentException("List must be non-empty");
        }
        return $$1 -> {
            return ((SheepColorProvider) $$0.getRandomOrThrow($$1)).get($$1);
        };
    }

    private static SheepColorProvider single(DyeColor $$0) {
        return $$1 -> {
            return $$0;
        };
    }

    private static WeightedList.Builder<SheepColorProvider> builder() {
        return WeightedList.builder();
    }
}

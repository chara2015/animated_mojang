package net.minecraft.world.level.biome;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Climate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeSource.class */
public abstract class BiomeSource implements BiomeResolver {
    public static final Codec<BiomeSource> CODEC = BuiltInRegistries.BIOME_SOURCE.byNameCodec().dispatchStable((v0) -> {
        return v0.codec();
    }, Function.identity());
    private final Supplier<Set<Holder<Biome>>> possibleBiomes = Suppliers.memoize(() -> {
        return (Set) collectPossibleBiomes().distinct().collect(ImmutableSet.toImmutableSet());
    });

    protected abstract MapCodec<? extends BiomeSource> codec();

    protected abstract Stream<Holder<Biome>> collectPossibleBiomes();

    @Override // net.minecraft.world.level.biome.BiomeResolver
    public abstract Holder<Biome> getNoiseBiome(int i, int i2, int i3, Climate.Sampler sampler);

    protected BiomeSource() {
    }

    public Set<Holder<Biome>> possibleBiomes() {
        return this.possibleBiomes.get();
    }

    public Set<Holder<Biome>> getBiomesWithin(int $$0, int $$1, int $$2, int $$3, Climate.Sampler $$4) {
        int $$5 = QuartPos.fromBlock($$0 - $$3);
        int $$6 = QuartPos.fromBlock($$1 - $$3);
        int $$7 = QuartPos.fromBlock($$2 - $$3);
        int $$8 = QuartPos.fromBlock($$0 + $$3);
        int $$9 = QuartPos.fromBlock($$1 + $$3);
        int $$10 = QuartPos.fromBlock($$2 + $$3);
        int $$11 = ($$8 - $$5) + 1;
        int $$12 = ($$9 - $$6) + 1;
        int $$13 = ($$10 - $$7) + 1;
        Set<Holder<Biome>> $$14 = Sets.newHashSet();
        for (int $$15 = 0; $$15 < $$13; $$15++) {
            for (int $$16 = 0; $$16 < $$11; $$16++) {
                for (int $$17 = 0; $$17 < $$12; $$17++) {
                    int $$18 = $$5 + $$16;
                    int $$19 = $$6 + $$17;
                    int $$20 = $$7 + $$15;
                    $$14.add(getNoiseBiome($$18, $$19, $$20, $$4));
                }
            }
        }
        return $$14;
    }

    public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int $$0, int $$1, int $$2, int $$3, Predicate<Holder<Biome>> $$4, RandomSource $$5, Climate.Sampler $$6) {
        return findBiomeHorizontal($$0, $$1, $$2, $$3, 1, $$4, $$5, false, $$6);
    }

    public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(BlockPos $$0, int $$1, int $$2, int $$3, Predicate<Holder<Biome>> $$4, Climate.Sampler $$5, LevelReader $$6) {
        Set<Holder<Biome>> $$7 = (Set) possibleBiomes().stream().filter($$4).collect(Collectors.toUnmodifiableSet());
        if ($$7.isEmpty()) {
            return null;
        }
        int $$8 = Math.floorDiv($$1, $$2);
        int[] $$9 = Mth.outFromOrigin($$0.getY(), $$6.getMinY() + 1, $$6.getMaxY() + 1, $$3).toArray();
        for (BlockPos.MutableBlockPos $$10 : BlockPos.spiralAround(BlockPos.ZERO, $$8, Direction.EAST, Direction.SOUTH)) {
            int $$11 = $$0.getX() + ($$10.getX() * $$2);
            int $$12 = $$0.getZ() + ($$10.getZ() * $$2);
            int $$13 = QuartPos.fromBlock($$11);
            int $$14 = QuartPos.fromBlock($$12);
            for (int $$15 : $$9) {
                int $$16 = QuartPos.fromBlock($$15);
                Holder<Biome> $$17 = getNoiseBiome($$13, $$16, $$14, $$5);
                if ($$7.contains($$17)) {
                    return Pair.of(new BlockPos($$11, $$15, $$12), $$17);
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.mojang.datafixers.util.Pair<net.minecraft.core.BlockPos, net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> findBiomeHorizontal(int r7, int r8, int r9, int r10, int r11, java.util.function.Predicate<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> r12, net.minecraft.util.RandomSource r13, boolean r14, net.minecraft.world.level.biome.Climate.Sampler r15) {
        /*
            Method dump skipped, instruction units count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.world.level.biome.BiomeSource.findBiomeHorizontal(int, int, int, int, int, java.util.function.Predicate, net.minecraft.util.RandomSource, boolean, net.minecraft.world.level.biome.Climate$Sampler):com.mojang.datafixers.util.Pair");
    }

    public void addDebugInfo(List<String> $$0, BlockPos $$1, Climate.Sampler $$2) {
    }
}

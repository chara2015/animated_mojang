package net.minecraft.world.level.levelgen.structure.pools.alias;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup.class */
@FunctionalInterface
public interface PoolAliasLookup {
    public static final PoolAliasLookup EMPTY = $$0 -> {
        return $$0;
    };

    ResourceKey<StructureTemplatePool> lookup(ResourceKey<StructureTemplatePool> resourceKey);

    static PoolAliasLookup create(List<PoolAliasBinding> $$0, BlockPos $$1, long $$2) {
        if ($$0.isEmpty()) {
            return EMPTY;
        }
        RandomSource $$3 = RandomSource.create($$2).forkPositional().at($$1);
        ImmutableMap.Builder<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> $$4 = ImmutableMap.builder();
        $$0.forEach($$22 -> {
            Objects.requireNonNull($$4);
            $$22.forEachResolved($$3, (v1, v2) -> {
                r2.put(v1, v2);
            });
        });
        ImmutableMap immutableMapBuild = $$4.build();
        return $$12 -> {
            return (ResourceKey) Objects.requireNonNull((ResourceKey) immutableMapBuild.getOrDefault($$12, $$12), (Supplier<String>) () -> {
                return "alias " + String.valueOf($$12.identifier()) + " was mapped to null value";
            });
        };
    }
}

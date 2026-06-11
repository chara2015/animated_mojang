package net.minecraft.world.entity.variant;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.attribute.EnvironmentAttributeReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/SpawnContext.class */
public final class SpawnContext extends Record {
    private final BlockPos pos;
    private final ServerLevelAccessor level;
    private final EnvironmentAttributeReader environmentAttributes;
    private final Holder<Biome> biome;

    public SpawnContext(BlockPos $$0, ServerLevelAccessor $$1, EnvironmentAttributeReader $$2, Holder<Biome> $$3) {
        this.pos = $$0;
        this.level = $$1;
        this.environmentAttributes = $$2;
        this.biome = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SpawnContext.class), SpawnContext.class, "pos;level;environmentAttributes;biome", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->level:Lnet/minecraft/world/level/ServerLevelAccessor;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->environmentAttributes:Lnet/minecraft/world/attribute/EnvironmentAttributeReader;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->biome:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SpawnContext.class), SpawnContext.class, "pos;level;environmentAttributes;biome", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->level:Lnet/minecraft/world/level/ServerLevelAccessor;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->environmentAttributes:Lnet/minecraft/world/attribute/EnvironmentAttributeReader;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->biome:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SpawnContext.class, Object.class), SpawnContext.class, "pos;level;environmentAttributes;biome", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->level:Lnet/minecraft/world/level/ServerLevelAccessor;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->environmentAttributes:Lnet/minecraft/world/attribute/EnvironmentAttributeReader;", "FIELD:Lnet/minecraft/world/entity/variant/SpawnContext;->biome:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public ServerLevelAccessor level() {
        return this.level;
    }

    public EnvironmentAttributeReader environmentAttributes() {
        return this.environmentAttributes;
    }

    public Holder<Biome> biome() {
        return this.biome;
    }

    public static SpawnContext create(ServerLevelAccessor $$0, BlockPos $$1) {
        Holder<Biome> $$2 = $$0.getBiome($$1);
        return new SpawnContext($$1, $$0, $$0.environmentAttributes(), $$2);
    }
}

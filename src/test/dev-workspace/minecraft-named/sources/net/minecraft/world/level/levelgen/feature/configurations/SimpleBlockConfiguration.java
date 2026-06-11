package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration.class */
public final class SimpleBlockConfiguration extends Record implements FeatureConfiguration {
    private final BlockStateProvider toPlace;
    private final boolean scheduleTick;
    public static final Codec<SimpleBlockConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter($$0 -> {
            return $$0.toPlace;
        }), Codec.BOOL.optionalFieldOf("schedule_tick", false).forGetter($$02 -> {
            return Boolean.valueOf($$02.scheduleTick);
        })).apply($$0, (v1, v2) -> {
            return new SimpleBlockConfiguration(v1, v2);
        });
    });

    public SimpleBlockConfiguration(BlockStateProvider $$0, boolean $$1) {
        this.toPlace = $$0;
        this.scheduleTick = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SimpleBlockConfiguration.class), SimpleBlockConfiguration.class, "toPlace;scheduleTick", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->toPlace:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->scheduleTick:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SimpleBlockConfiguration.class), SimpleBlockConfiguration.class, "toPlace;scheduleTick", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->toPlace:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->scheduleTick:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SimpleBlockConfiguration.class, Object.class), SimpleBlockConfiguration.class, "toPlace;scheduleTick", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->toPlace:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SimpleBlockConfiguration;->scheduleTick:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockStateProvider toPlace() {
        return this.toPlace;
    }

    public boolean scheduleTick() {
        return this.scheduleTick;
    }

    public SimpleBlockConfiguration(BlockStateProvider $$0) {
        this($$0, false);
    }
}

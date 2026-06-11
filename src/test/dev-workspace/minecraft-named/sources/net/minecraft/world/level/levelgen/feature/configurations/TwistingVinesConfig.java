package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig.class */
public final class TwistingVinesConfig extends Record implements FeatureConfiguration {
    private final int spreadWidth;
    private final int spreadHeight;
    private final int maxHeight;
    public static final Codec<TwistingVinesConfig> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter((v0) -> {
            return v0.spreadWidth();
        }), ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter((v0) -> {
            return v0.spreadHeight();
        }), ExtraCodecs.POSITIVE_INT.fieldOf("max_height").forGetter((v0) -> {
            return v0.maxHeight();
        })).apply($$0, (v1, v2, v3) -> {
            return new TwistingVinesConfig(v1, v2, v3);
        });
    });

    public TwistingVinesConfig(int $$0, int $$1, int $$2) {
        this.spreadWidth = $$0;
        this.spreadHeight = $$1;
        this.maxHeight = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TwistingVinesConfig.class), TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadWidth:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadHeight:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->maxHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TwistingVinesConfig.class), TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadWidth:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadHeight:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->maxHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TwistingVinesConfig.class, Object.class), TwistingVinesConfig.class, "spreadWidth;spreadHeight;maxHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadWidth:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->spreadHeight:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;->maxHeight:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int spreadWidth() {
        return this.spreadWidth;
    }

    public int spreadHeight() {
        return this.spreadHeight;
    }

    public int maxHeight() {
        return this.maxHeight;
    }
}

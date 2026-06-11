package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeSpecialEffects.class */
public final class BiomeSpecialEffects extends Record {
    private final int waterColor;
    private final Optional<Integer> foliageColorOverride;
    private final Optional<Integer> dryFoliageColorOverride;
    private final Optional<Integer> grassColorOverride;
    private final GrassColorModifier grassColorModifier;
    public static final Codec<BiomeSpecialEffects> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.STRING_RGB_COLOR.fieldOf("water_color").forGetter((v0) -> {
            return v0.waterColor();
        }), ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf("foliage_color").forGetter((v0) -> {
            return v0.foliageColorOverride();
        }), ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf("dry_foliage_color").forGetter((v0) -> {
            return v0.dryFoliageColorOverride();
        }), ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf("grass_color").forGetter((v0) -> {
            return v0.grassColorOverride();
        }), GrassColorModifier.CODEC.optionalFieldOf("grass_color_modifier", GrassColorModifier.NONE).forGetter((v0) -> {
            return v0.grassColorModifier();
        })).apply($$0, (v1, v2, v3, v4, v5) -> {
            return new BiomeSpecialEffects(v1, v2, v3, v4, v5);
        });
    });

    public BiomeSpecialEffects(int $$0, Optional<Integer> $$1, Optional<Integer> $$2, Optional<Integer> $$3, GrassColorModifier $$4) {
        this.waterColor = $$0;
        this.foliageColorOverride = $$1;
        this.dryFoliageColorOverride = $$2;
        this.grassColorOverride = $$3;
        this.grassColorModifier = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BiomeSpecialEffects.class), BiomeSpecialEffects.class, "waterColor;foliageColorOverride;dryFoliageColorOverride;grassColorOverride;grassColorModifier", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->waterColor:I", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->foliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->dryFoliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorModifier:Lnet/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BiomeSpecialEffects.class), BiomeSpecialEffects.class, "waterColor;foliageColorOverride;dryFoliageColorOverride;grassColorOverride;grassColorModifier", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->waterColor:I", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->foliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->dryFoliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorModifier:Lnet/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BiomeSpecialEffects.class, Object.class), BiomeSpecialEffects.class, "waterColor;foliageColorOverride;dryFoliageColorOverride;grassColorOverride;grassColorModifier", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->waterColor:I", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->foliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->dryFoliageColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorOverride:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;->grassColorModifier:Lnet/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int waterColor() {
        return this.waterColor;
    }

    public Optional<Integer> foliageColorOverride() {
        return this.foliageColorOverride;
    }

    public Optional<Integer> dryFoliageColorOverride() {
        return this.dryFoliageColorOverride;
    }

    public Optional<Integer> grassColorOverride() {
        return this.grassColorOverride;
    }

    public GrassColorModifier grassColorModifier() {
        return this.grassColorModifier;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeSpecialEffects$Builder.class */
    public static class Builder {
        private OptionalInt waterColor = OptionalInt.empty();
        private Optional<Integer> foliageColorOverride = Optional.empty();
        private Optional<Integer> dryFoliageColorOverride = Optional.empty();
        private Optional<Integer> grassColorOverride = Optional.empty();
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        public Builder waterColor(int $$0) {
            this.waterColor = OptionalInt.of($$0);
            return this;
        }

        public Builder foliageColorOverride(int $$0) {
            this.foliageColorOverride = Optional.of(Integer.valueOf($$0));
            return this;
        }

        public Builder dryFoliageColorOverride(int $$0) {
            this.dryFoliageColorOverride = Optional.of(Integer.valueOf($$0));
            return this;
        }

        public Builder grassColorOverride(int $$0) {
            this.grassColorOverride = Optional.of(Integer.valueOf($$0));
            return this;
        }

        public Builder grassColorModifier(GrassColorModifier $$0) {
            this.grassColorModifier = $$0;
            return this;
        }

        public BiomeSpecialEffects build() {
            return new BiomeSpecialEffects(this.waterColor.orElseThrow(() -> {
                return new IllegalStateException("Missing 'water' color.");
            }), this.foliageColorOverride, this.dryFoliageColorOverride, this.grassColorOverride, this.grassColorModifier);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier.class */
    public enum GrassColorModifier implements StringRepresentable {
        NONE("none") { // from class: net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.1
            @Override // net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier
            public int modifyColor(double $$0, double $$1, int $$2) {
                return $$2;
            }
        },
        DARK_FOREST("dark_forest") { // from class: net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.2
            @Override // net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier
            public int modifyColor(double $$0, double $$1, int $$2) {
                return (($$2 & 16711422) + 2634762) >> 1;
            }
        },
        SWAMP("swamp") { // from class: net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.3
            @Override // net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier
            public int modifyColor(double $$0, double $$1, int $$2) {
                double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0 * 0.0225d, $$1 * 0.0225d, false);
                if ($$3 < -0.1d) {
                    return 5011004;
                }
                return 6975545;
            }
        };

        private final String name;
        public static final Codec<GrassColorModifier> CODEC = StringRepresentable.fromEnum(GrassColorModifier::values);

        public abstract int modifyColor(double d, double d2, int i);

        GrassColorModifier(String $$0) {
            this.name = $$0;
        }

        public String getName() {
            return this.name;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}

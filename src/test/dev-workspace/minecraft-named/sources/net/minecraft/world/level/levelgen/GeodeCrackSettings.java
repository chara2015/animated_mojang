package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/GeodeCrackSettings.class */
public class GeodeCrackSettings {
    public static final Codec<GeodeCrackSettings> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(GeodeConfiguration.CHANCE_RANGE.fieldOf("generate_crack_chance").orElse(Double.valueOf(1.0d)).forGetter($$0 -> {
            return Double.valueOf($$0.generateCrackChance);
        }), Codec.doubleRange(Density.SURFACE, 5.0d).fieldOf("base_crack_size").orElse(Double.valueOf(2.0d)).forGetter($$02 -> {
            return Double.valueOf($$02.baseCrackSize);
        }), Codec.intRange(0, 10).fieldOf("crack_point_offset").orElse(2).forGetter($$03 -> {
            return Integer.valueOf($$03.crackPointOffset);
        })).apply($$0, (v1, v2, v3) -> {
            return new GeodeCrackSettings(v1, v2, v3);
        });
    });
    public final double generateCrackChance;
    public final double baseCrackSize;
    public final int crackPointOffset;

    public GeodeCrackSettings(double $$0, double $$1, int $$2) {
        this.generateCrackChance = $$0;
        this.baseCrackSize = $$1;
        this.crackPointOffset = $$2;
    }
}

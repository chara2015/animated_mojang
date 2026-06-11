package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.util.RandomSource;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldOptions.class */
public class WorldOptions {
    public static final MapCodec<WorldOptions> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.LONG.fieldOf("seed").stable().forGetter((v0) -> {
            return v0.seed();
        }), Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter((v0) -> {
            return v0.generateStructures();
        }), Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter((v0) -> {
            return v0.generateBonusChest();
        }), Codec.STRING.lenientOptionalFieldOf("legacy_custom_options").stable().forGetter($$0 -> {
            return $$0.legacyCustomOptions;
        })).apply($$0, $$0.stable((v1, v2, v3, v4) -> {
            return new WorldOptions(v1, v2, v3, v4);
        }));
    });
    public static final WorldOptions DEMO_OPTIONS = new WorldOptions("North Carolina".hashCode(), true, true);
    private final long seed;
    private final boolean generateStructures;
    private final boolean generateBonusChest;
    private final Optional<String> legacyCustomOptions;

    public WorldOptions(long $$0, boolean $$1, boolean $$2) {
        this($$0, $$1, $$2, Optional.empty());
    }

    public static WorldOptions defaultWithRandomSeed() {
        return new WorldOptions(randomSeed(), true, false);
    }

    public static WorldOptions testWorldWithRandomSeed() {
        return new WorldOptions(randomSeed(), false, false);
    }

    private WorldOptions(long $$0, boolean $$1, boolean $$2, Optional<String> $$3) {
        this.seed = $$0;
        this.generateStructures = $$1;
        this.generateBonusChest = $$2;
        this.legacyCustomOptions = $$3;
    }

    public long seed() {
        return this.seed;
    }

    public boolean generateStructures() {
        return this.generateStructures;
    }

    public boolean generateBonusChest() {
        return this.generateBonusChest;
    }

    public boolean isOldCustomizedWorld() {
        return this.legacyCustomOptions.isPresent();
    }

    public WorldOptions withBonusChest(boolean $$0) {
        return new WorldOptions(this.seed, this.generateStructures, $$0, this.legacyCustomOptions);
    }

    public WorldOptions withStructures(boolean $$0) {
        return new WorldOptions(this.seed, $$0, this.generateBonusChest, this.legacyCustomOptions);
    }

    public WorldOptions withSeed(OptionalLong $$0) {
        return new WorldOptions($$0.orElse(randomSeed()), this.generateStructures, this.generateBonusChest, this.legacyCustomOptions);
    }

    public static OptionalLong parseSeed(String $$0) {
        String $$02 = $$0.trim();
        if (StringUtils.isEmpty($$02)) {
            return OptionalLong.empty();
        }
        try {
            return OptionalLong.of(Long.parseLong($$02));
        } catch (NumberFormatException e) {
            return OptionalLong.of($$02.hashCode());
        }
    }

    public static long randomSeed() {
        return RandomSource.create().nextLong();
    }
}

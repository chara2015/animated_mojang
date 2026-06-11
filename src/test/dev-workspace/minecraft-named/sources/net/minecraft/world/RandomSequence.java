package net.minecraft.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/RandomSequence.class */
public class RandomSequence {
    public static final Codec<RandomSequence> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(XoroshiroRandomSource.CODEC.fieldOf("source").forGetter($$0 -> {
            return $$0.source;
        })).apply($$0, RandomSequence::new);
    });
    private final XoroshiroRandomSource source;

    public RandomSequence(XoroshiroRandomSource $$0) {
        this.source = $$0;
    }

    public RandomSequence(long $$0, Identifier $$1) {
        this(createSequence($$0, Optional.of($$1)));
    }

    public RandomSequence(long $$0, Optional<Identifier> $$1) {
        this(createSequence($$0, $$1));
    }

    private static XoroshiroRandomSource createSequence(long $$0, Optional<Identifier> $$1) {
        RandomSupport.Seed128bit $$2 = RandomSupport.upgradeSeedTo128bitUnmixed($$0);
        if ($$1.isPresent()) {
            $$2 = $$2.xor(seedForKey($$1.get()));
        }
        return new XoroshiroRandomSource($$2.mixed());
    }

    public static RandomSupport.Seed128bit seedForKey(Identifier $$0) {
        return RandomSupport.seedFromHashOf($$0.toString());
    }

    public RandomSource random() {
        return this.source;
    }
}

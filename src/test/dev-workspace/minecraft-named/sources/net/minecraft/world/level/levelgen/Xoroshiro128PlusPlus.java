package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.stream.LongStream;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.RandomSupport;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/Xoroshiro128PlusPlus.class */
public class Xoroshiro128PlusPlus {
    private long seedLo;
    private long seedHi;
    public static final Codec<Xoroshiro128PlusPlus> CODEC = Codec.LONG_STREAM.comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 2).map($$0 -> {
            return new Xoroshiro128PlusPlus($$0[0], $$0[1]);
        });
    }, $$02 -> {
        return LongStream.of($$02.seedLo, $$02.seedHi);
    });

    public Xoroshiro128PlusPlus(RandomSupport.Seed128bit $$0) {
        this($$0.seedLo(), $$0.seedHi());
    }

    public Xoroshiro128PlusPlus(long $$0, long $$1) {
        this.seedLo = $$0;
        this.seedHi = $$1;
        if ((this.seedLo | this.seedHi) == 0) {
            this.seedLo = RandomSupport.GOLDEN_RATIO_64;
            this.seedHi = RandomSupport.SILVER_RATIO_64;
        }
    }

    public long nextLong() {
        long $$0 = this.seedLo;
        long $$1 = this.seedHi;
        long $$2 = Long.rotateLeft($$0 + $$1, 17) + $$0;
        long $$12 = $$1 ^ $$0;
        this.seedLo = (Long.rotateLeft($$0, 49) ^ $$12) ^ ($$12 << 21);
        this.seedHi = Long.rotateLeft($$12, 28);
        return $$2;
    }
}

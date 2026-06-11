package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/RandomSupport.class */
public final class RandomSupport {
    public static final long GOLDEN_RATIO_64 = -7046029254386353131L;
    public static final long SILVER_RATIO_64 = 7640891576956012809L;
    private static final HashFunction MD5_128 = Hashing.md5();
    private static final AtomicLong SEED_UNIQUIFIER = new AtomicLong(8682522807148012L);

    @VisibleForTesting
    public static long mixStafford13(long $$0) {
        long $$02 = ($$0 ^ ($$0 >>> 30)) * (-4658895280553007687L);
        long $$03 = ($$02 ^ ($$02 >>> 27)) * (-7723592293110705685L);
        return $$03 ^ ($$03 >>> 31);
    }

    public static Seed128bit upgradeSeedTo128bitUnmixed(long $$0) {
        long $$1 = $$0 ^ SILVER_RATIO_64;
        long $$2 = $$1 + GOLDEN_RATIO_64;
        return new Seed128bit($$1, $$2);
    }

    public static Seed128bit upgradeSeedTo128bit(long $$0) {
        return upgradeSeedTo128bitUnmixed($$0).mixed();
    }

    public static Seed128bit seedFromHashOf(String $$0) {
        byte[] $$1 = MD5_128.hashString($$0, StandardCharsets.UTF_8).asBytes();
        long $$2 = Longs.fromBytes($$1[0], $$1[1], $$1[2], $$1[3], $$1[4], $$1[5], $$1[6], $$1[7]);
        long $$3 = Longs.fromBytes($$1[8], $$1[9], $$1[10], $$1[11], $$1[12], $$1[13], $$1[14], $$1[15]);
        return new Seed128bit($$2, $$3);
    }

    public static long generateUniqueSeed() {
        return SEED_UNIQUIFIER.updateAndGet($$0 -> {
            return $$0 * 1181783497276652981L;
        }) ^ System.nanoTime();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/RandomSupport$Seed128bit.class */
    public static final class Seed128bit extends Record {
        private final long seedLo;
        private final long seedHi;

        public Seed128bit(long $$0, long $$1) {
            this.seedLo = $$0;
            this.seedHi = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Seed128bit.class), Seed128bit.class, "seedLo;seedHi", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedLo:J", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedHi:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Seed128bit.class), Seed128bit.class, "seedLo;seedHi", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedLo:J", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedHi:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Seed128bit.class, Object.class), Seed128bit.class, "seedLo;seedHi", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedLo:J", "FIELD:Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;->seedHi:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long seedLo() {
            return this.seedLo;
        }

        public long seedHi() {
            return this.seedHi;
        }

        public Seed128bit xor(long $$0, long $$1) {
            return new Seed128bit(this.seedLo ^ $$0, this.seedHi ^ $$1);
        }

        public Seed128bit xor(Seed128bit $$0) {
            return xor($$0.seedLo, $$0.seedHi);
        }

        public Seed128bit mixed() {
            return new Seed128bit(RandomSupport.mixStafford13(this.seedLo), RandomSupport.mixStafford13(this.seedHi));
        }
    }
}

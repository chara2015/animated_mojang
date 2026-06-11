package net.minecraft.world.level.biome;

import com.google.common.hash.Hashing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeManager.class */
public class BiomeManager {
    public static final int CHUNK_CENTER_QUART = QuartPos.fromBlock(8);
    private static final int ZOOM_BITS = 2;
    private static final int ZOOM = 4;
    private static final int ZOOM_MASK = 3;
    private final NoiseBiomeSource noiseBiomeSource;
    private final long biomeZoomSeed;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeManager$NoiseBiomeSource.class */
    public interface NoiseBiomeSource {
        Holder<Biome> getNoiseBiome(int i, int i2, int i3);
    }

    public BiomeManager(NoiseBiomeSource $$0, long $$1) {
        this.noiseBiomeSource = $$0;
        this.biomeZoomSeed = $$1;
    }

    public static long obfuscateSeed(long $$0) {
        return Hashing.sha256().hashLong($$0).asLong();
    }

    public BiomeManager withDifferentSource(NoiseBiomeSource $$0) {
        return new BiomeManager($$0, this.biomeZoomSeed);
    }

    public Holder<Biome> getBiome(BlockPos $$0) {
        int $$1 = $$0.getX() - 2;
        int $$2 = $$0.getY() - 2;
        int $$3 = $$0.getZ() - 2;
        int $$4 = $$1 >> 2;
        int $$5 = $$2 >> 2;
        int $$6 = $$3 >> 2;
        double $$7 = ((double) ($$1 & 3)) / 4.0d;
        double $$8 = ((double) ($$2 & 3)) / 4.0d;
        double $$9 = ((double) ($$3 & 3)) / 4.0d;
        int $$10 = 0;
        double $$11 = Double.POSITIVE_INFINITY;
        for (int $$12 = 0; $$12 < 8; $$12++) {
            boolean $$13 = ($$12 & 4) == 0;
            boolean $$14 = ($$12 & 2) == 0;
            boolean $$15 = ($$12 & 1) == 0;
            int $$16 = $$13 ? $$4 : $$4 + 1;
            int $$17 = $$14 ? $$5 : $$5 + 1;
            int $$18 = $$15 ? $$6 : $$6 + 1;
            double $$19 = $$13 ? $$7 : $$7 - 1.0d;
            double $$20 = $$14 ? $$8 : $$8 - 1.0d;
            double $$21 = $$15 ? $$9 : $$9 - 1.0d;
            double $$22 = getFiddledDistance(this.biomeZoomSeed, $$16, $$17, $$18, $$19, $$20, $$21);
            if ($$11 > $$22) {
                $$10 = $$12;
                $$11 = $$22;
            }
        }
        int $$23 = ($$10 & 4) == 0 ? $$4 : $$4 + 1;
        int $$24 = ($$10 & 2) == 0 ? $$5 : $$5 + 1;
        int $$25 = ($$10 & 1) == 0 ? $$6 : $$6 + 1;
        return this.noiseBiomeSource.getNoiseBiome($$23, $$24, $$25);
    }

    public Holder<Biome> getNoiseBiomeAtPosition(double $$0, double $$1, double $$2) {
        int $$3 = QuartPos.fromBlock(Mth.floor($$0));
        int $$4 = QuartPos.fromBlock(Mth.floor($$1));
        int $$5 = QuartPos.fromBlock(Mth.floor($$2));
        return getNoiseBiomeAtQuart($$3, $$4, $$5);
    }

    public Holder<Biome> getNoiseBiomeAtPosition(BlockPos $$0) {
        int $$1 = QuartPos.fromBlock($$0.getX());
        int $$2 = QuartPos.fromBlock($$0.getY());
        int $$3 = QuartPos.fromBlock($$0.getZ());
        return getNoiseBiomeAtQuart($$1, $$2, $$3);
    }

    public Holder<Biome> getNoiseBiomeAtQuart(int $$0, int $$1, int $$2) {
        return this.noiseBiomeSource.getNoiseBiome($$0, $$1, $$2);
    }

    private static double getFiddledDistance(long $$0, int $$1, int $$2, int $$3, double $$4, double $$5, double $$6) {
        long $$7 = LinearCongruentialGenerator.next(LinearCongruentialGenerator.next(LinearCongruentialGenerator.next(LinearCongruentialGenerator.next(LinearCongruentialGenerator.next(LinearCongruentialGenerator.next($$0, $$1), $$2), $$3), $$1), $$2), $$3);
        double $$8 = getFiddle($$7);
        long $$72 = LinearCongruentialGenerator.next($$7, $$0);
        double $$9 = getFiddle($$72);
        double $$10 = getFiddle(LinearCongruentialGenerator.next($$72, $$0));
        return Mth.square($$6 + $$10) + Mth.square($$5 + $$9) + Mth.square($$4 + $$8);
    }

    private static double getFiddle(long $$0) {
        double $$1 = ((double) Math.floorMod($$0 >> 24, 1024)) / 1024.0d;
        return ($$1 - 0.5d) * 0.9d;
    }
}

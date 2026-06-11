package net.minecraft.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/RandomSequences.class */
public class RandomSequences extends SavedData {
    public static final Codec<RandomSequences> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.INT.fieldOf("salt").forGetter((v0) -> {
            return v0.salt();
        }), Codec.BOOL.optionalFieldOf("include_world_seed", true).forGetter((v0) -> {
            return v0.includeWorldSeed();
        }), Codec.BOOL.optionalFieldOf("include_sequence_id", true).forGetter((v0) -> {
            return v0.includeSequenceId();
        }), Codec.unboundedMap(Identifier.CODEC, RandomSequence.CODEC).fieldOf("sequences").forGetter($$0 -> {
            return $$0.sequences;
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new RandomSequences(v1, v2, v3, v4);
        });
    });
    public static final SavedDataType<RandomSequences> TYPE = new SavedDataType<>("random_sequences", RandomSequences::new, CODEC, DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES);
    private int salt;
    private boolean includeWorldSeed;
    private boolean includeSequenceId;
    private final Map<Identifier, RandomSequence> sequences;

    public RandomSequences() {
        this.includeWorldSeed = true;
        this.includeSequenceId = true;
        this.sequences = new Object2ObjectOpenHashMap();
    }

    private RandomSequences(int $$0, boolean $$1, boolean $$2, Map<Identifier, RandomSequence> $$3) {
        this.includeWorldSeed = true;
        this.includeSequenceId = true;
        this.sequences = new Object2ObjectOpenHashMap();
        this.salt = $$0;
        this.includeWorldSeed = $$1;
        this.includeSequenceId = $$2;
        this.sequences.putAll($$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/RandomSequences$DirtyMarkingRandomSource.class */
    class DirtyMarkingRandomSource implements RandomSource {
        private final RandomSource random;

        DirtyMarkingRandomSource(RandomSource $$0) {
            this.random = $$0;
        }

        @Override // net.minecraft.util.RandomSource
        public RandomSource fork() {
            RandomSequences.this.setDirty();
            return this.random.fork();
        }

        @Override // net.minecraft.util.RandomSource
        public PositionalRandomFactory forkPositional() {
            RandomSequences.this.setDirty();
            return this.random.forkPositional();
        }

        @Override // net.minecraft.util.RandomSource
        public void setSeed(long $$0) {
            RandomSequences.this.setDirty();
            this.random.setSeed($$0);
        }

        @Override // net.minecraft.util.RandomSource
        public int nextInt() {
            RandomSequences.this.setDirty();
            return this.random.nextInt();
        }

        @Override // net.minecraft.util.RandomSource
        public int nextInt(int $$0) {
            RandomSequences.this.setDirty();
            return this.random.nextInt($$0);
        }

        @Override // net.minecraft.util.RandomSource
        public long nextLong() {
            RandomSequences.this.setDirty();
            return this.random.nextLong();
        }

        @Override // net.minecraft.util.RandomSource
        public boolean nextBoolean() {
            RandomSequences.this.setDirty();
            return this.random.nextBoolean();
        }

        @Override // net.minecraft.util.RandomSource
        public float nextFloat() {
            RandomSequences.this.setDirty();
            return this.random.nextFloat();
        }

        @Override // net.minecraft.util.RandomSource
        public double nextDouble() {
            RandomSequences.this.setDirty();
            return this.random.nextDouble();
        }

        @Override // net.minecraft.util.RandomSource
        public double nextGaussian() {
            RandomSequences.this.setDirty();
            return this.random.nextGaussian();
        }

        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if ($$0 instanceof DirtyMarkingRandomSource) {
                DirtyMarkingRandomSource $$1 = (DirtyMarkingRandomSource) $$0;
                return this.random.equals($$1.random);
            }
            return false;
        }
    }

    public RandomSource get(Identifier $$0, long $$1) {
        RandomSource $$2 = this.sequences.computeIfAbsent($$0, $$12 -> {
            return createSequence($$12, $$1);
        }).random();
        return new DirtyMarkingRandomSource($$2);
    }

    private RandomSequence createSequence(Identifier $$0, long $$1) {
        return createSequence($$0, $$1, this.salt, this.includeWorldSeed, this.includeSequenceId);
    }

    private RandomSequence createSequence(Identifier $$0, long $$1, int $$2, boolean $$3, boolean $$4) {
        long $$5 = ($$3 ? $$1 : 0L) ^ ((long) $$2);
        return new RandomSequence($$5, (Optional<Identifier>) ($$4 ? Optional.of($$0) : Optional.empty()));
    }

    public void forAllSequences(BiConsumer<Identifier, RandomSequence> $$0) {
        this.sequences.forEach($$0);
    }

    public void setSeedDefaults(int $$0, boolean $$1, boolean $$2) {
        this.salt = $$0;
        this.includeWorldSeed = $$1;
        this.includeSequenceId = $$2;
    }

    public int clear() {
        int $$0 = this.sequences.size();
        this.sequences.clear();
        return $$0;
    }

    public void reset(Identifier $$0, long $$1) {
        this.sequences.put($$0, createSequence($$0, $$1));
    }

    public void reset(Identifier $$0, long $$1, int $$2, boolean $$3, boolean $$4) {
        this.sequences.put($$0, createSequence($$0, $$1, $$2, $$3, $$4));
    }

    private int salt() {
        return this.salt;
    }

    private boolean includeWorldSeed() {
        return this.includeWorldSeed;
    }

    private boolean includeSequenceId() {
        return this.includeSequenceId;
    }
}

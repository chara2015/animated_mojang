package net.minecraft.world.level.chunk;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/PalettedContainer.class */
public class PalettedContainer<T> implements PaletteResize<T>, PalettedContainerRO<T> {
    private static final int MIN_PALETTE_BITS = 0;
    private volatile Data<T> data;
    private final Strategy<T> strategy;
    private final ThreadingDetector threadingDetector;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/PalettedContainer$CountConsumer.class */
    @FunctionalInterface
    public interface CountConsumer<T> {
        void accept(T t, int i);
    }

    public void acquire() {
        this.threadingDetector.checkAndLock();
    }

    public void release() {
        this.threadingDetector.checkAndUnlock();
    }

    public static <T> Codec<PalettedContainer<T>> codecRW(Codec<T> $$0, Strategy<T> $$1, T $$2) {
        PalettedContainerRO.Unpacker<T, PalettedContainer<T>> $$3 = PalettedContainer::unpack;
        return codec($$0, $$1, $$2, $$3);
    }

    public static <T> Codec<PalettedContainerRO<T>> codecRO(Codec<T> $$0, Strategy<T> $$1, T $$2) {
        PalettedContainerRO.Unpacker<T, PalettedContainerRO<T>> $$3 = ($$02, $$12) -> {
            return unpack($$02, $$12).map($$02 -> {
                return $$02;
            });
        };
        return codec($$0, $$1, $$2, $$3);
    }

    private static <T, C extends PalettedContainerRO<T>> Codec<C> codec(Codec<T> $$0, Strategy<T> $$1, T $$2, PalettedContainerRO.Unpacker<T, C> $$3) {
        return RecordCodecBuilder.create($$22 -> {
            return $$22.group($$0.mapResult(ExtraCodecs.orElsePartial($$2)).listOf().fieldOf(StructureTemplate.PALETTE_TAG).forGetter((v0) -> {
                return v0.paletteEntries();
            }), Codec.LONG_STREAM.lenientOptionalFieldOf("data").forGetter((v0) -> {
                return v0.storage();
            })).apply($$22, PalettedContainerRO.PackedData::new);
        }).comapFlatMap($$23 -> {
            return $$3.read($$1, $$23);
        }, $$12 -> {
            return $$12.pack($$1);
        });
    }

    private PalettedContainer(Strategy<T> $$0, Configuration $$1, BitStorage $$2, Palette<T> $$3) {
        this.threadingDetector = new ThreadingDetector("PalettedContainer");
        this.strategy = $$0;
        this.data = new Data<>($$1, $$2, $$3);
    }

    private PalettedContainer(PalettedContainer<T> $$0) {
        this.threadingDetector = new ThreadingDetector("PalettedContainer");
        this.strategy = $$0.strategy;
        this.data = $$0.data.copy();
    }

    public PalettedContainer(T $$0, Strategy<T> $$1) {
        this.threadingDetector = new ThreadingDetector("PalettedContainer");
        this.strategy = $$1;
        this.data = createOrReuseData(null, 0);
        ((Data) this.data).palette.idFor($$0, this);
    }

    private Data<T> createOrReuseData(Data<T> $$0, int $$1) {
        Configuration $$2 = this.strategy.getConfigurationForBitCount($$1);
        if ($$0 != null && $$2.equals($$0.configuration())) {
            return $$0;
        }
        BitStorage $$3 = $$2.bitsInMemory() == 0 ? new ZeroBitStorage(this.strategy.entryCount()) : new SimpleBitStorage($$2.bitsInMemory(), this.strategy.entryCount());
        Palette<T> $$4 = $$2.createPalette(this.strategy, List.of());
        return new Data<>($$2, $$3, $$4);
    }

    @Override // net.minecraft.world.level.chunk.PaletteResize
    public int onResize(int $$0, T $$1) {
        Data<T> $$2 = this.data;
        Data<T> $$3 = createOrReuseData($$2, $$0);
        $$3.copyFrom(((Data) $$2).palette, ((Data) $$2).storage);
        this.data = $$3;
        return ((Data) $$3).palette.idFor($$1, PaletteResize.noResizeExpected());
    }

    public T getAndSet(int $$0, int $$1, int $$2, T $$3) {
        acquire();
        try {
            T andSet = getAndSet(this.strategy.getIndex($$0, $$1, $$2), $$3);
            release();
            return andSet;
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    public T getAndSetUnchecked(int $$0, int $$1, int $$2, T $$3) {
        return getAndSet(this.strategy.getIndex($$0, $$1, $$2), $$3);
    }

    private T getAndSet(int $$0, T $$1) {
        int $$2 = ((Data) this.data).palette.idFor($$1, this);
        int $$3 = ((Data) this.data).storage.getAndSet($$0, $$2);
        return ((Data) this.data).palette.valueFor($$3);
    }

    public void set(int $$0, int $$1, int $$2, T $$3) {
        acquire();
        try {
            set(this.strategy.getIndex($$0, $$1, $$2), $$3);
            release();
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    private void set(int $$0, T $$1) {
        int $$2 = ((Data) this.data).palette.idFor($$1, this);
        ((Data) this.data).storage.set($$0, $$2);
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public T get(int $$0, int $$1, int $$2) {
        return get(this.strategy.getIndex($$0, $$1, $$2));
    }

    protected T get(int $$0) {
        Data<T> $$1 = this.data;
        return ((Data) $$1).palette.valueFor(((Data) $$1).storage.get($$0));
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public void getAll(Consumer<T> $$0) {
        Palette<T> $$1 = this.data.palette();
        IntArraySet intArraySet = new IntArraySet();
        BitStorage bitStorage = ((Data) this.data).storage;
        Objects.requireNonNull(intArraySet);
        bitStorage.getAll(intArraySet::add);
        intArraySet.forEach($$2 -> {
            $$0.accept($$1.valueFor($$2));
        });
    }

    public void read(FriendlyByteBuf $$0) {
        acquire();
        try {
            int $$1 = $$0.readByte();
            Data<T> $$2 = createOrReuseData(this.data, $$1);
            ((Data) $$2).palette.read($$0, this.strategy.globalMap());
            $$0.readFixedSizeLongArray(((Data) $$2).storage.getRaw());
            this.data = $$2;
            release();
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public void write(FriendlyByteBuf $$0) {
        acquire();
        try {
            this.data.write($$0, this.strategy.globalMap());
        } finally {
            release();
        }
    }

    @VisibleForTesting
    public static <T> DataResult<PalettedContainer<T>> unpack(Strategy<T> $$0, PalettedContainerRO.PackedData<T> $$1) {
        Palette<T> $$20;
        BitStorage $$19;
        List<T> $$2 = $$1.paletteEntries();
        int $$3 = $$0.entryCount();
        Configuration $$4 = $$0.getConfigurationForPaletteSize($$2.size());
        int $$5 = $$4.bitsInStorage();
        if ($$1.bitsPerEntry() != -1 && $$5 != $$1.bitsPerEntry()) {
            return DataResult.error(() -> {
                return "Invalid bit count, calculated " + $$5 + ", but container declared " + $$1.bitsPerEntry();
            });
        }
        if ($$4.bitsInMemory() == 0) {
            $$20 = $$4.createPalette($$0, $$2);
            $$19 = new ZeroBitStorage($$3);
        } else {
            Optional<LongStream> $$8 = $$1.storage();
            if ($$8.isEmpty()) {
                return DataResult.error(() -> {
                    return "Missing values for non-zero storage";
                });
            }
            long[] $$9 = $$8.get().toArray();
            try {
                if ($$4.alwaysRepack() || $$4.bitsInMemory() != $$5) {
                    Palette<T> $$10 = new HashMapPalette<>($$5, $$2);
                    SimpleBitStorage $$11 = new SimpleBitStorage($$5, $$3, $$9);
                    Palette<T> $$12 = $$4.createPalette($$0, $$2);
                    int[] $$13 = reencodeContents($$11, $$10, $$12);
                    $$20 = $$12;
                    $$19 = new SimpleBitStorage($$4.bitsInMemory(), $$3, $$13);
                } else {
                    $$20 = $$4.createPalette($$0, $$2);
                    $$19 = new SimpleBitStorage($$4.bitsInMemory(), $$3, $$9);
                }
            } catch (SimpleBitStorage.InitializationException $$18) {
                return DataResult.error(() -> {
                    return "Failed to read PalettedContainer: " + $$18.getMessage();
                });
            }
        }
        return DataResult.success(new PalettedContainer($$0, $$4, $$19, $$20));
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public PalettedContainerRO.PackedData<T> pack(Strategy<T> $$0) {
        Optional<LongStream> $$10;
        acquire();
        try {
            BitStorage $$1 = ((Data) this.data).storage;
            Palette<T> $$2 = ((Data) this.data).palette;
            HashMapPalette<T> $$3 = new HashMapPalette<>($$1.getBits());
            int $$4 = $$0.entryCount();
            int[] $$5 = reencodeContents($$1, $$2, $$3);
            Configuration $$6 = $$0.getConfigurationForPaletteSize($$3.getSize());
            int $$7 = $$6.bitsInStorage();
            if ($$7 != 0) {
                SimpleBitStorage $$8 = new SimpleBitStorage($$7, $$4, $$5);
                $$10 = Optional.of(Arrays.stream($$8.getRaw()));
            } else {
                $$10 = Optional.empty();
            }
            PalettedContainerRO.PackedData<T> packedData = new PalettedContainerRO.PackedData<>($$3.getEntries(), $$10, $$7);
            release();
            return packedData;
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    private static <T> int[] reencodeContents(BitStorage $$0, Palette<T> $$1, Palette<T> $$2) {
        int[] $$3 = new int[$$0.getSize()];
        $$0.unpack($$3);
        PaletteResize<T> $$4 = PaletteResize.noResizeExpected();
        int $$5 = -1;
        int $$6 = -1;
        for (int $$7 = 0; $$7 < $$3.length; $$7++) {
            int $$8 = $$3[$$7];
            if ($$8 != $$5) {
                $$5 = $$8;
                $$6 = $$2.idFor($$1.valueFor($$8), $$4);
            }
            $$3[$$7] = $$6;
        }
        return $$3;
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public int getSerializedSize() {
        return this.data.getSerializedSize(this.strategy.globalMap());
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public int bitsPerEntry() {
        return this.data.storage().getBits();
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public boolean maybeHas(Predicate<T> $$0) {
        return ((Data) this.data).palette.maybeHas($$0);
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public PalettedContainer<T> copy() {
        return new PalettedContainer<>(this);
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public PalettedContainer<T> recreate() {
        return new PalettedContainer<>(((Data) this.data).palette.valueFor(0), this.strategy);
    }

    @Override // net.minecraft.world.level.chunk.PalettedContainerRO
    public void count(CountConsumer<T> $$0) {
        if (((Data) this.data).palette.getSize() == 1) {
            $$0.accept(((Data) this.data).palette.valueFor(0), ((Data) this.data).storage.getSize());
            return;
        }
        Int2IntOpenHashMap $$1 = new Int2IntOpenHashMap();
        ((Data) this.data).storage.getAll($$12 -> {
            $$1.addTo($$12, 1);
        });
        $$1.int2IntEntrySet().forEach($$13 -> {
            $$0.accept(((Data) this.data).palette.valueFor($$13.getIntKey()), $$13.getIntValue());
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/PalettedContainer$Data.class */
    static final class Data<T> extends Record {
        private final Configuration configuration;
        private final BitStorage storage;
        private final Palette<T> palette;

        Data(Configuration $$0, BitStorage $$1, Palette<T> $$2) {
            this.configuration = $$0;
            this.storage = $$1;
            this.palette = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Data.class), Data.class, "configuration;storage;palette", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->configuration:Lnet/minecraft/world/level/chunk/Configuration;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->storage:Lnet/minecraft/util/BitStorage;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->palette:Lnet/minecraft/world/level/chunk/Palette;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Data.class), Data.class, "configuration;storage;palette", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->configuration:Lnet/minecraft/world/level/chunk/Configuration;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->storage:Lnet/minecraft/util/BitStorage;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->palette:Lnet/minecraft/world/level/chunk/Palette;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Data.class, Object.class), Data.class, "configuration;storage;palette", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->configuration:Lnet/minecraft/world/level/chunk/Configuration;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->storage:Lnet/minecraft/util/BitStorage;", "FIELD:Lnet/minecraft/world/level/chunk/PalettedContainer$Data;->palette:Lnet/minecraft/world/level/chunk/Palette;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Configuration configuration() {
            return this.configuration;
        }

        public BitStorage storage() {
            return this.storage;
        }

        public Palette<T> palette() {
            return this.palette;
        }

        public void copyFrom(Palette<T> $$0, BitStorage $$1) {
            PaletteResize<T> $$2 = PaletteResize.noResizeExpected();
            for (int $$3 = 0; $$3 < $$1.getSize(); $$3++) {
                T $$4 = $$0.valueFor($$1.get($$3));
                this.storage.set($$3, this.palette.idFor($$4, $$2));
            }
        }

        public int getSerializedSize(IdMap<T> $$0) {
            return 1 + this.palette.getSerializedSize($$0) + (this.storage.getRaw().length * 8);
        }

        public void write(FriendlyByteBuf $$0, IdMap<T> $$1) {
            $$0.m1593writeByte(this.storage.getBits());
            this.palette.write($$0, $$1);
            $$0.writeFixedSizeLongArray(this.storage.getRaw());
        }

        public Data<T> copy() {
            return new Data<>(this.configuration, this.storage.copy(), this.palette.copy());
        }
    }
}

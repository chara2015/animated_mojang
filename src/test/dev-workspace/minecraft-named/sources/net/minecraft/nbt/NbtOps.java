package net.minecraft.nbt;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps.class */
public class NbtOps implements DynamicOps<Tag> {
    public static final NbtOps INSTANCE = new NbtOps();

    /* JADX INFO: renamed from: createList, reason: collision with other method in class */
    public /* synthetic */ Object m1530createList(Stream stream) {
        return createList((Stream<Tag>) stream);
    }

    /* JADX INFO: renamed from: createMap, reason: collision with other method in class */
    public /* synthetic */ Object m1531createMap(Stream stream) {
        return createMap((Stream<Pair<Tag, Tag>>) stream);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, MapLike mapLike) {
        return mergeToMap((Tag) obj, (MapLike<Tag>) mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, Map map) {
        return mergeToMap((Tag) obj, (Map<Tag, Tag>) map);
    }

    public /* synthetic */ DataResult mergeToList(Object obj, List list) {
        return mergeToList((Tag) obj, (List<Tag>) list);
    }

    private NbtOps() {
    }

    /* JADX INFO: renamed from: empty, reason: merged with bridge method [inline-methods] */
    public Tag m1543empty() {
        return EndTag.INSTANCE;
    }

    /* JADX INFO: renamed from: emptyList, reason: merged with bridge method [inline-methods] */
    public Tag m1541emptyList() {
        return new ListTag();
    }

    /* JADX INFO: renamed from: emptyMap, reason: merged with bridge method [inline-methods] */
    public Tag m1542emptyMap() {
        return new CompoundTag();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public <U> U convertTo(DynamicOps<U> dynamicOps, Tag tag) throws MatchException {
        U u;
        Objects.requireNonNull(tag);
        try {
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), EndTag.class, ByteTag.class, ShortTag.class, IntTag.class, LongTag.class, FloatTag.class, DoubleTag.class, ByteArrayTag.class, StringTag.class, ListTag.class, CompoundTag.class, IntArrayTag.class, LongArrayTag.class).dynamicInvoker().invoke(tag, 0) /* invoke-custom */) {
                case 0:
                    return (U) dynamicOps.empty();
                case 1:
                    u = (U) dynamicOps.createByte(((ByteTag) tag).value());
                    break;
                case 2:
                    u = (U) dynamicOps.createShort(((ShortTag) tag).value());
                    break;
                case 3:
                    u = (U) dynamicOps.createInt(((IntTag) tag).value());
                    break;
                case 4:
                    u = (U) dynamicOps.createLong(((LongTag) tag).value());
                    break;
                case 5:
                    u = (U) dynamicOps.createFloat(((FloatTag) tag).value());
                    break;
                case 6:
                    u = (U) dynamicOps.createDouble(((DoubleTag) tag).value());
                    break;
                case 7:
                    return (U) dynamicOps.createByteList(ByteBuffer.wrap(((ByteArrayTag) tag).getAsByteArray()));
                case 8:
                    u = (U) dynamicOps.createString(((StringTag) tag).value());
                    break;
                case 9:
                    return (U) convertList(dynamicOps, (ListTag) tag);
                case 10:
                    return (U) convertMap(dynamicOps, (CompoundTag) tag);
                case 11:
                    return (U) dynamicOps.createIntList(Arrays.stream(((IntArrayTag) tag).getAsIntArray()));
                case 12:
                    return (U) dynamicOps.createLongList(Arrays.stream(((LongArrayTag) tag).getAsLongArray()));
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            return u;
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    public DataResult<Number> getNumberValue(Tag $$0) {
        return (DataResult) $$0.asNumber().map((v0) -> {
            return DataResult.success(v0);
        }).orElseGet(() -> {
            return DataResult.error(() -> {
                return "Not a number";
            });
        });
    }

    /* JADX INFO: renamed from: createNumeric, reason: merged with bridge method [inline-methods] */
    public Tag m1540createNumeric(Number $$0) {
        return DoubleTag.valueOf($$0.doubleValue());
    }

    /* JADX INFO: renamed from: createByte, reason: merged with bridge method [inline-methods] */
    public Tag m1539createByte(byte $$0) {
        return ByteTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createShort, reason: merged with bridge method [inline-methods] */
    public Tag m1538createShort(short $$0) {
        return ShortTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createInt, reason: merged with bridge method [inline-methods] */
    public Tag m1537createInt(int $$0) {
        return IntTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createLong, reason: merged with bridge method [inline-methods] */
    public Tag m1536createLong(long $$0) {
        return LongTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createFloat, reason: merged with bridge method [inline-methods] */
    public Tag m1535createFloat(float $$0) {
        return FloatTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createDouble, reason: merged with bridge method [inline-methods] */
    public Tag m1534createDouble(double $$0) {
        return DoubleTag.valueOf($$0);
    }

    /* JADX INFO: renamed from: createBoolean, reason: merged with bridge method [inline-methods] */
    public Tag m1533createBoolean(boolean $$0) {
        return ByteTag.valueOf($$0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public DataResult<String> getStringValue(Tag $$0) throws MatchException {
        if ($$0 instanceof StringTag) {
            try {
                String $$1 = ((StringTag) $$0).value();
                return DataResult.success($$1);
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        return DataResult.error(() -> {
            return "Not a string";
        });
    }

    /* JADX INFO: renamed from: createString, reason: merged with bridge method [inline-methods] */
    public Tag m1532createString(String $$0) {
        return StringTag.valueOf($$0);
    }

    public DataResult<Tag> mergeToList(Tag $$0, Tag $$1) {
        return (DataResult) createCollector($$0).map($$12 -> {
            return DataResult.success($$12.accept($$1).result());
        }).orElseGet(() -> {
            return DataResult.error(() -> {
                return "mergeToList called with not a list: " + String.valueOf($$0);
            }, $$0);
        });
    }

    public DataResult<Tag> mergeToList(Tag $$0, List<Tag> $$1) {
        return (DataResult) createCollector($$0).map($$12 -> {
            return DataResult.success($$12.acceptAll($$1).result());
        }).orElseGet(() -> {
            return DataResult.error(() -> {
                return "mergeToList called with not a list: " + String.valueOf($$0);
            }, $$0);
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public DataResult<Tag> mergeToMap(Tag $$0, Tag $$1, Tag $$2) throws MatchException {
        CompoundTag compoundTag;
        if (!($$0 instanceof CompoundTag) && !($$0 instanceof EndTag)) {
            return DataResult.error(() -> {
                return "mergeToMap called with not a map: " + String.valueOf($$0);
            }, $$0);
        }
        if (!($$1 instanceof StringTag)) {
            return DataResult.error(() -> {
                return "key is not a string: " + String.valueOf($$1);
            }, $$0);
        }
        try {
            String $$4 = ((StringTag) $$1).value();
            if ($$0 instanceof CompoundTag) {
                CompoundTag $$5 = (CompoundTag) $$0;
                compoundTag = $$5.shallowCopy();
            } else {
                compoundTag = new CompoundTag();
            }
            CompoundTag $$6 = compoundTag;
            $$6.put($$4, $$2);
            return DataResult.success($$6);
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    public DataResult<Tag> mergeToMap(Tag $$0, MapLike<Tag> $$1) {
        CompoundTag compoundTag;
        if (!($$0 instanceof CompoundTag) && !($$0 instanceof EndTag)) {
            return DataResult.error(() -> {
                return "mergeToMap called with not a map: " + String.valueOf($$0);
            }, $$0);
        }
        Iterator<Pair<Tag, Tag>> $$2 = $$1.entries().iterator();
        if (!$$2.hasNext()) {
            if ($$0 == m1543empty()) {
                return DataResult.success(m1542emptyMap());
            }
            return DataResult.success($$0);
        }
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$3 = (CompoundTag) $$0;
            compoundTag = $$3.shallowCopy();
        } else {
            compoundTag = new CompoundTag();
        }
        CompoundTag $$4 = compoundTag;
        List<Tag> $$5 = new ArrayList<>();
        $$2.forEachRemaining($$22 -> {
            Tag $$32 = (Tag) $$22.getFirst();
            if (!($$32 instanceof StringTag)) {
                $$5.add($$32);
                return;
            }
            StringTag $$42 = (StringTag) $$32;
            try {
                String $$52 = $$42.value();
                $$4.put($$52, (Tag) $$22.getSecond());
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        });
        if (!$$5.isEmpty()) {
            return DataResult.error(() -> {
                return "some keys are not strings: " + String.valueOf($$5);
            }, $$4);
        }
        return DataResult.success($$4);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public DataResult<Tag> mergeToMap(Tag $$0, Map<Tag, Tag> $$1) throws MatchException {
        CompoundTag compoundTag;
        if (!($$0 instanceof CompoundTag) && !($$0 instanceof EndTag)) {
            return DataResult.error(() -> {
                return "mergeToMap called with not a map: " + String.valueOf($$0);
            }, $$0);
        }
        if ($$1.isEmpty()) {
            if ($$0 == m1543empty()) {
                return DataResult.success(m1542emptyMap());
            }
            return DataResult.success($$0);
        }
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$2 = (CompoundTag) $$0;
            compoundTag = $$2.shallowCopy();
        } else {
            compoundTag = new CompoundTag();
        }
        CompoundTag $$3 = compoundTag;
        List<Tag> $$4 = new ArrayList<>();
        for (Map.Entry<Tag, Tag> $$5 : $$1.entrySet()) {
            Tag $$6 = $$5.getKey();
            if ($$6 instanceof StringTag) {
                try {
                    String $$7 = ((StringTag) $$6).value();
                    $$3.put($$7, $$5.getValue());
                } catch (Throwable th) {
                    throw new MatchException(th.toString(), th);
                }
            } else {
                $$4.add($$6);
            }
        }
        if (!$$4.isEmpty()) {
            return DataResult.error(() -> {
                return "some keys are not strings: " + String.valueOf($$4);
            }, $$3);
        }
        return DataResult.success($$3);
    }

    public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag $$0) {
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$1 = (CompoundTag) $$0;
            return DataResult.success($$1.entrySet().stream().map($$02 -> {
                return Pair.of(m1532createString((String) $$02.getKey()), (Tag) $$02.getValue());
            }));
        }
        return DataResult.error(() -> {
            return "Not a map: " + String.valueOf($$0);
        });
    }

    public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag $$0) {
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$1 = (CompoundTag) $$0;
            return DataResult.success($$12 -> {
                for (Map.Entry<String, Tag> $$2 : $$1.entrySet()) {
                    $$12.accept(m1532createString($$2.getKey()), $$2.getValue());
                }
            });
        }
        return DataResult.error(() -> {
            return "Not a map: " + String.valueOf($$0);
        });
    }

    public DataResult<MapLike<Tag>> getMap(Tag $$0) {
        if ($$0 instanceof CompoundTag) {
            final CompoundTag $$1 = (CompoundTag) $$0;
            return DataResult.success(new MapLike<Tag>() { // from class: net.minecraft.nbt.NbtOps.1
                /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                public Tag get(Tag $$02) throws MatchException {
                    if ($$02 instanceof StringTag) {
                        try {
                            String $$12 = ((StringTag) $$02).value();
                            return $$1.get($$12);
                        } catch (Throwable th) {
                            throw new MatchException(th.toString(), th);
                        }
                    }
                    throw new UnsupportedOperationException("Cannot get map entry with non-string key: " + String.valueOf($$02));
                }

                /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
                public Tag m1544get(String $$02) {
                    return $$1.get($$02);
                }

                public Stream<Pair<Tag, Tag>> entries() {
                    return $$1.entrySet().stream().map($$02 -> {
                        return Pair.of(NbtOps.this.m1532createString((String) $$02.getKey()), (Tag) $$02.getValue());
                    });
                }

                public String toString() {
                    return "MapLike[" + String.valueOf($$1) + "]";
                }
            });
        }
        return DataResult.error(() -> {
            return "Not a map: " + String.valueOf($$0);
        });
    }

    public Tag createMap(Stream<Pair<Tag, Tag>> $$0) {
        CompoundTag $$1 = new CompoundTag();
        $$0.forEach($$12 -> {
            Tag $$2 = (Tag) $$12.getFirst();
            Tag $$3 = (Tag) $$12.getSecond();
            if ($$2 instanceof StringTag) {
                StringTag $$4 = (StringTag) $$2;
                try {
                    String $$5 = $$4.value();
                    $$1.put($$5, $$3);
                    return;
                } catch (Throwable th) {
                    throw new MatchException(th.toString(), th);
                }
            }
            throw new UnsupportedOperationException("Cannot create map with non-string key: " + String.valueOf($$2));
        });
        return $$1;
    }

    public DataResult<Stream<Tag>> getStream(Tag $$0) {
        if ($$0 instanceof CollectionTag) {
            CollectionTag $$1 = (CollectionTag) $$0;
            return DataResult.success($$1.stream());
        }
        return DataResult.error(() -> {
            return "Not a list";
        });
    }

    public DataResult<Consumer<Consumer<Tag>>> getList(Tag $$0) {
        if ($$0 instanceof CollectionTag) {
            CollectionTag $$1 = (CollectionTag) $$0;
            Objects.requireNonNull($$1);
            return DataResult.success($$1::forEach);
        }
        return DataResult.error(() -> {
            return "Not a list: " + String.valueOf($$0);
        });
    }

    public DataResult<ByteBuffer> getByteBuffer(Tag $$0) {
        if ($$0 instanceof ByteArrayTag) {
            ByteArrayTag $$1 = (ByteArrayTag) $$0;
            return DataResult.success(ByteBuffer.wrap($$1.getAsByteArray()));
        }
        return super.getByteBuffer($$0);
    }

    /* JADX INFO: renamed from: createByteList, reason: merged with bridge method [inline-methods] */
    public Tag m1529createByteList(ByteBuffer $$0) {
        ByteBuffer $$1 = $$0.duplicate().clear();
        byte[] $$2 = new byte[$$0.capacity()];
        $$1.get(0, $$2, 0, $$2.length);
        return new ByteArrayTag($$2);
    }

    public DataResult<IntStream> getIntStream(Tag $$0) {
        if ($$0 instanceof IntArrayTag) {
            IntArrayTag $$1 = (IntArrayTag) $$0;
            return DataResult.success(Arrays.stream($$1.getAsIntArray()));
        }
        return super.getIntStream($$0);
    }

    /* JADX INFO: renamed from: createIntList, reason: merged with bridge method [inline-methods] */
    public Tag m1528createIntList(IntStream $$0) {
        return new IntArrayTag($$0.toArray());
    }

    public DataResult<LongStream> getLongStream(Tag $$0) {
        if ($$0 instanceof LongArrayTag) {
            LongArrayTag $$1 = (LongArrayTag) $$0;
            return DataResult.success(Arrays.stream($$1.getAsLongArray()));
        }
        return super.getLongStream($$0);
    }

    /* JADX INFO: renamed from: createLongList, reason: merged with bridge method [inline-methods] */
    public Tag m1527createLongList(LongStream $$0) {
        return new LongArrayTag($$0.toArray());
    }

    public Tag createList(Stream<Tag> $$0) {
        return new ListTag((List) $$0.collect(Util.toMutableList()));
    }

    public Tag remove(Tag $$0, String $$1) {
        if ($$0 instanceof CompoundTag) {
            CompoundTag $$2 = (CompoundTag) $$0;
            CompoundTag $$3 = $$2.shallowCopy();
            $$3.remove($$1);
            return $$3;
        }
        return $$0;
    }

    public String toString() {
        return "NBT";
    }

    public RecordBuilder<Tag> mapBuilder() {
        return new NbtRecordBuilder(this);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$NbtRecordBuilder.class */
    class NbtRecordBuilder extends RecordBuilder.AbstractStringBuilder<Tag, CompoundTag> {
        protected NbtRecordBuilder(NbtOps nbtOps) {
            super(nbtOps);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: initBuilder, reason: merged with bridge method [inline-methods] */
        public CompoundTag m1545initBuilder() {
            return new CompoundTag();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public CompoundTag append(String $$0, Tag $$1, CompoundTag $$2) {
            $$2.put($$0, $$1);
            return $$2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public DataResult<Tag> build(CompoundTag $$0, Tag $$1) {
            if ($$1 == null || $$1 == EndTag.INSTANCE) {
                return DataResult.success($$0);
            }
            if ($$1 instanceof CompoundTag) {
                CompoundTag $$2 = (CompoundTag) $$1;
                CompoundTag $$3 = $$2.shallowCopy();
                for (Map.Entry<String, Tag> $$4 : $$0.entrySet()) {
                    $$3.put($$4.getKey(), $$4.getValue());
                }
                return DataResult.success($$3);
            }
            return DataResult.error(() -> {
                return "mergeToMap called with not a map: " + String.valueOf($$1);
            }, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$ListCollector.class */
    interface ListCollector {
        ListCollector accept(Tag tag);

        Tag result();

        default ListCollector acceptAll(Iterable<Tag> $$0) {
            ListCollector $$1 = this;
            for (Tag $$2 : $$0) {
                $$1 = $$1.accept($$2);
            }
            return $$1;
        }

        default ListCollector acceptAll(Stream<Tag> $$0) {
            Objects.requireNonNull($$0);
            return acceptAll($$0::iterator);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static Optional<ListCollector> createCollector(Tag $$0) throws MatchException {
        if ($$0 instanceof EndTag) {
            return Optional.of(new GenericListCollector());
        }
        if ($$0 instanceof CollectionTag) {
            CollectionTag $$1 = (CollectionTag) $$0;
            if ($$1.isEmpty()) {
                return Optional.of(new GenericListCollector());
            }
            Objects.requireNonNull($$1);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), ListTag.class, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class).dynamicInvoker().invoke($$1, 0) /* invoke-custom */) {
                case 0:
                    ListTag $$2 = (ListTag) $$1;
                    return Optional.of(new GenericListCollector($$2));
                case 1:
                    ByteArrayTag $$3 = (ByteArrayTag) $$1;
                    return Optional.of(new ByteListCollector($$3.getAsByteArray()));
                case 2:
                    IntArrayTag $$4 = (IntArrayTag) $$1;
                    return Optional.of(new IntListCollector($$4.getAsIntArray()));
                case 3:
                    LongArrayTag $$5 = (LongArrayTag) $$1;
                    return Optional.of(new LongListCollector($$5.getAsLongArray()));
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        return Optional.empty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$GenericListCollector.class */
    static class GenericListCollector implements ListCollector {
        private final ListTag result = new ListTag();

        GenericListCollector() {
        }

        GenericListCollector(ListTag $$0) {
            this.result.addAll($$0);
        }

        public GenericListCollector(IntArrayList $$0) {
            $$0.forEach($$02 -> {
                this.result.add(IntTag.valueOf($$02));
            });
        }

        public GenericListCollector(ByteArrayList $$0) {
            $$0.forEach($$02 -> {
                this.result.add(ByteTag.valueOf($$02));
            });
        }

        public GenericListCollector(LongArrayList $$0) {
            $$0.forEach($$02 -> {
                this.result.add(LongTag.valueOf($$02));
            });
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public ListCollector accept(Tag $$0) {
            this.result.add($$0);
            return this;
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public Tag result() {
            return this.result;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$IntListCollector.class */
    static class IntListCollector implements ListCollector {
        private final IntArrayList values = new IntArrayList();

        public IntListCollector(int[] $$0) {
            this.values.addElements(0, $$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public ListCollector accept(Tag $$0) {
            if ($$0 instanceof IntTag) {
                IntTag $$1 = (IntTag) $$0;
                this.values.add($$1.intValue());
                return this;
            }
            return new GenericListCollector(this.values).accept($$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public Tag result() {
            return new IntArrayTag(this.values.toIntArray());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$ByteListCollector.class */
    static class ByteListCollector implements ListCollector {
        private final ByteArrayList values = new ByteArrayList();

        public ByteListCollector(byte[] $$0) {
            this.values.addElements(0, $$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public ListCollector accept(Tag $$0) {
            if ($$0 instanceof ByteTag) {
                ByteTag $$1 = (ByteTag) $$0;
                this.values.add($$1.byteValue());
                return this;
            }
            return new GenericListCollector(this.values).accept($$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public Tag result() {
            return new ByteArrayTag(this.values.toByteArray());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtOps$LongListCollector.class */
    static class LongListCollector implements ListCollector {
        private final LongArrayList values = new LongArrayList();

        public LongListCollector(long[] $$0) {
            this.values.addElements(0, $$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public ListCollector accept(Tag $$0) {
            if ($$0 instanceof LongTag) {
                LongTag $$1 = (LongTag) $$0;
                this.values.add($$1.longValue());
                return this;
            }
            return new GenericListCollector(this.values).accept($$0);
        }

        @Override // net.minecraft.nbt.NbtOps.ListCollector
        public Tag result() {
            return new LongArrayTag(this.values.toLongArray());
        }
    }
}

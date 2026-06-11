package net.minecraft.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/HashOps.class */
public class HashOps implements DynamicOps<HashCode> {
    private static final byte TAG_EMPTY = 1;
    private static final byte TAG_MAP_START = 2;
    private static final byte TAG_MAP_END = 3;
    private static final byte TAG_LIST_START = 4;
    private static final byte TAG_LIST_END = 5;
    private static final byte TAG_BYTE = 6;
    private static final byte TAG_SHORT = 7;
    private static final byte TAG_INT = 8;
    private static final byte TAG_LONG = 9;
    private static final byte TAG_FLOAT = 10;
    private static final byte TAG_DOUBLE = 11;
    private static final byte TAG_STRING = 12;
    private static final byte TAG_BOOLEAN = 13;
    private static final byte TAG_BYTE_ARRAY_START = 14;
    private static final byte TAG_BYTE_ARRAY_END = 15;
    private static final byte TAG_INT_ARRAY_START = 16;
    private static final byte TAG_INT_ARRAY_END = 17;
    private static final byte TAG_LONG_ARRAY_START = 18;
    private static final byte TAG_LONG_ARRAY_END = 19;
    private static final byte[] EMPTY_PAYLOAD = {1};
    private static final byte[] FALSE_PAYLOAD = {13, 0};
    private static final byte[] TRUE_PAYLOAD = {13, 1};
    public static final byte[] EMPTY_MAP_PAYLOAD = {2, 3};
    public static final byte[] EMPTY_LIST_PAYLOAD = {4, 5};
    private static final DataResult<Object> UNSUPPORTED_OPERATION_ERROR = DataResult.error(() -> {
        return "Unsupported operation";
    });
    private static final Comparator<HashCode> HASH_COMPARATOR = Comparator.comparingLong((v0) -> {
        return v0.padToLong();
    });
    private static final Comparator<Map.Entry<HashCode, HashCode>> MAP_ENTRY_ORDER = Map.Entry.comparingByKey(HASH_COMPARATOR).thenComparing(Map.Entry.comparingByValue(HASH_COMPARATOR));
    private static final Comparator<Pair<HashCode, HashCode>> MAPLIKE_ENTRY_ORDER = Comparator.comparing((v0) -> {
        return v0.getFirst();
    }, HASH_COMPARATOR).thenComparing((v0) -> {
        return v0.getSecond();
    }, HASH_COMPARATOR);
    public static final HashOps CRC32C_INSTANCE = new HashOps(Hashing.crc32c());
    final HashFunction hashFunction;
    final HashCode empty;
    private final HashCode emptyMap;
    private final HashCode emptyList;
    private final HashCode trueHash;
    private final HashCode falseHash;

    public /* synthetic */ Object updateGeneric(Object obj, Object obj2, Function function) {
        return updateGeneric((HashCode) obj, (HashCode) obj2, (Function<HashCode, HashCode>) function);
    }

    public /* synthetic */ Object update(Object obj, String str, Function function) {
        return update((HashCode) obj, str, (Function<HashCode, HashCode>) function);
    }

    /* JADX INFO: renamed from: createList, reason: collision with other method in class */
    public /* synthetic */ Object m2350createList(Stream stream) {
        return createList((Stream<HashCode>) stream);
    }

    /* JADX INFO: renamed from: createMap, reason: collision with other method in class */
    public /* synthetic */ Object m2351createMap(Map map) {
        return createMap((Map<HashCode, HashCode>) map);
    }

    /* JADX INFO: renamed from: createMap, reason: collision with other method in class */
    public /* synthetic */ Object m2352createMap(Stream stream) {
        return createMap((Stream<Pair<HashCode, HashCode>>) stream);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, MapLike mapLike) {
        return mergeToMap((HashCode) obj, (MapLike<HashCode>) mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, Map map) {
        return mergeToMap((HashCode) obj, (Map<HashCode, HashCode>) map);
    }

    public /* synthetic */ DataResult mergeToList(Object obj, List list) {
        return mergeToList((HashCode) obj, (List<HashCode>) list);
    }

    public HashOps(HashFunction $$0) {
        this.hashFunction = $$0;
        this.empty = $$0.hashBytes(EMPTY_PAYLOAD);
        this.emptyMap = $$0.hashBytes(EMPTY_MAP_PAYLOAD);
        this.emptyList = $$0.hashBytes(EMPTY_LIST_PAYLOAD);
        this.falseHash = $$0.hashBytes(FALSE_PAYLOAD);
        this.trueHash = $$0.hashBytes(TRUE_PAYLOAD);
    }

    /* JADX INFO: renamed from: empty, reason: merged with bridge method [inline-methods] */
    public HashCode m2364empty() {
        return this.empty;
    }

    /* JADX INFO: renamed from: emptyMap, reason: merged with bridge method [inline-methods] */
    public HashCode m2363emptyMap() {
        return this.emptyMap;
    }

    /* JADX INFO: renamed from: emptyList, reason: merged with bridge method [inline-methods] */
    public HashCode m2362emptyList() {
        return this.emptyList;
    }

    /* JADX INFO: renamed from: createNumeric, reason: merged with bridge method [inline-methods] */
    public HashCode m2361createNumeric(Number $$0) {
        Objects.requireNonNull($$0);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), Byte.class, Short.class, Integer.class, Long.class, Double.class, Float.class).dynamicInvoker().invoke($$0, 0) /* invoke-custom */) {
            case 0:
                Byte $$1 = (Byte) $$0;
                return m2360createByte($$1.byteValue());
            case 1:
                Short $$2 = (Short) $$0;
                return m2359createShort($$2.shortValue());
            case 2:
                Integer $$3 = (Integer) $$0;
                return m2358createInt($$3.intValue());
            case 3:
                Long $$4 = (Long) $$0;
                return m2357createLong($$4.longValue());
            case 4:
                Double $$5 = (Double) $$0;
                return m2355createDouble($$5.doubleValue());
            case 5:
                Float $$6 = (Float) $$0;
                return m2356createFloat($$6.floatValue());
            default:
                return m2355createDouble($$0.doubleValue());
        }
    }

    /* JADX INFO: renamed from: createByte, reason: merged with bridge method [inline-methods] */
    public HashCode m2360createByte(byte $$0) {
        return this.hashFunction.newHasher(2).putByte((byte) 6).putByte($$0).hash();
    }

    /* JADX INFO: renamed from: createShort, reason: merged with bridge method [inline-methods] */
    public HashCode m2359createShort(short $$0) {
        return this.hashFunction.newHasher(3).putByte((byte) 7).putShort($$0).hash();
    }

    /* JADX INFO: renamed from: createInt, reason: merged with bridge method [inline-methods] */
    public HashCode m2358createInt(int $$0) {
        return this.hashFunction.newHasher(5).putByte((byte) 8).putInt($$0).hash();
    }

    /* JADX INFO: renamed from: createLong, reason: merged with bridge method [inline-methods] */
    public HashCode m2357createLong(long $$0) {
        return this.hashFunction.newHasher(9).putByte((byte) 9).putLong($$0).hash();
    }

    /* JADX INFO: renamed from: createFloat, reason: merged with bridge method [inline-methods] */
    public HashCode m2356createFloat(float $$0) {
        return this.hashFunction.newHasher(5).putByte((byte) 10).putFloat($$0).hash();
    }

    /* JADX INFO: renamed from: createDouble, reason: merged with bridge method [inline-methods] */
    public HashCode m2355createDouble(double $$0) {
        return this.hashFunction.newHasher(9).putByte((byte) 11).putDouble($$0).hash();
    }

    /* JADX INFO: renamed from: createString, reason: merged with bridge method [inline-methods] */
    public HashCode m2353createString(String $$0) {
        return this.hashFunction.newHasher().putByte((byte) 12).putInt($$0.length()).putUnencodedChars($$0).hash();
    }

    /* JADX INFO: renamed from: createBoolean, reason: merged with bridge method [inline-methods] */
    public HashCode m2354createBoolean(boolean $$0) {
        return $$0 ? this.trueHash : this.falseHash;
    }

    private static Hasher hashMap(Hasher $$0, Map<HashCode, HashCode> $$1) {
        $$0.putByte((byte) 2);
        $$1.entrySet().stream().sorted(MAP_ENTRY_ORDER).forEach($$12 -> {
            $$0.putBytes(((HashCode) $$12.getKey()).asBytes()).putBytes(((HashCode) $$12.getValue()).asBytes());
        });
        $$0.putByte((byte) 3);
        return $$0;
    }

    static Hasher hashMap(Hasher $$0, Stream<Pair<HashCode, HashCode>> $$1) {
        $$0.putByte((byte) 2);
        $$1.sorted(MAPLIKE_ENTRY_ORDER).forEach($$12 -> {
            $$0.putBytes(((HashCode) $$12.getFirst()).asBytes()).putBytes(((HashCode) $$12.getSecond()).asBytes());
        });
        $$0.putByte((byte) 3);
        return $$0;
    }

    public HashCode createMap(Stream<Pair<HashCode, HashCode>> $$0) {
        return hashMap(this.hashFunction.newHasher(), $$0).hash();
    }

    public HashCode createMap(Map<HashCode, HashCode> $$0) {
        return hashMap(this.hashFunction.newHasher(), $$0).hash();
    }

    public HashCode createList(Stream<HashCode> $$0) {
        Hasher $$1 = this.hashFunction.newHasher();
        $$1.putByte((byte) 4);
        $$0.forEach($$12 -> {
            $$1.putBytes($$12.asBytes());
        });
        $$1.putByte((byte) 5);
        return $$1.hash();
    }

    /* JADX INFO: renamed from: createByteList, reason: merged with bridge method [inline-methods] */
    public HashCode m2349createByteList(ByteBuffer $$0) {
        Hasher $$1 = this.hashFunction.newHasher();
        $$1.putByte((byte) 14);
        $$1.putBytes($$0);
        $$1.putByte((byte) 15);
        return $$1.hash();
    }

    /* JADX INFO: renamed from: createIntList, reason: merged with bridge method [inline-methods] */
    public HashCode m2348createIntList(IntStream $$0) {
        Hasher $$1 = this.hashFunction.newHasher();
        $$1.putByte((byte) 16);
        Objects.requireNonNull($$1);
        $$0.forEach($$1::putInt);
        $$1.putByte((byte) 17);
        return $$1.hash();
    }

    /* JADX INFO: renamed from: createLongList, reason: merged with bridge method [inline-methods] */
    public HashCode m2347createLongList(LongStream $$0) {
        Hasher $$1 = this.hashFunction.newHasher();
        $$1.putByte((byte) 18);
        Objects.requireNonNull($$1);
        $$0.forEach($$1::putLong);
        $$1.putByte((byte) 19);
        return $$1.hash();
    }

    public HashCode remove(HashCode $$0, String $$1) {
        return $$0;
    }

    public RecordBuilder<HashCode> mapBuilder() {
        return new MapHashBuilder();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/HashOps$MapHashBuilder.class */
    final class MapHashBuilder extends RecordBuilder.AbstractUniversalBuilder<HashCode, List<Pair<HashCode, HashCode>>> {
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !HashOps.class.desiredAssertionStatus();
        }

        public MapHashBuilder() {
            super(HashOps.this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: initBuilder, reason: merged with bridge method [inline-methods] */
        public List<Pair<HashCode, HashCode>> m2367initBuilder() {
            return new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public List<Pair<HashCode, HashCode>> append(HashCode $$0, HashCode $$1, List<Pair<HashCode, HashCode>> $$2) {
            $$2.add(Pair.of($$0, $$1));
            return $$2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public DataResult<HashCode> build(List<Pair<HashCode, HashCode>> $$0, HashCode $$1) {
            if ($assertionsDisabled || HashOps.this.isEmpty($$1)) {
                return DataResult.success(HashOps.hashMap(HashOps.this.hashFunction.newHasher(), $$0.stream()).hash());
            }
            throw new AssertionError();
        }
    }

    public ListBuilder<HashCode> listBuilder() {
        return new ListHashBuilder();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/HashOps$ListHashBuilder.class */
    class ListHashBuilder extends AbstractListBuilder<HashCode, Hasher> {
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !HashOps.class.desiredAssertionStatus();
        }

        public ListHashBuilder() {
            super(HashOps.this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.util.AbstractListBuilder
        public Hasher initBuilder() {
            return HashOps.this.hashFunction.newHasher().putByte((byte) 4);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.minecraft.util.AbstractListBuilder
        public Hasher append(Hasher $$0, HashCode $$1) {
            return $$0.putBytes($$1.asBytes());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.minecraft.util.AbstractListBuilder
        public DataResult<HashCode> build(Hasher $$0, HashCode $$1) {
            if (!$assertionsDisabled && !$$1.equals(HashOps.this.empty)) {
                throw new AssertionError();
            }
            $$0.putByte((byte) 5);
            return DataResult.success($$0.hash());
        }
    }

    public String toString() {
        return "Hash " + String.valueOf(this.hashFunction);
    }

    public <U> U convertTo(DynamicOps<U> $$0, HashCode $$1) {
        throw new UnsupportedOperationException("Can't convert from this type");
    }

    public Number getNumberValue(HashCode $$0, Number $$1) {
        return $$1;
    }

    public HashCode set(HashCode $$0, String $$1, HashCode $$2) {
        return $$0;
    }

    public HashCode update(HashCode $$0, String $$1, Function<HashCode, HashCode> $$2) {
        return $$0;
    }

    public HashCode updateGeneric(HashCode $$0, HashCode $$1, Function<HashCode, HashCode> $$2) {
        return $$0;
    }

    private static <T> DataResult<T> unsupported() {
        return (DataResult<T>) UNSUPPORTED_OPERATION_ERROR;
    }

    public DataResult<HashCode> get(HashCode $$0, String $$1) {
        return unsupported();
    }

    public DataResult<HashCode> getGeneric(HashCode $$0, HashCode $$1) {
        return unsupported();
    }

    public DataResult<Number> getNumberValue(HashCode $$0) {
        return unsupported();
    }

    public DataResult<Boolean> getBooleanValue(HashCode $$0) {
        return unsupported();
    }

    public DataResult<String> getStringValue(HashCode $$0) {
        return unsupported();
    }

    boolean isEmpty(HashCode $$0) {
        return $$0.equals(this.empty);
    }

    public DataResult<HashCode> mergeToList(HashCode $$0, HashCode $$1) {
        if (isEmpty($$0)) {
            return DataResult.success(createList(Stream.of($$1)));
        }
        return unsupported();
    }

    public DataResult<HashCode> mergeToList(HashCode $$0, List<HashCode> $$1) {
        if (isEmpty($$0)) {
            return DataResult.success(createList($$1.stream()));
        }
        return unsupported();
    }

    public DataResult<HashCode> mergeToMap(HashCode $$0, HashCode $$1, HashCode $$2) {
        if (isEmpty($$0)) {
            return DataResult.success(createMap(Map.of($$1, $$2)));
        }
        return unsupported();
    }

    public DataResult<HashCode> mergeToMap(HashCode $$0, Map<HashCode, HashCode> $$1) {
        if (isEmpty($$0)) {
            return DataResult.success(createMap($$1));
        }
        return unsupported();
    }

    public DataResult<HashCode> mergeToMap(HashCode $$0, MapLike<HashCode> $$1) {
        if (isEmpty($$0)) {
            return DataResult.success(createMap($$1.entries()));
        }
        return unsupported();
    }

    public DataResult<Stream<Pair<HashCode, HashCode>>> getMapValues(HashCode $$0) {
        return unsupported();
    }

    public DataResult<Consumer<BiConsumer<HashCode, HashCode>>> getMapEntries(HashCode $$0) {
        return unsupported();
    }

    public DataResult<Stream<HashCode>> getStream(HashCode $$0) {
        return unsupported();
    }

    public DataResult<Consumer<Consumer<HashCode>>> getList(HashCode $$0) {
        return unsupported();
    }

    public DataResult<MapLike<HashCode>> getMap(HashCode $$0) {
        return unsupported();
    }

    public DataResult<ByteBuffer> getByteBuffer(HashCode $$0) {
        return unsupported();
    }

    public DataResult<IntStream> getIntStream(HashCode $$0) {
        return unsupported();
    }

    public DataResult<LongStream> getLongStream(HashCode $$0) {
        return unsupported();
    }
}

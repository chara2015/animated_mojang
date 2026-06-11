package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NullOps.class */
public class NullOps implements DynamicOps<Unit> {
    public static final NullOps INSTANCE = new NullOps();
    private static final MapLike<Unit> EMPTY_MAP = new MapLike<Unit>() { // from class: net.minecraft.util.NullOps.1
        public Unit get(Unit $$0) {
            return null;
        }

        /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
        public Unit m2393get(String $$0) {
            return null;
        }

        public Stream<Pair<Unit, Unit>> entries() {
            return Stream.empty();
        }
    };

    /* JADX INFO: renamed from: createList, reason: collision with other method in class */
    public /* synthetic */ Object m2378createList(Stream stream) {
        return createList((Stream<Unit>) stream);
    }

    /* JADX INFO: renamed from: createMap, reason: collision with other method in class */
    public /* synthetic */ Object m2379createMap(Map map) {
        return createMap((Map<Unit, Unit>) map);
    }

    /* JADX INFO: renamed from: createMap, reason: collision with other method in class */
    public /* synthetic */ Object m2380createMap(Stream stream) {
        return createMap((Stream<Pair<Unit, Unit>>) stream);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, MapLike mapLike) {
        return mergeToMap((Unit) obj, (MapLike<Unit>) mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object obj, Map map) {
        return mergeToMap((Unit) obj, (Map<Unit, Unit>) map);
    }

    public /* synthetic */ DataResult mergeToList(Object obj, List list) {
        return mergeToList((Unit) obj, (List<Unit>) list);
    }

    private NullOps() {
    }

    public <U> U convertTo(DynamicOps<U> dynamicOps, Unit unit) {
        return (U) dynamicOps.empty();
    }

    /* JADX INFO: renamed from: empty, reason: merged with bridge method [inline-methods] */
    public Unit m2392empty() {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: emptyMap, reason: merged with bridge method [inline-methods] */
    public Unit m2391emptyMap() {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: emptyList, reason: merged with bridge method [inline-methods] */
    public Unit m2390emptyList() {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createNumeric, reason: merged with bridge method [inline-methods] */
    public Unit m2389createNumeric(Number $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createByte, reason: merged with bridge method [inline-methods] */
    public Unit m2388createByte(byte $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createShort, reason: merged with bridge method [inline-methods] */
    public Unit m2387createShort(short $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createInt, reason: merged with bridge method [inline-methods] */
    public Unit m2386createInt(int $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createLong, reason: merged with bridge method [inline-methods] */
    public Unit m2385createLong(long $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createFloat, reason: merged with bridge method [inline-methods] */
    public Unit m2384createFloat(float $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createDouble, reason: merged with bridge method [inline-methods] */
    public Unit m2383createDouble(double $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createBoolean, reason: merged with bridge method [inline-methods] */
    public Unit m2382createBoolean(boolean $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createString, reason: merged with bridge method [inline-methods] */
    public Unit m2381createString(String $$0) {
        return Unit.INSTANCE;
    }

    public DataResult<Number> getNumberValue(Unit $$0) {
        return DataResult.success(0);
    }

    public DataResult<Boolean> getBooleanValue(Unit $$0) {
        return DataResult.success(false);
    }

    public DataResult<String> getStringValue(Unit $$0) {
        return DataResult.success("");
    }

    public DataResult<Unit> mergeToList(Unit $$0, Unit $$1) {
        return DataResult.success(Unit.INSTANCE);
    }

    public DataResult<Unit> mergeToList(Unit $$0, List<Unit> $$1) {
        return DataResult.success(Unit.INSTANCE);
    }

    public DataResult<Unit> mergeToMap(Unit $$0, Unit $$1, Unit $$2) {
        return DataResult.success(Unit.INSTANCE);
    }

    public DataResult<Unit> mergeToMap(Unit $$0, Map<Unit, Unit> $$1) {
        return DataResult.success(Unit.INSTANCE);
    }

    public DataResult<Unit> mergeToMap(Unit $$0, MapLike<Unit> $$1) {
        return DataResult.success(Unit.INSTANCE);
    }

    public DataResult<Stream<Pair<Unit, Unit>>> getMapValues(Unit $$0) {
        return DataResult.success(Stream.empty());
    }

    public DataResult<Consumer<BiConsumer<Unit, Unit>>> getMapEntries(Unit $$0) {
        return DataResult.success($$02 -> {
        });
    }

    public DataResult<MapLike<Unit>> getMap(Unit $$0) {
        return DataResult.success(EMPTY_MAP);
    }

    public DataResult<Stream<Unit>> getStream(Unit $$0) {
        return DataResult.success(Stream.empty());
    }

    public DataResult<Consumer<Consumer<Unit>>> getList(Unit $$0) {
        return DataResult.success($$02 -> {
        });
    }

    public DataResult<ByteBuffer> getByteBuffer(Unit $$0) {
        return DataResult.success(ByteBuffer.wrap(new byte[0]));
    }

    public DataResult<IntStream> getIntStream(Unit $$0) {
        return DataResult.success(IntStream.empty());
    }

    public DataResult<LongStream> getLongStream(Unit $$0) {
        return DataResult.success(LongStream.empty());
    }

    public Unit createMap(Stream<Pair<Unit, Unit>> $$0) {
        return Unit.INSTANCE;
    }

    public Unit createMap(Map<Unit, Unit> $$0) {
        return Unit.INSTANCE;
    }

    public Unit createList(Stream<Unit> $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createByteList, reason: merged with bridge method [inline-methods] */
    public Unit m2377createByteList(ByteBuffer $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createIntList, reason: merged with bridge method [inline-methods] */
    public Unit m2376createIntList(IntStream $$0) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: createLongList, reason: merged with bridge method [inline-methods] */
    public Unit m2375createLongList(LongStream $$0) {
        return Unit.INSTANCE;
    }

    public Unit remove(Unit $$0, String $$1) {
        return $$0;
    }

    public RecordBuilder<Unit> mapBuilder() {
        return new NullMapBuilder(this);
    }

    public ListBuilder<Unit> listBuilder() {
        return new NullListBuilder(this);
    }

    public String toString() {
        return "Null";
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NullOps$NullMapBuilder.class */
    static final class NullMapBuilder extends RecordBuilder.AbstractUniversalBuilder<Unit, Unit> {
        public NullMapBuilder(DynamicOps<Unit> $$0) {
            super($$0);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: initBuilder, reason: merged with bridge method [inline-methods] */
        public Unit m2394initBuilder() {
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Unit append(Unit $$0, Unit $$1, Unit $$2) {
            return $$2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public DataResult<Unit> build(Unit $$0, Unit $$1) {
            return DataResult.success($$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/NullOps$NullListBuilder.class */
    static final class NullListBuilder extends AbstractListBuilder<Unit, Unit> {
        public NullListBuilder(DynamicOps<Unit> $$0) {
            super($$0);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.util.AbstractListBuilder
        public Unit initBuilder() {
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.minecraft.util.AbstractListBuilder
        public Unit append(Unit $$0, Unit $$1) {
            return $$0;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.minecraft.util.AbstractListBuilder
        public DataResult<Unit> build(Unit $$0, Unit $$1) {
            return DataResult.success($$0);
        }
    }
}

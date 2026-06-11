package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.nbt.StreamTagVisitor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/Tag.class */
public interface Tag {
    public static final int OBJECT_HEADER = 8;
    public static final int ARRAY_HEADER = 12;
    public static final int OBJECT_REFERENCE = 4;
    public static final int STRING_SIZE = 28;
    public static final byte TAG_END = 0;
    public static final byte TAG_BYTE = 1;
    public static final byte TAG_SHORT = 2;
    public static final byte TAG_INT = 3;
    public static final byte TAG_LONG = 4;
    public static final byte TAG_FLOAT = 5;
    public static final byte TAG_DOUBLE = 6;
    public static final byte TAG_BYTE_ARRAY = 7;
    public static final byte TAG_STRING = 8;
    public static final byte TAG_LIST = 9;
    public static final byte TAG_COMPOUND = 10;
    public static final byte TAG_INT_ARRAY = 11;
    public static final byte TAG_LONG_ARRAY = 12;
    public static final int MAX_DEPTH = 512;

    void write(DataOutput dataOutput) throws IOException;

    String toString();

    byte getId();

    TagType<?> getType();

    Tag copy();

    int sizeInBytes();

    void accept(TagVisitor tagVisitor);

    StreamTagVisitor.ValueResult accept(StreamTagVisitor streamTagVisitor);

    default void acceptAsRoot(StreamTagVisitor $$0) {
        StreamTagVisitor.ValueResult $$1 = $$0.visitRootEntry(getType());
        if ($$1 == StreamTagVisitor.ValueResult.CONTINUE) {
            accept($$0);
        }
    }

    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<Number> asNumber() {
        return Optional.empty();
    }

    default Optional<Byte> asByte() {
        return asNumber().map((v0) -> {
            return v0.byteValue();
        });
    }

    default Optional<Short> asShort() {
        return asNumber().map((v0) -> {
            return v0.shortValue();
        });
    }

    default Optional<Integer> asInt() {
        return asNumber().map((v0) -> {
            return v0.intValue();
        });
    }

    default Optional<Long> asLong() {
        return asNumber().map((v0) -> {
            return v0.longValue();
        });
    }

    default Optional<Float> asFloat() {
        return asNumber().map((v0) -> {
            return v0.floatValue();
        });
    }

    default Optional<Double> asDouble() {
        return asNumber().map((v0) -> {
            return v0.doubleValue();
        });
    }

    default Optional<Boolean> asBoolean() {
        return asByte().map($$0 -> {
            return Boolean.valueOf($$0.byteValue() != 0);
        });
    }

    default Optional<byte[]> asByteArray() {
        return Optional.empty();
    }

    default Optional<int[]> asIntArray() {
        return Optional.empty();
    }

    default Optional<long[]> asLongArray() {
        return Optional.empty();
    }

    default Optional<CompoundTag> asCompound() {
        return Optional.empty();
    }

    default Optional<ListTag> asList() {
        return Optional.empty();
    }
}

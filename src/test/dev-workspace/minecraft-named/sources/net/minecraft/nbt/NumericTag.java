package net.minecraft.nbt;

import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NumericTag.class */
public interface NumericTag extends PrimitiveTag {
    byte byteValue();

    short shortValue();

    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();

    Number box();

    @Override // net.minecraft.nbt.Tag
    default Optional<Number> asNumber() {
        return Optional.of(box());
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Byte> asByte() {
        return Optional.of(Byte.valueOf(byteValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Short> asShort() {
        return Optional.of(Short.valueOf(shortValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Integer> asInt() {
        return Optional.of(Integer.valueOf(intValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Long> asLong() {
        return Optional.of(Long.valueOf(longValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Float> asFloat() {
        return Optional.of(Float.valueOf(floatValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Double> asDouble() {
        return Optional.of(Double.valueOf(doubleValue()));
    }

    @Override // net.minecraft.nbt.Tag
    default Optional<Boolean> asBoolean() {
        return Optional.of(Boolean.valueOf(byteValue() != 0));
    }
}

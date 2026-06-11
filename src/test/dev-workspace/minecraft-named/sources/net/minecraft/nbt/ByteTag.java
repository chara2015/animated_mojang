package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/ByteTag.class */
public final class ByteTag extends Record implements NumericTag {
    private final byte value;
    private static final int SELF_SIZE_IN_BYTES = 9;
    public static final TagType<ByteTag> TYPE = new TagType.StaticSize<ByteTag>() { // from class: net.minecraft.nbt.ByteTag.1
        @Override // net.minecraft.nbt.TagType
        public ByteTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return ByteTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static byte readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(9L);
            return $$0.readByte();
        }

        @Override // net.minecraft.nbt.TagType.StaticSize
        public int size() {
            return 1;
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "BYTE";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_Byte";
        }
    };
    public static final ByteTag ZERO = valueOf((byte) 0);
    public static final ByteTag ONE = valueOf((byte) 1);

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ByteTag.class), ByteTag.class, "value", "FIELD:Lnet/minecraft/nbt/ByteTag;->value:B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ByteTag.class, Object.class), ByteTag.class, "value", "FIELD:Lnet/minecraft/nbt/ByteTag;->value:B").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public byte value() {
        return this.value;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/ByteTag$Cache.class */
    static class Cache {
        static final ByteTag[] cache = new ByteTag[256];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < cache.length; $$0++) {
                cache[$$0] = new ByteTag((byte) ($$0 - 128));
            }
        }
    }

    @Deprecated(forRemoval = true)
    public ByteTag(byte $$0) {
        this.value = $$0;
    }

    public static ByteTag valueOf(byte $$0) {
        return Cache.cache[128 + $$0];
    }

    public static ByteTag valueOf(boolean $$0) {
        return $$0 ? ONE : ZERO;
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeByte(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 9;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 1;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<ByteTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public ByteTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitByte(this);
    }

    @Override // net.minecraft.nbt.NumericTag
    public long longValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public int intValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public short shortValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public byte byteValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public double doubleValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public float floatValue() {
        return this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public Number box() {
        return Byte.valueOf(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitByte(this);
        return $$0.build();
    }
}

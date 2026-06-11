package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/IntTag.class */
public final class IntTag extends Record implements NumericTag {
    private final int value;
    private static final int SELF_SIZE_IN_BYTES = 12;
    public static final TagType<IntTag> TYPE = new TagType.StaticSize<IntTag>() { // from class: net.minecraft.nbt.IntTag.1
        @Override // net.minecraft.nbt.TagType
        public IntTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return IntTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static int readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(12L);
            return $$0.readInt();
        }

        @Override // net.minecraft.nbt.TagType.StaticSize
        public int size() {
            return 4;
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "INT";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_Int";
        }
    };

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntTag.class), IntTag.class, "value", "FIELD:Lnet/minecraft/nbt/IntTag;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntTag.class, Object.class), IntTag.class, "value", "FIELD:Lnet/minecraft/nbt/IntTag;->value:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int value() {
        return this.value;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/IntTag$Cache.class */
    static class Cache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final IntTag[] cache = new IntTag[1153];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < cache.length; $$0++) {
                cache[$$0] = new IntTag(LOW + $$0);
            }
        }
    }

    @Deprecated(forRemoval = true)
    public IntTag(int $$0) {
        this.value = $$0;
    }

    public static IntTag valueOf(int $$0) {
        if ($$0 >= -128 && $$0 <= 1024) {
            return Cache.cache[$$0 - (-128)];
        }
        return new IntTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeInt(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 12;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 3;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<IntTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public IntTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitInt(this);
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
        return (short) (this.value & 65535);
    }

    @Override // net.minecraft.nbt.NumericTag
    public byte byteValue() {
        return (byte) (this.value & 255);
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
        return Integer.valueOf(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitInt(this);
        return $$0.build();
    }
}

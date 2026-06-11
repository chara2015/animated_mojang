package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/ShortTag.class */
public final class ShortTag extends Record implements NumericTag {
    private final short value;
    private static final int SELF_SIZE_IN_BYTES = 10;
    public static final TagType<ShortTag> TYPE = new TagType.StaticSize<ShortTag>() { // from class: net.minecraft.nbt.ShortTag.1
        @Override // net.minecraft.nbt.TagType
        public ShortTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return ShortTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static short readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(10L);
            return $$0.readShort();
        }

        @Override // net.minecraft.nbt.TagType.StaticSize
        public int size() {
            return 2;
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "SHORT";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_Short";
        }
    };

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShortTag.class), ShortTag.class, "value", "FIELD:Lnet/minecraft/nbt/ShortTag;->value:S").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShortTag.class, Object.class), ShortTag.class, "value", "FIELD:Lnet/minecraft/nbt/ShortTag;->value:S").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public short value() {
        return this.value;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/ShortTag$Cache.class */
    static class Cache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final ShortTag[] cache = new ShortTag[1153];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < cache.length; $$0++) {
                cache[$$0] = new ShortTag((short) (LOW + $$0));
            }
        }
    }

    @Deprecated(forRemoval = true)
    public ShortTag(short $$0) {
        this.value = $$0;
    }

    public static ShortTag valueOf(short $$0) {
        if ($$0 >= -128 && $$0 <= 1024) {
            return Cache.cache[$$0 - (-128)];
        }
        return new ShortTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeShort(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 10;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 2;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<ShortTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public ShortTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitShort(this);
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
        return Short.valueOf(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitShort(this);
        return $$0.build();
    }
}

package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/FloatTag.class */
public final class FloatTag extends Record implements NumericTag {
    private final float value;
    private static final int SELF_SIZE_IN_BYTES = 12;
    public static final FloatTag ZERO = new FloatTag(0.0f);
    public static final TagType<FloatTag> TYPE = new TagType.StaticSize<FloatTag>() { // from class: net.minecraft.nbt.FloatTag.1
        @Override // net.minecraft.nbt.TagType
        public FloatTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return FloatTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static float readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(12L);
            return $$0.readFloat();
        }

        @Override // net.minecraft.nbt.TagType.StaticSize
        public int size() {
            return 4;
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "FLOAT";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_Float";
        }
    };

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FloatTag.class), FloatTag.class, "value", "FIELD:Lnet/minecraft/nbt/FloatTag;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FloatTag.class, Object.class), FloatTag.class, "value", "FIELD:Lnet/minecraft/nbt/FloatTag;->value:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float value() {
        return this.value;
    }

    @Deprecated(forRemoval = true)
    public FloatTag(float $$0) {
        this.value = $$0;
    }

    public static FloatTag valueOf(float $$0) {
        if ($$0 == 0.0f) {
            return ZERO;
        }
        return new FloatTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeFloat(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 12;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 5;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<FloatTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public FloatTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitFloat(this);
    }

    @Override // net.minecraft.nbt.NumericTag
    public long longValue() {
        return (long) this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public int intValue() {
        return Mth.floor(this.value);
    }

    @Override // net.minecraft.nbt.NumericTag
    public short shortValue() {
        return (short) (Mth.floor(this.value) & 65535);
    }

    @Override // net.minecraft.nbt.NumericTag
    public byte byteValue() {
        return (byte) (Mth.floor(this.value) & 255);
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
        return Float.valueOf(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitFloat(this);
        return $$0.build();
    }
}

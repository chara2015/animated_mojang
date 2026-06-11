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
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/DoubleTag.class */
public final class DoubleTag extends Record implements NumericTag {
    private final double value;
    private static final int SELF_SIZE_IN_BYTES = 16;
    public static final DoubleTag ZERO = new DoubleTag(Density.SURFACE);
    public static final TagType<DoubleTag> TYPE = new TagType.StaticSize<DoubleTag>() { // from class: net.minecraft.nbt.DoubleTag.1
        @Override // net.minecraft.nbt.TagType
        public DoubleTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return DoubleTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static double readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(16L);
            return $$0.readDouble();
        }

        @Override // net.minecraft.nbt.TagType.StaticSize
        public int size() {
            return 8;
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "DOUBLE";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_Double";
        }
    };

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DoubleTag.class), DoubleTag.class, "value", "FIELD:Lnet/minecraft/nbt/DoubleTag;->value:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DoubleTag.class, Object.class), DoubleTag.class, "value", "FIELD:Lnet/minecraft/nbt/DoubleTag;->value:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public double value() {
        return this.value;
    }

    @Deprecated(forRemoval = true)
    public DoubleTag(double $$0) {
        this.value = $$0;
    }

    public static DoubleTag valueOf(double $$0) {
        if ($$0 == Density.SURFACE) {
            return ZERO;
        }
        return new DoubleTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeDouble(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 16;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 6;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<DoubleTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public DoubleTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitDouble(this);
    }

    @Override // net.minecraft.nbt.NumericTag
    public long longValue() {
        return (long) Math.floor(this.value);
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
        return (float) this.value;
    }

    @Override // net.minecraft.nbt.NumericTag
    public Number box() {
        return Double.valueOf(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitDouble(this);
        return $$0.build();
    }
}

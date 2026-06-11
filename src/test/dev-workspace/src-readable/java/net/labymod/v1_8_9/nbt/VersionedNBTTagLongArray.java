package net.labymod.v1_8_9.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.labymod.api.nbt.NBTTagType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/nbt/VersionedNBTTagLongArray.class */
public class VersionedNBTTagLongArray extends eb {
    private long[] longArray;

    public VersionedNBTTagLongArray() {
    }

    public VersionedNBTTagLongArray(long[] longArray) {
        this.longArray = longArray;
    }

    protected void a(DataOutput output) throws IOException {
        output.writeInt(this.longArray.length);
        for (long l : this.longArray) {
            output.writeLong(l);
        }
    }

    protected void a(DataInput input, int depth, dw sizeTracker) throws IOException {
        sizeTracker.a(192L);
        int length = input.readInt();
        sizeTracker.a(64 * ((long) length));
        this.longArray = new long[length];
        for (int i = 0; i < length; i++) {
            this.longArray[i] = input.readLong();
        }
    }

    public byte a() {
        return NBTTagType.LONG_ARRAY.getId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (long l : this.longArray) {
            builder.append(l).append(",");
        }
        return String.valueOf(builder) + "]";
    }

    public eb b() {
        long[] arrayCopy = new long[this.longArray.length];
        System.arraycopy(this.longArray, 0, arrayCopy, 0, this.longArray.length);
        return new VersionedNBTTagLongArray(arrayCopy);
    }

    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(this.longArray, ((VersionedNBTTagLongArray) other).longArray);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.longArray);
    }

    public long[] getLongArray() {
        return this.longArray;
    }
}

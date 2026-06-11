package net.labymod.api.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.function.IntFunction;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Buffers.class */
public final class Buffers {
    private Buffers() {
    }

    @NotNull
    public static ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    @NotNull
    public static ShortBuffer createShortBuffer(int size) {
        return createByteBuffer(size << 1).asShortBuffer();
    }

    @NotNull
    public static CharBuffer createCharBuffer(int size) {
        return createByteBuffer(size << 1).asCharBuffer();
    }

    @NotNull
    public static IntBuffer createIntBuffer(int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    @NotNull
    public static LongBuffer createLongBuffer(int size) {
        return createByteBuffer(size << 3).asLongBuffer();
    }

    @NotNull
    public static FloatBuffer createFloatBuffer(int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    @NotNull
    public static DoubleBuffer createDoubleBuffer(int size) {
        return createByteBuffer(size << 3).asDoubleBuffer();
    }

    public static ByteBuffer cloneBuffer(ByteBuffer buffer) {
        return cloneBuffer(buffer, Buffers::createByteBuffer);
    }

    public static ByteBuffer cloneBuffer(ByteBuffer buffer, IntFunction<ByteBuffer> bufferFactory) {
        ByteBuffer clone = bufferFactory.apply(buffer.capacity());
        int position = buffer.position();
        clone.put(buffer);
        buffer.position(position);
        clone.flip();
        return clone;
    }
}

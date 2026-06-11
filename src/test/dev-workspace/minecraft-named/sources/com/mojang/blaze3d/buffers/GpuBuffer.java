package com.mojang.blaze3d.buffers;

import com.mojang.blaze3d.DontObfuscate;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/buffers/GpuBuffer.class */
@DontObfuscate
public abstract class GpuBuffer implements AutoCloseable {
    public static final int USAGE_MAP_READ = 1;
    public static final int USAGE_MAP_WRITE = 2;
    public static final int USAGE_HINT_CLIENT_STORAGE = 4;
    public static final int USAGE_COPY_DST = 8;
    public static final int USAGE_COPY_SRC = 16;
    public static final int USAGE_VERTEX = 32;
    public static final int USAGE_INDEX = 64;
    public static final int USAGE_UNIFORM = 128;
    public static final int USAGE_UNIFORM_TEXEL_BUFFER = 256;

    @Usage
    private final int usage;
    private final long size;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/buffers/GpuBuffer$MappedView.class */
    @DontObfuscate
    public interface MappedView extends AutoCloseable {
        ByteBuffer data();

        @Override // java.lang.AutoCloseable
        void close();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/buffers/GpuBuffer$Usage.class */
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Usage {
    }

    public abstract boolean isClosed();

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public GpuBuffer(@Usage int $$0, long $$1) {
        this.size = $$1;
        this.usage = $$0;
    }

    public long size() {
        return this.size;
    }

    @Usage
    public int usage() {
        return this.usage;
    }

    public GpuBufferSlice slice(long $$0, long $$1) {
        if ($$0 < 0 || $$1 < 0 || $$0 + $$1 > this.size) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Offset of " + $$0 + " and length " + illegalArgumentException + " would put new slice outside buffer's range (of 0," + $$1 + ")");
            throw illegalArgumentException;
        }
        return new GpuBufferSlice(this, $$0, $$1);
    }

    public GpuBufferSlice slice() {
        return new GpuBufferSlice(this, 0L, this.size);
    }
}

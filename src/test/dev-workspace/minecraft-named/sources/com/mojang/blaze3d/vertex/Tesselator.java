package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.VertexFormat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/Tesselator.class */
public class Tesselator {
    private static final int MAX_BYTES = 786432;
    private final ByteBufferBuilder buffer;
    private static Tesselator instance;

    public static void init() {
        if (instance != null) {
            throw new IllegalStateException("Tesselator has already been initialized");
        }
        instance = new Tesselator();
    }

    public static Tesselator getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Tesselator has not been initialized");
        }
        return instance;
    }

    public Tesselator(int $$0) {
        this.buffer = new ByteBufferBuilder($$0);
    }

    public Tesselator() {
        this(786432);
    }

    public BufferBuilder begin(VertexFormat.Mode $$0, VertexFormat $$1) {
        return new BufferBuilder(this.buffer, $$0, $$1);
    }

    public void clear() {
        this.buffer.clear();
    }
}

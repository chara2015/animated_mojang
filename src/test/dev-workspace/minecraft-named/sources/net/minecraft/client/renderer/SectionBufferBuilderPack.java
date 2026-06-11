package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SectionBufferBuilderPack.class */
public class SectionBufferBuilderPack implements AutoCloseable {
    public static final int TOTAL_BUFFERS_SIZE = Arrays.stream(ChunkSectionLayer.values()).mapToInt((v0) -> {
        return v0.bufferSize();
    }).sum();
    private final Map<ChunkSectionLayer, ByteBufferBuilder> buffers = Util.makeEnumMap(ChunkSectionLayer.class, $$0 -> {
        return new ByteBufferBuilder($$0.bufferSize());
    });

    public ByteBufferBuilder buffer(ChunkSectionLayer $$0) {
        return this.buffers.get($$0);
    }

    public void clearAll() {
        this.buffers.values().forEach((v0) -> {
            v0.clear();
        });
    }

    public void discardAll() {
        this.buffers.values().forEach((v0) -> {
            v0.discard();
        });
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffers.values().forEach((v0) -> {
            v0.close();
        });
    }
}

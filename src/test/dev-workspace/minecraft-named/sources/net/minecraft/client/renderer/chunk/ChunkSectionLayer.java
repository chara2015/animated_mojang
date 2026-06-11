package net.minecraft.client.renderer.chunk;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.Locale;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/ChunkSectionLayer.class */
public enum ChunkSectionLayer {
    SOLID(RenderPipelines.SOLID_TERRAIN, RenderType.BIG_BUFFER_SIZE, false),
    CUTOUT(RenderPipelines.CUTOUT_TERRAIN, RenderType.BIG_BUFFER_SIZE, false),
    TRANSLUCENT(RenderPipelines.TRANSLUCENT_TERRAIN, RenderType.SMALL_BUFFER_SIZE, true),
    TRIPWIRE(RenderPipelines.TRIPWIRE_TERRAIN, RenderType.TRANSIENT_BUFFER_SIZE, true);

    private final RenderPipeline pipeline;
    private final int bufferSize;
    private final boolean sortOnUpload;
    private final String label = toString().toLowerCase(Locale.ROOT);

    ChunkSectionLayer(RenderPipeline $$0, int $$1, boolean $$2) {
        this.pipeline = $$0;
        this.bufferSize = $$1;
        this.sortOnUpload = $$2;
    }

    public RenderPipeline pipeline() {
        return this.pipeline;
    }

    public int bufferSize() {
        return this.bufferSize;
    }

    public String label() {
        return this.label;
    }

    public boolean sortOnUpload() {
        return this.sortOnUpload;
    }
}

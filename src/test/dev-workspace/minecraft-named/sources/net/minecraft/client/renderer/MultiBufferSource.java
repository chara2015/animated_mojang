package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;
import java.util.HashMap;
import java.util.Map;
import java.util.SequencedMap;
import net.minecraft.client.renderer.rendertype.RenderType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/MultiBufferSource.class */
public interface MultiBufferSource {
    VertexConsumer getBuffer(RenderType renderType);

    static BufferSource immediate(ByteBufferBuilder $$0) {
        return immediateWithBuffers(Object2ObjectSortedMaps.emptyMap(), $$0);
    }

    static BufferSource immediateWithBuffers(SequencedMap<RenderType, ByteBufferBuilder> $$0, ByteBufferBuilder $$1) {
        return new BufferSource($$1, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/MultiBufferSource$BufferSource.class */
    public static class BufferSource implements MultiBufferSource {
        protected final ByteBufferBuilder sharedBuffer;
        protected final SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers;
        protected final Map<RenderType, BufferBuilder> startedBuilders = new HashMap();
        protected RenderType lastSharedType;

        protected BufferSource(ByteBufferBuilder $$0, SequencedMap<RenderType, ByteBufferBuilder> $$1) {
            this.sharedBuffer = $$0;
            this.fixedBuffers = $$1;
        }

        @Override // net.minecraft.client.renderer.MultiBufferSource
        public VertexConsumer getBuffer(RenderType $$0) {
            BufferBuilder $$1;
            BufferBuilder $$12 = this.startedBuilders.get($$0);
            if ($$12 != null && !$$0.canConsolidateConsecutiveGeometry()) {
                endBatch($$0, $$12);
                $$12 = null;
            }
            if ($$12 != null) {
                return $$12;
            }
            ByteBufferBuilder $$2 = (ByteBufferBuilder) this.fixedBuffers.get($$0);
            if ($$2 != null) {
                $$1 = new BufferBuilder($$2, $$0.mode(), $$0.format());
            } else {
                if (this.lastSharedType != null) {
                    endBatch(this.lastSharedType);
                }
                $$1 = new BufferBuilder(this.sharedBuffer, $$0.mode(), $$0.format());
                this.lastSharedType = $$0;
            }
            this.startedBuilders.put($$0, $$1);
            return $$1;
        }

        public void endLastBatch() {
            if (this.lastSharedType != null) {
                endBatch(this.lastSharedType);
                this.lastSharedType = null;
            }
        }

        public void endBatch() {
            endLastBatch();
            for (RenderType $$0 : this.fixedBuffers.keySet()) {
                endBatch($$0);
            }
        }

        public void endBatch(RenderType $$0) {
            BufferBuilder $$1 = this.startedBuilders.remove($$0);
            if ($$1 != null) {
                endBatch($$0, $$1);
            }
        }

        private void endBatch(RenderType $$0, BufferBuilder $$1) {
            MeshData $$2 = $$1.build();
            if ($$2 != null) {
                if ($$0.sortOnUpload()) {
                    ByteBufferBuilder $$3 = (ByteBufferBuilder) this.fixedBuffers.getOrDefault($$0, this.sharedBuffer);
                    $$2.sortQuads($$3, RenderSystem.getProjectionType().vertexSorting());
                }
                $$0.draw($$2);
            }
            if ($$0.equals(this.lastSharedType)) {
                this.lastSharedType = null;
            }
        }
    }
}

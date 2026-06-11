package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntIterator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlTextureView.class */
public class GlTextureView extends GpuTextureView {
    private static final int EMPTY = -1;
    private boolean closed;
    private int firstFboId;
    private int firstFboDepthId;
    private Int2IntMap fboCache;

    protected GlTextureView(GlTexture $$0, int $$1, int $$2) {
        super($$0, $$1, $$2);
        this.firstFboId = -1;
        this.firstFboDepthId = -1;
        $$0.addViews();
    }

    @Override // com.mojang.blaze3d.textures.GpuTextureView
    public boolean isClosed() {
        return this.closed;
    }

    @Override // com.mojang.blaze3d.textures.GpuTextureView, java.lang.AutoCloseable
    public void close() {
        if (!this.closed) {
            this.closed = true;
            texture().removeViews();
            if (this.firstFboId != -1) {
                GlStateManager._glDeleteFramebuffers(this.firstFboId);
            }
            if (this.fboCache != null) {
                IntIterator it = this.fboCache.values().iterator();
                while (it.hasNext()) {
                    int $$0 = ((Integer) it.next()).intValue();
                    GlStateManager._glDeleteFramebuffers($$0);
                }
            }
        }
    }

    public int getFbo(DirectStateAccess $$0, GpuTexture $$1) {
        int $$2 = $$1 == null ? 0 : ((GlTexture) $$1).id;
        if (this.firstFboDepthId == $$2) {
            return this.firstFboId;
        }
        if (this.firstFboId == -1) {
            this.firstFboId = createFbo($$0, $$2);
            this.firstFboDepthId = $$2;
            return this.firstFboId;
        }
        if (this.fboCache == null) {
            this.fboCache = new Int2IntArrayMap();
        }
        return this.fboCache.computeIfAbsent($$2, $$12 -> {
            return createFbo($$0, $$12);
        });
    }

    private int createFbo(DirectStateAccess $$0, int $$1) {
        int $$2 = $$0.createFrameBufferObject();
        $$0.bindFrameBufferTextures($$2, texture().id, $$1, baseMipLevel(), 0);
        return $$2;
    }

    @Override // com.mojang.blaze3d.textures.GpuTextureView
    public GlTexture texture() {
        return (GlTexture) super.texture();
    }
}

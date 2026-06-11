package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/TextureTarget.class */
public class TextureTarget extends RenderTarget {
    public TextureTarget(String $$0, int $$1, int $$2, boolean $$3) {
        super($$0, $$3);
        RenderSystem.assertOnRenderThread();
        resize($$1, $$2);
    }
}

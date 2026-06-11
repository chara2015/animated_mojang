package net.labymod.core.client.gfx.pipeline.renderer.shadow;

import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/shadow/DefaultShadowRenderPassContext.class */
public class DefaultShadowRenderPassContext implements ShadowRenderPassContext {
    @Override // net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext
    public boolean isShadowRenderPass() {
        return false;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext
    @Nullable
    public FloatMatrix4 getShadowModelViewMatrix() {
        return null;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext
    @Nullable
    public FloatMatrix4 getShadowModelViewInverseMatrix() {
        return null;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext
    @Nullable
    public FloatMatrix4 getShadowProjectionMatrix() {
        return null;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext
    @Nullable
    public FloatMatrix4 getShadowProjectionInverseMatrix() {
        return null;
    }
}

package net.labymod.api.client.gfx.pipeline.renderer.shader;

import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/shader/ShaderPipeline.class */
@Referenceable
public interface ShaderPipeline {
    boolean hasActiveShaderPack();

    ShadowRenderPassContext shadowRenderPassContext();
}

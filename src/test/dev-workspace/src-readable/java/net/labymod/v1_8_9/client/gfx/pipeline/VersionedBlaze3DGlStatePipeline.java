package net.labymod.v1_8_9.client.gfx.pipeline;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gfx/pipeline/VersionedBlaze3DGlStatePipeline.class */
@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline {
    @Inject
    public VersionedBlaze3DGlStatePipeline() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void bindTexture(DeviceTextureView textureView) {
        ShaderTextures.setShaderTexture(bfl.o, textureView);
        bfl.i(textureView.texture().getId());
    }
}

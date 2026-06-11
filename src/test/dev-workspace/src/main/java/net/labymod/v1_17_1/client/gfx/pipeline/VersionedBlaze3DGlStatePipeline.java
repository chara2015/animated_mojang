package net.labymod.v1_17_1.client.gfx.pipeline;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.function.Functional;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gfx/pipeline/VersionedBlaze3DGlStatePipeline.class */
@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline {
    private static final k DIFFUSE_LIGHT_0 = (k) Functional.of(new k(0.0f, 0.0f, 0.2f), (v0) -> {
        v0.d();
    });
    private static final k DIFFUSE_LIGHT_1 = (k) Functional.of(new k(0.2f, 0.0f, 0.0f), (v0) -> {
        v0.d();
    });

    @Inject
    public VersionedBlaze3DGlStatePipeline() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public Stack getModelViewStack() {
        return RenderSystem.getModelViewStack().stack();
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void applyModelViewMatrix() {
        RenderSystem.applyModelViewMatrix();
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void bindTexture(DeviceTextureView textureView) {
        int slot = GlStateManager._getActiveTexture() - GlConst.GL_TEXTURE0;
        ShaderTextures.setShaderTexture(slot, textureView);
        GlResource deviceTexture = (GlResource) CastUtil.requireInstanceOf(textureView.texture(), GlResource.class);
        int id = deviceTexture.getId();
        GlStateManager._bindTexture(id);
        RenderSystem.setShaderTexture(slot, id);
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void setupFlatLighting(Runnable runnable) {
        k diffuseLight0 = RenderSystem.shaderLightDirections[0];
        k diffuseLight1 = RenderSystem.shaderLightDirections[1];
        try {
            RenderSystem.setShaderLights(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
            runnable.run();
            RenderSystem.setShaderLights(diffuseLight0, diffuseLight1);
        } catch (Throwable th) {
            RenderSystem.setShaderLights(diffuseLight0, diffuseLight1);
            throw th;
        }
    }
}

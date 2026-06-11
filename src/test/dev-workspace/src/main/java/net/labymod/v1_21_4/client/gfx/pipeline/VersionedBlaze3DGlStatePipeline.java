package net.labymod.v1_21_4.client.gfx.pipeline;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_4.client.render.matrix.JomlStackProvider;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/gfx/pipeline/VersionedBlaze3DGlStatePipeline.class */
@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline {
    private static final Vector3f DIFFUSE_LIGHT_0 = new Vector3f(0.0f, 0.0f, 0.2f).normalize();
    private static final Vector3f DIFFUSE_LIGHT_1 = new Vector3f(0.2f, 0.0f, 0.0f).normalize();
    private static final Stack MODE_VIEW_STACK = Stack.create((StackProvider) new JomlStackProvider(RenderSystem.getModelViewStack()));

    @Inject
    public VersionedBlaze3DGlStatePipeline() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public Stack getModelViewStack() {
        return MODE_VIEW_STACK;
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void applyModelViewMatrix() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void bindTexture(DeviceTextureView handle) {
        int slot = GlStateManager._getActiveTexture() - GlConst.GL_TEXTURE0;
        ShaderTextures.setShaderTexture(slot, handle);
        GlResource deviceTexture = (GlResource) CastUtil.requireInstanceOf(handle.texture(), GlResource.class);
        int id = deviceTexture.getId();
        GlStateManager._bindTexture(id);
        RenderSystem.setShaderTexture(slot, id);
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void setupFlatLighting(Runnable runnable) {
        Vector3f diffuseLight0 = RenderSystem.shaderLightDirections[0];
        Vector3f diffuseLight1 = RenderSystem.shaderLightDirections[1];
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

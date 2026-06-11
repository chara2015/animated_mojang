package net.labymod.v1_21_8.client.gfx.pipeline;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_8.client.render.matrix.JomlStackProvider;
import net.labymod.v1_21_8.laby3d.pipeline.opengl.Laby3DGlTexture;
import net.labymod.v1_21_8.laby3d.pipeline.opengl.Laby3DGlTextureView;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/VersionedBlaze3DGlStatePipeline.class */
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
    public void bindTexture(DeviceTextureView textureView) {
        int slot = GlStateManager._getActiveTexture() - GlConst.GL_TEXTURE0;
        ShaderTextures.setShaderTexture(slot, textureView);
        GlResource deviceTexture = (GlResource) CastUtil.requireInstanceOf(textureView.texture(), GlResource.class);
        GlStateManager._bindTexture(deviceTexture.getId());
        ForeignDeviceTexture foreignDeviceTexture = Laby.references().laby3D().foreignDeviceTextureRegistry().get(Integer.valueOf(deviceTexture.getId()));
        Laby3DGlTexture texture = new Laby3DGlTexture(foreignDeviceTexture);
        Laby3DGlTextureView texView = new Laby3DGlTextureView(texture);
        RenderSystem.setShaderTexture(slot, texView);
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void setupFlatLighting(Runnable runnable) {
    }
}

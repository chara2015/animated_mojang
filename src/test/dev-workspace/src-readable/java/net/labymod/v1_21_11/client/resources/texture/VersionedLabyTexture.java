package net.labymod.v1_21_11.client.resources.texture;

import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.textures.SamplerDescription;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.laby3d.pipeline.opengl.Laby3DGlTexture;
import net.labymod.v1_21_11.laby3d.pipeline.opengl.Laby3DGlTextureView;
import net.minecraft.client.renderer.texture.AbstractTexture;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends AbstractTexture implements Texture {
    private final LabyTexture delegate;
    private Laby3DGlTexture gameGlTexture;
    private Laby3DGlTextureView gameGlTextureView;
    private boolean samplerCreated;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void close() {
        this.delegate.release();
    }

    public GpuTexture getTexture() {
        return (GpuTexture) CastUtil.requireInstanceOf(getGameGlTexture(), GpuTexture.class);
    }

    public GpuTextureView getTextureView() {
        return (GpuTextureView) CastUtil.requireInstanceOf(getGameGlTextureView(), GpuTextureView.class);
    }

    public GpuSampler getSampler() {
        if (!this.samplerCreated) {
            this.samplerCreated = true;
            SamplerDescription description = this.delegate.deviceTexture().samplerDescription();
            this.sampler = MinecraftUtil.getSampler(description);
        }
        return super.getSampler();
    }

    public LabyTexture getDelegate() {
        return this.delegate;
    }

    public DeviceTexture deviceTexture() {
        return (DeviceTexture) CastUtil.requireInstanceOf(getTexture(), DeviceTexture.class);
    }

    public DeviceTextureView deviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(getTextureView(), DeviceTextureView.class);
    }

    private Laby3DGlTexture getGameGlTexture() {
        if (this.gameGlTexture == null) {
            this.gameGlTexture = new Laby3DGlTexture(this.delegate.deviceTexture());
        }
        return this.gameGlTexture;
    }

    private Laby3DGlTextureView getGameGlTextureView() {
        if (this.gameGlTextureView == null) {
            this.gameGlTextureView = new Laby3DGlTextureView(getGameGlTexture());
        }
        return this.gameGlTextureView;
    }
}

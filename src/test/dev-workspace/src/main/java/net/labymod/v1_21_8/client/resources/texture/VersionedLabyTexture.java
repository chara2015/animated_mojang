package net.labymod.v1_21_8.client.resources.texture;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_8.laby3d.pipeline.opengl.Laby3DGlTexture;
import net.labymod.v1_21_8.laby3d.pipeline.opengl.Laby3DGlTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends hrc implements Texture {
    private final LabyTexture delegate;
    private Laby3DGlTexture gameGlTexture;
    private Laby3DGlTextureView gameGlTextureView;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void a(boolean blur, boolean mipmap) {
    }

    public void b(boolean $$0) {
    }

    public void close() {
        this.delegate.release();
    }

    public GpuTexture a() {
        return (GpuTexture) CastUtil.requireInstanceOf(getGameGlTexture(), GpuTexture.class);
    }

    public GpuTextureView b() {
        return (GpuTextureView) CastUtil.requireInstanceOf(getGameGlTextureView(), GpuTextureView.class);
    }

    public LabyTexture getDelegate() {
        return this.delegate;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        return (DeviceTexture) CastUtil.requireInstanceOf(a(), DeviceTexture.class);
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(b(), DeviceTextureView.class);
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

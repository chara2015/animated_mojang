package net.labymod.v1_21_5.client.resources.texture;

import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_5.laby3d.pipeline.opengl.Laby3DGlTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends hkb implements Texture {
    private final LabyTexture delegate;
    private Laby3DGlTexture gameGlTexture;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void a(boolean blur, boolean mipmap) {
    }

    public void close() {
        this.delegate.release();
    }

    public GpuTexture a() {
        return (GpuTexture) CastUtil.requireInstanceOf(getGameGlTexture(), GpuTexture.class);
    }

    public LabyTexture getDelegate() {
        return this.delegate;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        return this.delegate.deviceTexture();
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return this.delegate.deviceTextureView();
    }

    private Laby3DGlTexture getGameGlTexture() {
        if (this.gameGlTexture == null) {
            this.gameGlTexture = new Laby3DGlTexture(this.delegate.deviceTexture());
        }
        return this.gameGlTexture;
    }
}

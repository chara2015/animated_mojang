package net.labymod.v1_12_2.client.resources.texture;

import java.io.IOException;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.opengl.textures.GlDeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends cdf implements Texture {
    private final LabyTexture delegate;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void b(boolean linear, boolean mipmap) {
        a(linear, mipmap);
    }

    public void a(boolean linear, boolean mipmap) {
    }

    public void a(cep lvt_1_1_) throws IOException {
    }

    public int b() {
        return ((GlDeviceTexture) CastUtil.requireInstanceOf(this.delegate.deviceTexture(), GlDeviceTexture.class)).getId();
    }

    public void c() {
        this.delegate.release();
        this.delegate.close();
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
}

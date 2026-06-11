package net.labymod.v1_17_1.client.resources.texture;

import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/resources/texture/VersionedLabyTexture.class */
public class VersionedLabyTexture extends exa implements Texture {
    private final LabyTexture delegate;

    public VersionedLabyTexture(LabyTexture delegate) {
        this.delegate = delegate;
    }

    public void a(boolean blur, boolean mipmap) {
    }

    public int b() {
        return this.delegate.deviceTexture().getId();
    }

    public void c() {
        this.delegate.release();
    }

    public void a(adt var1) throws IOException {
    }

    public void d() {
        Laby.gfx().bindTexture2D(this.delegate.deviceTextureView());
    }

    public void close() {
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

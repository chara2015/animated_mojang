package net.labymod.v1_21_11.laby3d.pipeline.opengl;

import com.mojang.blaze3d.opengl.GlTextureView;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/pipeline/opengl/Laby3DGlTextureView.class */
public class Laby3DGlTextureView extends GlTextureView {
    public /* bridge */ /* synthetic */ GpuTexture texture() {
        return super.texture();
    }

    public Laby3DGlTextureView(Laby3DGlTexture texture) {
        super(texture, 0, texture.getMipLevels());
    }

    public Laby3DGlTextureView(DeviceTextureView textureView) {
        super(new Laby3DGlTexture(textureView.texture()), 0, textureView.mipLevels());
    }
}

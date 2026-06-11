package net.labymod.v1_21_8.laby3d.pipeline.opengl;

import com.mojang.blaze3d.textures.GpuTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/laby3d/pipeline/opengl/Laby3DGlTextureView.class */
public class Laby3DGlTextureView extends fml {
    public /* bridge */ /* synthetic */ GpuTexture texture() {
        return super.a();
    }

    public Laby3DGlTextureView(Laby3DGlTexture texture) {
        super(texture, 0, texture.getMipLevels());
    }
}

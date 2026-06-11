package net.labymod.v26_1_2.laby3d.pipeline.opengl;

import com.mojang.blaze3d.opengl.GlTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/laby3d/pipeline/opengl/Laby3DGlTextureView.class */
public class Laby3DGlTextureView extends GlTextureView {
    public Laby3DGlTextureView(Laby3DGlTexture texture) {
        super(texture, 0, texture.getMipLevels());
    }
}

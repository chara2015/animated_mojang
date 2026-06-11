package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlTextureState.class */
public class GlTextureState extends GlBooleanState {
    private int texture;

    public GlTextureState() {
        super(GlConst.GL_TEXTURE_2D);
    }

    public int getTexture() {
        return this.texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }
}

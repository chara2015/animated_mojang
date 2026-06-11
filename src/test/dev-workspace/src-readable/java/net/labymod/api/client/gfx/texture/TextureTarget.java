package net.labymod.api.client.gfx.texture;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/texture/TextureTarget.class */
public enum TextureTarget {
    TEXTURE_1D(GlConst.GL_TEXTURE_1D),
    TEXTURE_1D_ARRAY(GlConst.GL_TEXTURE_1D_ARRAY),
    TEXTURE_2D(GlConst.GL_TEXTURE_2D),
    TEXTURE_2D_ARRAY(GlConst.GL_TEXTURE_2D_ARRAY),
    TEXTURE_3D(GlConst.GL_TEXTURE_3D),
    TEXTURE_RECTANGLE(GlConst.GL_TEXTURE_RECTANGLE),
    TEXTURE_CUBE_MAP(GlConst.GL_TEXTURE_CUBE_MAP),
    TEXTURE_CUBE_MAP_ARRAY(GlConst.GL_TEXTURE_CUBE_MAP_ARRAY),
    TEXTURE_BUFFER(35882),
    TEXTURE_2D_MULTISAMPLE(GlConst.GL_TEXTURE_2D_MULTISAMPLE),
    TEXTURE_2D_MULTISAMPLE_ARRAY(GlConst.GL_TEXTURE_2D_MULTISAMPLE_ARRAY);

    private final int id;

    TextureTarget(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

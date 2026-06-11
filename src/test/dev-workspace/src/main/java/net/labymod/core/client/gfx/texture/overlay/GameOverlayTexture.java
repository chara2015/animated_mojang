package net.labymod.core.client.gfx.texture.overlay;

import net.labymod.api.client.resources.texture.GameImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/texture/overlay/GameOverlayTexture.class */
public interface GameOverlayTexture {
    public static final float TEXTURE_SCALE = 0.6666667f;

    GameImage image();

    void upload();

    DynamicOverlayTexture dynamicTexture();
}

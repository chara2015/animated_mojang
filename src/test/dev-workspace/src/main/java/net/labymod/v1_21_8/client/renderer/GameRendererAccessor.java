package net.labymod.v1_21_8.client.renderer;

import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/renderer/GameRendererAccessor.class */
public interface GameRendererAccessor {
    gcy labyMod$getGuiRenderState();

    void renderGui();

    static GameRendererAccessor self(Object obj) {
        return (GameRendererAccessor) CastUtil.requireInstanceOf(obj, GameRendererAccessor.class);
    }
}

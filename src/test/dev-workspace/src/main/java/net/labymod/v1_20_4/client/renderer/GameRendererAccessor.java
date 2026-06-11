package net.labymod.v1_20_4.client.renderer;

import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/renderer/GameRendererAccessor.class */
public interface GameRendererAccessor {
    ewu labyMod$getGuiGraphics();

    static GameRendererAccessor self(Object obj) {
        return (GameRendererAccessor) CastUtil.requireInstanceOf(obj, GameRendererAccessor.class);
    }
}

package net.labymod.v1_21_11.client.renderer;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.gui.render.state.GuiRenderState;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/renderer/GameRendererAccessor.class */
public interface GameRendererAccessor {
    GuiRenderState labyMod$getGuiRenderState();

    void renderGui();

    static GameRendererAccessor self(Object obj) {
        return (GameRendererAccessor) CastUtil.requireInstanceOf(obj, GameRendererAccessor.class);
    }
}

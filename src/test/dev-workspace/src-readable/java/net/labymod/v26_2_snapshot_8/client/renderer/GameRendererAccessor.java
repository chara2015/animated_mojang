package net.labymod.v26_2_snapshot_8.client.renderer;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.renderer.state.gui.GuiRenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/renderer/GameRendererAccessor.class */
public interface GameRendererAccessor {
    GuiRenderState labyMod$getGuiRenderState();

    static GameRendererAccessor self(Object obj) {
        return (GameRendererAccessor) CastUtil.requireInstanceOf(obj, GameRendererAccessor.class);
    }
}

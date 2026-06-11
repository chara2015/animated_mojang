package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/TextDisplayEntityRenderState.class */
public class TextDisplayEntityRenderState extends DisplayEntityRenderState {
    public Display.TextDisplay.TextRenderState textRenderState;
    public Display.TextDisplay.CachedInfo cachedInfo;

    @Override // net.minecraft.client.renderer.entity.state.DisplayEntityRenderState
    public boolean hasSubState() {
        return this.textRenderState != null;
    }
}

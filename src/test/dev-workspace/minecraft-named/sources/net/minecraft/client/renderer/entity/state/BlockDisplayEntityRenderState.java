package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/BlockDisplayEntityRenderState.class */
public class BlockDisplayEntityRenderState extends DisplayEntityRenderState {
    public Display.BlockDisplay.BlockRenderState blockRenderState;

    @Override // net.minecraft.client.renderer.entity.state.DisplayEntityRenderState
    public boolean hasSubState() {
        return this.blockRenderState != null;
    }
}

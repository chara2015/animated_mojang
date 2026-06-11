package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemStackRenderState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/ItemDisplayEntityRenderState.class */
public class ItemDisplayEntityRenderState extends DisplayEntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();

    @Override // net.minecraft.client.renderer.entity.state.DisplayEntityRenderState
    public boolean hasSubState() {
        return !this.item.isEmpty();
    }
}

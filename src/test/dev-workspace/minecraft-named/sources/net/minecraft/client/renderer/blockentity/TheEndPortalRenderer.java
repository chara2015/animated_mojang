package net.minecraft.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.state.EndPortalRenderState;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/TheEndPortalRenderer.class */
public class TheEndPortalRenderer extends AbstractEndPortalRenderer<TheEndPortalBlockEntity, EndPortalRenderState> {
    @Override // net.minecraft.client.renderer.blockentity.BlockEntityRenderer
    public EndPortalRenderState createRenderState() {
        return new EndPortalRenderState();
    }
}

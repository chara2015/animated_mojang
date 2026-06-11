package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.world.level.block.entity.BoundingBoxRenderable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BlockEntityWithBoundingBoxRenderState.class */
public class BlockEntityWithBoundingBoxRenderState extends BlockEntityRenderState {
    public boolean isVisible;
    public BoundingBoxRenderable.Mode mode;
    public BoundingBoxRenderable.RenderableBox box;
    public InvisibleBlockType[] invisibleBlocks;
    public boolean[] structureVoids;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BlockEntityWithBoundingBoxRenderState$InvisibleBlockType.class */
    public enum InvisibleBlockType {
        AIR,
        BARRIER,
        LIGHT,
        STRUCTURE_VOID
    }
}

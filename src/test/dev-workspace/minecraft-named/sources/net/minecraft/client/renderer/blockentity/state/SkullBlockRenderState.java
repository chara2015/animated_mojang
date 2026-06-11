package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SkullBlock;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/SkullBlockRenderState.class */
public class SkullBlockRenderState extends BlockEntityRenderState {
    public float animationProgress;
    public float rotationDegrees;
    public RenderType renderType;
    public Direction direction = Direction.NORTH;
    public SkullBlock.Type skullType = SkullBlock.Types.ZOMBIE;
}

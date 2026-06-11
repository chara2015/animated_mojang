package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BedRenderState.class */
public class BedRenderState extends BlockEntityRenderState {
    public DyeColor color = DyeColor.WHITE;
    public Direction facing = Direction.NORTH;
    public boolean isHead;
}

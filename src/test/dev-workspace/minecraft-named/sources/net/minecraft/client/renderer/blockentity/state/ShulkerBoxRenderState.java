package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState.class */
public class ShulkerBoxRenderState extends BlockEntityRenderState {
    public Direction direction = Direction.NORTH;
    public DyeColor color;
    public float progress;
}

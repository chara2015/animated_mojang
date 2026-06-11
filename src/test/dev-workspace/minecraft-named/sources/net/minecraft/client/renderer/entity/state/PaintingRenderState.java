package net.minecraft.client.renderer.entity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/PaintingRenderState.class */
public class PaintingRenderState extends EntityRenderState {
    public PaintingVariant variant;
    public Direction direction = Direction.NORTH;
    public int[] lightCoordsPerBlock = new int[0];
}

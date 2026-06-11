package net.minecraft.client.renderer.entity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/ShulkerRenderState.class */
public class ShulkerRenderState extends LivingEntityRenderState {
    public DyeColor color;
    public float peekAmount;
    public float yHeadRot;
    public float yBodyRot;
    public Vec3 renderOffset = Vec3.ZERO;
    public Direction attachFace = Direction.DOWN;
}

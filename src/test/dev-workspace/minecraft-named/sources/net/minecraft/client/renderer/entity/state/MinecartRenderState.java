package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/MinecartRenderState.class */
public class MinecartRenderState extends EntityRenderState {
    public float xRot;
    public float yRot;
    public long offsetSeed;
    public int hurtDir;
    public float hurtTime;
    public float damageTime;
    public int displayOffset;
    public BlockState displayBlockState = Blocks.AIR.defaultBlockState();
    public boolean isNewRender;
    public Vec3 renderPos;
    public Vec3 posOnRail;
    public Vec3 frontPos;
    public Vec3 backPos;
}

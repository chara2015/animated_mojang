package net.minecraft.client.renderer.state;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/state/CameraRenderState.class */
public class CameraRenderState {
    public boolean initialized;
    public BlockPos blockPos = BlockPos.ZERO;
    public Vec3 pos = new Vec3(Density.SURFACE, Density.SURFACE, Density.SURFACE);
    public Vec3 entityPos = new Vec3(Density.SURFACE, Density.SURFACE, Density.SURFACE);
    public Quaternionf orientation = new Quaternionf();
}

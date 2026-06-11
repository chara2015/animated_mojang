package net.minecraft.world.level.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/Hopper.class */
public interface Hopper extends Container {
    public static final AABB SUCK_AABB = Block.column(16.0d, 11.0d, 32.0d).toAabbs().get(0);

    double getLevelX();

    double getLevelY();

    double getLevelZ();

    boolean isGridAligned();

    default AABB getSuckAabb() {
        return SUCK_AABB;
    }
}

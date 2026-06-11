package net.minecraft.world.inventory;

import net.minecraft.world.entity.player.StackedItemContents;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/inventory/StackedContentsCompatible.class */
@FunctionalInterface
public interface StackedContentsCompatible {
    void fillStackedContents(StackedItemContents stackedItemContents);
}

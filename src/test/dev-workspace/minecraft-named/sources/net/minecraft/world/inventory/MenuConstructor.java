package net.minecraft.world.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/inventory/MenuConstructor.class */
@FunctionalInterface
public interface MenuConstructor {
    AbstractContainerMenu createMenu(int i, Inventory inventory, Player player);
}

package net.minecraft.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuConstructor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/MenuProvider.class */
public interface MenuProvider extends MenuConstructor {
    Component getDisplayName();
}

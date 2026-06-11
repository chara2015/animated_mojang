package net.labymod.api.client.entity.player;

import net.labymod.api.client.world.item.ItemStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/Inventory.class */
public interface Inventory {
    int getSelectedIndex();

    void setSelectedIndex(int i);

    ItemStack itemStackAt(int i);

    void setCreativeModeItemStack(int i, ItemStack itemStack);

    int countAllArrows();

    ItemStack getNextArrows();
}

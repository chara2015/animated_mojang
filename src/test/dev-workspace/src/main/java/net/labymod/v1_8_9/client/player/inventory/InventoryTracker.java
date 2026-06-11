package net.labymod.v1_8_9.client.player.inventory;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/player/inventory/InventoryTracker.class */
public class InventoryTracker {
    private final ItemStack[] prevContent = new ItemStack[36];

    @Subscribe
    public void onTick(GameTickEvent event) {
        Minecraft minecraft;
        ClientPlayer player;
        if (event.phase() != Phase.POST || (minecraft = Laby.labyAPI().minecraft()) == null || (player = minecraft.getClientPlayer()) == null) {
            return;
        }
        Inventory inventory = player.inventory();
        for (int slot = 0; slot < this.prevContent.length; slot++) {
            ItemStack prevItemStack = this.prevContent[slot];
            ItemStack itemStack = inventory.itemStackAt(slot);
            if (!compare(prevItemStack, itemStack)) {
                this.prevContent[slot] = itemStack;
                Laby.fireEvent(new InventorySetSlotEvent(inventory, slot, itemStack));
            }
        }
    }

    private boolean compare(ItemStack prevItemStack, ItemStack itemStack) {
        if (prevItemStack == null && itemStack == null) {
            return true;
        }
        return prevItemStack != null && itemStack != null && prevItemStack.getSize() == itemStack.getSize() && prevItemStack.getAsItem() == itemStack.getAsItem();
    }
}

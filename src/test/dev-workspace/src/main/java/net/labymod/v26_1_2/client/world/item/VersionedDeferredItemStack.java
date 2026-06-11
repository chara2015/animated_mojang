package net.labymod.v26_1_2.client.world.item;

import net.labymod.api.client.world.item.DeferredItemStack;
import net.labymod.api.client.world.item.ItemStack;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/world/item/VersionedDeferredItemStack.class */
public class VersionedDeferredItemStack implements DeferredItemStack {
    private final ItemStackProvider provider;
    private ItemStack itemStack;

    public VersionedDeferredItemStack(ItemStackProvider provider) {
        this.provider = provider;
    }

    @Override // net.labymod.api.client.world.item.DeferredItemStack, net.labymod.api.client.world.item.ItemStack
    public boolean areComponentsBound() {
        return this.provider.areComponentsBound();
    }

    @Override // net.labymod.api.client.world.item.DeferredItemStack
    public ItemStack get() {
        if (!areComponentsBound()) {
            throw new IllegalStateException("Item components are not bound yet!");
        }
        if (this.itemStack == null) {
            this.itemStack = this.provider.createStack();
        }
        return this.itemStack;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/world/item/VersionedDeferredItemStack$ItemStackProvider.class */
    public interface ItemStackProvider {
        Item getItem();

        ItemStack createStack();

        default boolean areComponentsBound() {
            Holder.Reference<Item> holder = getItem().builtInRegistryHolder();
            return holder.areComponentsBound();
        }
    }
}

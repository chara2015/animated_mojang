package net.labymod.api.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/Item.class */
public interface Item {
    ResourceLocation getIdentifier();

    int getMaximumStackSize();

    int getMaximumDamage();

    boolean isAir();
}

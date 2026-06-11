package net.labymod.api.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.VanillaItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/Block.class */
public interface Block {
    ResourceLocation id();

    boolean isAir();

    BlockState defaultState();

    default boolean is(VanillaItem item) {
        return id().equals(item.identifier());
    }
}

package net.labymod.api.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/ItemStackFactory.class */
@Referenceable
public interface ItemStackFactory {
    ItemStack create(ResourceLocation resourceLocation, int i);

    ItemStack create(BlockState blockState);

    default ItemStack create(String namespace, String path) {
        return create(namespace, path, 1);
    }

    default ItemStack create(String namespace, String path, int count) {
        return create(ResourceLocation.create(namespace, path), count);
    }

    default ItemStack create(ResourceLocation location) {
        return create(location, 1);
    }

    default ItemStack create(VanillaItem item) {
        return create(item.identifier());
    }
}

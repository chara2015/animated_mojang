package net.labymod.api.client.entity.decoration;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.VanillaItems;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/decoration/ItemFrame.class */
public interface ItemFrame extends Entity {
    @Nullable
    ItemStack getItem();

    default ResourceLocation getFrameType() {
        return VanillaItems.ITEM_FRAME.identifier();
    }
}

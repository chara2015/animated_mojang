package net.labymod.api.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/ItemUtil.class */
public final class ItemUtil {
    public static ResourceLocation getFrameType(boolean glowing) {
        return glowing ? VanillaItems.GLOW_ITEM_FRAME.identifier() : VanillaItems.ITEM_FRAME.identifier();
    }
}

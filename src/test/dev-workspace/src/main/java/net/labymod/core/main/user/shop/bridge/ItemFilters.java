package net.labymod.core.main.user.shop.bridge;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import net.labymod.core.main.user.shop.cosmetic.MinecraftItemTypeData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ItemFilters.class */
public class ItemFilters {
    private static final ItemFilter DEFAULT_FILTER = definition -> {
        if (definition == null) {
            return true;
        }
        if (definition.cosmeticType() != CosmeticType.MINECRAFT_ITEM) {
            return definition.cosmeticType() == CosmeticType.WALKING_PET;
        }
        MinecraftItemTypeData typeData = (MinecraftItemTypeData) definition.typeData(MinecraftItemTypeData.class);
        return typeData != null && typeData.isAvailable();
    };
    private static final ItemFilter NO_FILTER = definition -> {
        return false;
    };
    private static final ItemFilter NO_WALKING_PETS = definition -> {
        return definition != null && definition.cosmeticType() == CosmeticType.WALKING_PET;
    };

    public static ItemFilter defaultFilter() {
        return DEFAULT_FILTER;
    }

    public static ItemFilter noFilter() {
        return NO_FILTER;
    }

    public static ItemFilter noWalkingPets() {
        return NO_WALKING_PETS;
    }

    public static boolean filterMinecraftItem(MinecraftItemTypeData typeData, ItemStack itemStack) {
        ResourceLocation itemId;
        return typeData == null || !typeData.isResolved() || (itemId = typeData.itemIdentifier()) == null || !itemStack.getIdentifier().equals(itemId);
    }
}

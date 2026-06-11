package net.labymod.core.main.user.shop.bridge;

import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ItemFilter.class */
@FunctionalInterface
public interface ItemFilter {
    boolean filter(@Nullable CosmeticDefinition cosmeticDefinition);
}

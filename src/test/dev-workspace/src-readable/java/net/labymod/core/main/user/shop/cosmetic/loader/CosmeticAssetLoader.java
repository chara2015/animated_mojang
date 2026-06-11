package net.labymod.core.main.user.shop.cosmetic.loader;

import java.util.concurrent.CompletableFuture;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/loader/CosmeticAssetLoader.class */
public interface CosmeticAssetLoader {
    CompletableFuture<CosmeticAssets> loadAssets(CosmeticDefinition cosmeticDefinition);
}

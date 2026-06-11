package net.labymod.core.main.user.shop.cosmetic.loader;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/loader/LocalCosmeticAssetLoader.class */
public class LocalCosmeticAssetLoader implements CosmeticAssetLoader {
    private final Supplier<Model> modelFactory;

    public LocalCosmeticAssetLoader(Supplier<Model> modelFactory) {
        this.modelFactory = modelFactory;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.loader.CosmeticAssetLoader
    public CompletableFuture<CosmeticAssets> loadAssets(CosmeticDefinition definition) {
        Model model = this.modelFactory.get();
        CosmeticAssets assets = CosmeticAssets.ofModel(model, Collections.emptyList(), false);
        definition.setAssets(assets);
        return CompletableFuture.completedFuture(assets);
    }
}

package net.labymod.core.main.user.shop.cosmetic.texture;

import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/IndividualTextureProvider.class */
public class IndividualTextureProvider implements TextureProvider {
    private final CosmeticTextureService textureService;

    public IndividualTextureProvider(CosmeticTextureService textureService) {
        this.textureService = textureService;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.texture.TextureProvider
    @Nullable
    public ResourceLocation resolveTexture(CosmeticDefinition definition, CosmeticState state, Player player, ItemMetadata metadata) {
        state.updateAnimatedTexture();
        ResourceLocation resolved = state.resolvedTextureLocation();
        if (resolved != null) {
            return resolved;
        }
        if (state.isTextureLoading() || state.isTextureFailed()) {
            return Textures.EMPTY;
        }
        state.setTextureLoading(true);
        LabyExecutors.submitBackgroundTask(() -> {
            CompletableResourceLocation completable = this.textureService.loadTexture(definition, state, metadata, player);
            if (completable == null) {
                state.setTextureLoading(false);
                state.setTextureFailed(true);
            } else {
                completable.addCompletableListener(() -> {
                    if (completable.hasError()) {
                        state.setTextureFailed(true);
                    } else {
                        state.setResolvedTextureLocation(completable.getCompleted());
                    }
                    state.setTextureLoading(false);
                });
            }
        });
        return Textures.EMPTY;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.texture.TextureProvider
    @Nullable
    public UVTransform getUVTransform(CosmeticDefinition definition, CosmeticState state) {
        return null;
    }
}

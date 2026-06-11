package net.labymod.core.main.user.shop.cosmetic.texture;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/TextureProvider.class */
public interface TextureProvider {
    @Nullable
    ResourceLocation resolveTexture(CosmeticDefinition cosmeticDefinition, CosmeticState cosmeticState, Player player, ItemMetadata itemMetadata);

    @Nullable
    UVTransform getUVTransform(CosmeticDefinition cosmeticDefinition, CosmeticState cosmeticState);
}

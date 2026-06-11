package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/GeometryEffectApplier.class */
@FunctionalInterface
public interface GeometryEffectApplier {
    void apply(Player player, ItemMetadata itemMetadata, ItemEffect.EffectData effectData);
}

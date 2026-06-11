package net.labymod.core.main.user.shop.item.geometry.effect.effects.color;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/color/GlowingGeometryEffect.class */
public class GlowingGeometryEffect extends GeometryEffect {
    public GlowingGeometryEffect(String effectArgument, ModelPart modelPart) {
        super(effectArgument, modelPart, GeometryEffect.Type.BUFFER_CREATION, 0);
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        this.appearanceStore.setGlowing(this.appearance, true);
    }
}

package net.labymod.core.main.user.shop.item.geometry.effect.effects.metadata;

import net.labymod.api.Namespaces;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/metadata/MinecraftItemGeometryEffect.class */
public class MinecraftItemGeometryEffect extends GeometryEffect {
    private ResourceLocation itemIdentifier;
    private boolean shield;

    public MinecraftItemGeometryEffect(String effectArgument, ModelPart modelPart) {
        super(effectArgument, modelPart, GeometryEffect.Type.METADATA, 2);
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        if (!hasParameter(0)) {
            return false;
        }
        String[] arguments = getArguments();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < arguments.length; i++) {
            stringBuilder.append(arguments[i]);
            if (i < arguments.length - 1) {
                stringBuilder.append("_");
            }
        }
        if (stringBuilder.isEmpty()) {
            return false;
        }
        this.itemIdentifier = ResourceLocation.create(Namespaces.MINECRAFT, stringBuilder.toString());
        if (VanillaItems.SHIELD.identifier().equals(this.itemIdentifier)) {
            this.shield = true;
            return true;
        }
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        if (this.shield) {
            player.gameUser().setTag(GameUser.HIDE_SHIELD);
        }
    }

    @Nullable
    public ResourceLocation getItemIdentifier() {
        return this.itemIdentifier;
    }
}

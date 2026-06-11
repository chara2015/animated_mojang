package net.labymod.core.main.user.shop.item.geometry.effect.effects.layer;

import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.model.TextureDetails;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/layer/LayerGeometryEffect.class */
public class LayerGeometryEffect extends GeometryEffect {
    private UUID uniqueId;
    private boolean negate;
    private boolean slim;
    private boolean rightSide;

    public LayerGeometryEffect(String effectArgument, ModelPart model) {
        super(effectArgument, model, GeometryEffect.Type.BUFFER_CREATION, 1);
        this.uniqueId = null;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        String id = getParameter(0);
        if (id.equals("slim")) {
            this.slim = true;
        } else if (id.equals("right")) {
            this.rightSide = true;
        } else {
            try {
                this.uniqueId = UUIDTypeAdapter.fromString(id);
            } catch (Exception e) {
                return false;
            }
        }
        this.negate = hasParameter(1) && getParameter(1).equals("negate");
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        if (this.uniqueId != null) {
            TextureDetails textureDetails = itemMetadata.getCosmeticTexture();
            setModelPartVisible((textureDetails == null || textureDetails.getUuid() == null || this.negate == textureDetails.getUuid().equals(this.uniqueId)) ? false : true);
        }
        if (this.slim) {
            setModelPartVisible(this.negate != effectData.isSlim());
        }
        if (this.rightSide) {
            setModelPartVisible(this.negate != effectData.isRightSide());
        }
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public void setModelPartVisible(boolean condition) {
        this.modelPart.setVisible(condition);
        this.appearanceStore.setVisible(this.appearance, condition);
    }
}

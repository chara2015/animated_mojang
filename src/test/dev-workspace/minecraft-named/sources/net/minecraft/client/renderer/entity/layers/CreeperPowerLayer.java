package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.creeper.CreeperModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/CreeperPowerLayer.class */
public class CreeperPowerLayer extends EnergySwirlLayer<CreeperRenderState, CreeperModel> {
    private static final Identifier POWER_LOCATION = Identifier.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel model;

    public CreeperPowerLayer(RenderLayerParent<CreeperRenderState, CreeperModel> $$0, EntityModelSet $$1) {
        super($$0);
        this.model = new CreeperModel($$1.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.renderer.entity.layers.EnergySwirlLayer
    public boolean isPowered(CreeperRenderState $$0) {
        return $$0.isPowered;
    }

    @Override // net.minecraft.client.renderer.entity.layers.EnergySwirlLayer
    protected float xOffset(float $$0) {
        return $$0 * 0.01f;
    }

    @Override // net.minecraft.client.renderer.entity.layers.EnergySwirlLayer
    protected Identifier getTextureLocation() {
        return POWER_LOCATION;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.renderer.entity.layers.EnergySwirlLayer
    public CreeperModel model() {
        return this.model;
    }
}

package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.DrownedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/DrownedOuterLayer.class */
public class DrownedOuterLayer extends RenderLayer<ZombieRenderState, DrownedModel> {
    private static final Identifier DROWNED_OUTER_LAYER_LOCATION = Identifier.withDefaultNamespace("textures/entity/zombie/drowned_outer_layer.png");
    private final DrownedModel model;
    private final DrownedModel babyModel;

    public DrownedOuterLayer(RenderLayerParent<ZombieRenderState, DrownedModel> $$0, EntityModelSet $$1) {
        super($$0);
        this.model = new DrownedModel($$1.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
        this.babyModel = new DrownedModel($$1.bakeLayer(ModelLayers.DROWNED_BABY_OUTER_LAYER));
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, ZombieRenderState $$3, float $$4, float $$5) {
        DrownedModel $$6 = $$3.isBaby ? this.babyModel : this.model;
        coloredCutoutModelCopyLayerRender($$6, DROWNED_OUTER_LAYER_LOCATION, $$0, $$1, $$2, $$3, -1, 1);
    }
}

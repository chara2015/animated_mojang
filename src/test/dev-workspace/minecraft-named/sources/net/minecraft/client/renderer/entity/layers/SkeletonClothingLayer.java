package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/SkeletonClothingLayer.class */
public class SkeletonClothingLayer<S extends SkeletonRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private final SkeletonModel<S> layerModel;
    private final Identifier clothesLocation;

    public SkeletonClothingLayer(RenderLayerParent<S, M> $$0, EntityModelSet $$1, ModelLayerLocation $$2, Identifier $$3) {
        super($$0);
        this.clothesLocation = $$3;
        this.layerModel = new SkeletonModel<>($$1.bakeLayer($$2));
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, S $$3, float $$4, float $$5) {
        coloredCutoutModelCopyLayerRender(this.layerModel, this.clothesLocation, $$0, $$1, $$2, $$3, -1, 1);
    }
}

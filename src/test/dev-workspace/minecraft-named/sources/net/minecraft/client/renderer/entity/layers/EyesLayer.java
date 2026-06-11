package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/EyesLayer.class */
public abstract class EyesLayer<S extends EntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    public abstract RenderType renderType();

    public EyesLayer(RenderLayerParent<S, M> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, S $$3, float $$4, float $$5) {
        $$1.order(1).submitModel(getParentModel(), $$3, $$0, renderType(), $$2, OverlayTexture.NO_OVERLAY, -1, null, $$3.outlineColor, null);
    }
}

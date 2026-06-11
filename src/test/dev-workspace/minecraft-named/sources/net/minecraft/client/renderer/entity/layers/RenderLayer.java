package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/RenderLayer.class */
public abstract class RenderLayer<S extends EntityRenderState, M extends EntityModel<? super S>> {
    private final RenderLayerParent<S, M> renderer;

    public abstract void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, S s, float f, float f2);

    public RenderLayer(RenderLayerParent<S, M> $$0) {
        this.renderer = $$0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static <S extends LivingEntityRenderState> void coloredCutoutModelCopyLayerRender(Model<? super S> $$0, Identifier $$1, PoseStack $$2, SubmitNodeCollector $$3, int $$4, S s, int $$6, int $$7) {
        if (!s.isInvisible) {
            renderColoredCutoutModel($$0, $$1, $$2, $$3, $$4, s, $$6, $$7);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static <S extends LivingEntityRenderState> void renderColoredCutoutModel(Model<? super S> $$0, Identifier $$1, PoseStack $$2, SubmitNodeCollector $$3, int $$4, S s, int $$6, int $$7) {
        $$3.order($$7).submitModel($$0, s, $$2, RenderTypes.entityCutoutNoCull($$1), $$4, LivingEntityRenderer.getOverlayCoords(s, 0.0f), $$6, null, s.outlineColor, null);
    }

    public M getParentModel() {
        return (M) this.renderer.getModel();
    }
}

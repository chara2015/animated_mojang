package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.panda.PandaModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PandaRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/PandaHoldsItemLayer.class */
public class PandaHoldsItemLayer extends RenderLayer<PandaRenderState, PandaModel> {
    public PandaHoldsItemLayer(RenderLayerParent<PandaRenderState, PandaModel> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, PandaRenderState $$3, float $$4, float $$5) {
        ItemStackRenderState $$6 = $$3.heldItem;
        if ($$6.isEmpty() || !$$3.isSitting || $$3.isScared) {
            return;
        }
        float $$7 = -0.6f;
        float $$8 = 1.4f;
        if ($$3.isEating) {
            $$7 = (-0.6f) - ((0.2f * Mth.sin($$3.ageInTicks * 0.6f)) + 0.2f);
            $$8 = 1.4f - (0.09f * Mth.sin($$3.ageInTicks * 0.6f));
        }
        $$0.pushPose();
        $$0.translate(0.1f, $$8, $$7);
        $$6.submit($$0, $$1, $$2, OverlayTexture.NO_OVERLAY, $$3.outlineColor);
        $$0.popPose();
    }
}

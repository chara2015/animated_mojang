package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.monster.enderman.EndermanModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EndermanRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionfc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/CarriedBlockLayer.class */
public class CarriedBlockLayer extends RenderLayer<EndermanRenderState, EndermanModel<EndermanRenderState>> {
    public CarriedBlockLayer(RenderLayerParent<EndermanRenderState, EndermanModel<EndermanRenderState>> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, EndermanRenderState $$3, float $$4, float $$5) {
        BlockState $$6 = $$3.carriedBlock;
        if ($$6 == null) {
            return;
        }
        $$0.pushPose();
        $$0.translate(0.0f, 0.6875f, -0.75f);
        $$0.mulPose((Quaternionfc) Axis.XP.rotationDegrees(20.0f));
        $$0.mulPose((Quaternionfc) Axis.YP.rotationDegrees(45.0f));
        $$0.translate(0.25f, 0.1875f, 0.25f);
        $$0.scale(-0.5f, -0.5f, 0.5f);
        $$0.mulPose((Quaternionfc) Axis.YP.rotationDegrees(90.0f));
        $$1.submitBlock($$0, $$6, $$2, OverlayTexture.NO_OVERLAY, $$3.outlineColor);
        $$0.popPose();
    }
}

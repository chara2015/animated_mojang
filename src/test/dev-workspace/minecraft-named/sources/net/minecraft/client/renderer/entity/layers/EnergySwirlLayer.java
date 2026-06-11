package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/layers/EnergySwirlLayer.class */
public abstract class EnergySwirlLayer<S extends EntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    protected abstract boolean isPowered(S s);

    protected abstract float xOffset(float f);

    protected abstract Identifier getTextureLocation();

    protected abstract M model();

    public EnergySwirlLayer(RenderLayerParent<S, M> $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.entity.layers.RenderLayer
    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, S $$3, float $$4, float $$5) {
        if (!isPowered($$3)) {
            return;
        }
        float $$6 = $$3.ageInTicks;
        $$1.order(1).submitModel(model(), $$3, $$0, RenderTypes.energySwirl(getTextureLocation(), xOffset($$6) % 1.0f, ($$6 * 0.01f) % 1.0f), $$2, OverlayTexture.NO_OVERLAY, CommonColors.GRAY, null, $$3.outlineColor, null);
    }
}

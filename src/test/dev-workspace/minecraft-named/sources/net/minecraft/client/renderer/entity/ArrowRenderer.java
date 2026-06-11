package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.joml.Quaternionfc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/ArrowRenderer.class */
public abstract class ArrowRenderer<T extends AbstractArrow, S extends ArrowRenderState> extends EntityRenderer<T, S> {
    private final ArrowModel model;

    protected abstract Identifier getTextureLocation(S s);

    public ArrowRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
        this.model = new ArrowModel($$0.bakeLayer(ModelLayers.ARROW));
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public void submit(S $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3) {
        $$1.pushPose();
        $$1.mulPose((Quaternionfc) Axis.YP.rotationDegrees($$0.yRot - 90.0f));
        $$1.mulPose((Quaternionfc) Axis.ZP.rotationDegrees($$0.xRot));
        $$2.submitModel(this.model, $$0, $$1, RenderTypes.entityCutout(getTextureLocation($$0)), $$0.lightCoords, OverlayTexture.NO_OVERLAY, $$0.outlineColor, null);
        $$1.popPose();
        super.submit($$0, $$1, $$2, $$3);
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public void extractRenderState(T $$0, S $$1, float $$2) {
        super.extractRenderState($$0, $$1, $$2);
        $$1.xRot = $$0.getXRot($$2);
        $$1.yRot = $$0.getYRot($$2);
        $$1.shake = $$0.shakeTime - $$2;
    }
}

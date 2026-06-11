package net.labymod.v1_21_3.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/entity/layers/MixinItemInHandLayer.class */
@Mixin({gvx.class})
public class MixinItemInHandLayer {
    private Stack labyMod$stack;
    private bwg labyMod$livingEntity;

    @Inject(method = {"renderArmWithItem"}, at = {@At("HEAD")})
    private void labyMod$storeEntity(gyt $$0, hdn $$1, cxp $$2, cxn $$3, bwa $$4, fgs $$5, gll $$6, int $$7, CallbackInfo ci) {
        EntityRenderStateAccessor<bwg> entityState = EntityRenderStateAccessor.self($$0);
        if (entityState == null) {
            labyMod$invalidateCustomData();
            return;
        }
        bwg entity = entityState.labyMod$getEntity();
        if (entity == null) {
            labyMod$invalidateCustomData();
        } else {
            this.labyMod$livingEntity = entity;
        }
    }

    @Redirect(method = {"renderArmWithItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"))
    private void labyMod$callItemInHandLayerRenderEvent(gso renderer, cxp itemStack, cxn displayContext, boolean leftHand, fgs poseStack, gll bufferSource, int packedLightCoords, int packedOverlayCoords, hdn bakedModel) {
        if (this.labyMod$livingEntity == null) {
            return;
        }
        this.labyMod$stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, this.labyMod$livingEntity, itemStack, displayContext, leftHand, packedLightCoords);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(itemStack, displayContext, leftHand, poseStack, bufferSource, packedLightCoords, packedOverlayCoords, bakedModel);
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, this.labyMod$livingEntity, itemStack, displayContext, leftHand, packedLightCoords);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, bwg livingEntity, cxp itemStack, cxn type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity) livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }

    private void labyMod$invalidateCustomData() {
        this.labyMod$livingEntity = null;
    }
}

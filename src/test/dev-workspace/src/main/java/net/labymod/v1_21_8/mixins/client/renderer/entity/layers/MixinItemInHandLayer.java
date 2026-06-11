package net.labymod.v1_21_8.mixins.client.renderer.entity.layers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.v1_21_8.client.renderer.ItemStackRenderStateAccessor;
import net.labymod.v1_21_8.client.util.EntityRenderStateAccessor;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/entity/layers/MixinItemInHandLayer.class */
@Mixin({hil.class})
public class MixinItemInHandLayer {
    private Stack labyMod$stack;
    private cam labyMod$livingEntity;
    private dcv labyMod$itemStack;

    @Inject(method = {"renderArmWithItem"}, at = {@At("HEAD")})
    private void labyMod$storeEntity(hjr armedEntityRenderState, hoh itemStackRenderState, cad humanoidArm, fod poseStack, gxn bufferSource, int packedLightCoords, CallbackInfo ci) {
        EntityRenderStateAccessor<cam> entityState = EntityRenderStateAccessor.self(armedEntityRenderState);
        if (entityState == null) {
            labyMod$invalidateCustomData();
            return;
        }
        cam entity = entityState.labyMod$getEntity();
        if (entity == null) {
            labyMod$invalidateCustomData();
        } else {
            this.labyMod$livingEntity = entity;
            this.labyMod$itemStack = this.labyMod$livingEntity.a(humanoidArm);
        }
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V")})
    private void labyMod$callItemInHandLayerRenderEvent(hoh renderState, fod poseStack, gxn bufferSource, int packedLightCoords, int packedOverlayCoords, Operation<Void> original) {
        if (this.labyMod$livingEntity == null) {
            return;
        }
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) renderState;
        boolean leftHand = accessor.labyMod$isLeftHand();
        dct displayContext = accessor.labyMod$getItemDisplayContext();
        this.labyMod$stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, this.labyMod$livingEntity, this.labyMod$itemStack, displayContext, leftHand, packedLightCoords);
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[]{renderState, poseStack, bufferSource, Integer.valueOf(packedLightCoords), Integer.valueOf(packedOverlayCoords)});
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, this.labyMod$livingEntity, this.labyMod$itemStack, displayContext, leftHand, packedLightCoords);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, cam livingEntity, dcv itemStack, dct type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity) livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }

    private void labyMod$invalidateCustomData() {
        this.labyMod$itemStack = null;
        this.labyMod$livingEntity = null;
    }
}

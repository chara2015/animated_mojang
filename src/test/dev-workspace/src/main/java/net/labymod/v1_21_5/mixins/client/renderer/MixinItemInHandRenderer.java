package net.labymod.v1_21_5.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/MixinItemInHandRenderer.class */
@Mixin({grg.class})
public abstract class MixinItemInHandRenderer {
    private RenderFirstPersonItemInHandEvent event;

    @Shadow
    protected abstract void b(fld fldVar, bxw bxwVar, float f);

    @Shadow
    protected abstract void a(fld fldVar, bxw bxwVar, float f);

    @Inject(method = {"renderArmWithItem"}, at = {@At("HEAD")})
    private void labymod$renderArmWithItemPre(gqj player, float partialTicks, float yaw, bvb hand, float attackProgress, dak itemStack, float equipProgress, fld poseStack, grn source, int packedLight, CallbackInfo callbackInfo) {
        boolean isMainHand = hand == bvb.a;
        bxw humanoidArm = isMainHand ? player.fy() : player.fy().e();
        boolean isRightArm = humanoidArm == bxw.b;
        boolean isUsingItem = player.fz() && player.fC() > 0 && player.fA() == hand;
        Player apiPlayer = (Player) player;
        this.event = new RenderFirstPersonItemInHandEvent(((VanillaStackAccessor) poseStack).stack(source), apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.w()), partialTicks, equipProgress, attackProgress, isUsingItem);
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmTransformPre(grg instance, fld poseStack, bxw arm, float equipProgress, Operation<Void> original) {
        injectApplyItemArmTransform(instance, poseStack, arm, original);
    }

    @WrapOperation(method = {"swingArm"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmTransformAtSwingArmPre(grg instance, fld poseStack, bxw arm, float equipProgress, Operation<Void> original) {
        injectApplyItemArmTransform(instance, poseStack, arm, original);
    }

    private void injectApplyItemArmTransform(grg instance, fld poseStack, bxw arm, Operation<Void> original) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            b(poseStack, arm, this.event.getEquipProgress());
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
        if (this.event.isUsingItem() && this.event.isApplyItemArmAttackTransform() && this.event.isAttackWhileItemUse()) {
            RenderFirstPersonItemInHandEvent.AnimationType type = this.event.animationType();
            if (type == RenderFirstPersonItemInHandEvent.AnimationType.BOW || type == RenderFirstPersonItemInHandEvent.AnimationType.EAT || type == RenderFirstPersonItemInHandEvent.AnimationType.DRINK) {
                fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
                a(poseStack, arm, this.event.getAttackProgress());
                fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
            }
        }
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z")})
    private boolean labymod$cancelVanillaBlockRotation(gqj instance, Operation<Boolean> original) {
        return this.event.isUsingItem();
    }

    @WrapOperation(method = {"swingArm"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmAttackTransformPre(grg instance, fld poseStack, bxw arm, float attackProgress, Operation<Void> original) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            a(poseStack, arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")})
    private void labymod$renderItemPreRender(grg instance, byf livingEntity, dak itemStack, dai itemDisplayContext, fld poseStack, grn bufferSource, int packedLightCoords, Operation<Void> original) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            original.call(new Object[]{instance, livingEntity, itemStack, itemDisplayContext, poseStack, bufferSource, Integer.valueOf(packedLightCoords)});
        }
    }

    private void fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}

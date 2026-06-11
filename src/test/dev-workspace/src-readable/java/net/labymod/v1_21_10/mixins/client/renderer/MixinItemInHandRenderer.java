package net.labymod.v1_21_10.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinItemInHandRenderer.class */
@Mixin({hfo.class})
public abstract class MixinItemInHandRenderer {
    private RenderFirstPersonItemInHandEvent event;

    @Shadow
    protected abstract void b(fua fuaVar, cem cemVar, float f);

    @Shadow
    protected abstract void a(fua fuaVar, cem cemVar, float f);

    @Inject(method = {"renderArmWithItem"}, at = {@At("HEAD")})
    private void labymod$renderArmWithItemPre(hem player, float partialTicks, float yaw, cbp hand, float attackProgress, dhp itemStack, float equipProgress, fua poseStack, hgy collector, int packedLight, CallbackInfo callbackInfo) {
        boolean isMainHand = hand == cbp.a;
        cem humanoidArm = isMainHand ? player.as() : player.as().e();
        boolean isRightArm = humanoidArm == cem.b;
        boolean isUsingItem = player.fO() && player.fR() > 0 && player.fP() == hand;
        Player apiPlayer = (Player) player;
        this.event = new RenderFirstPersonItemInHandEvent(((VanillaStackAccessor) poseStack).stack(), apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.w()), partialTicks, equipProgress, attackProgress, isUsingItem);
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmTransformPre(hfo instance, fua poseStack, cem arm, float equipProgress, Operation<Void> original) {
        injectApplyItemArmTransform(instance, poseStack, arm, original);
    }

    @WrapOperation(method = {"swingArm"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmTransformAtSwingArmPre(hfo instance, fua poseStack, cem arm, float equipProgress, Operation<Void> original) {
        injectApplyItemArmTransform(instance, poseStack, arm, original);
    }

    private void injectApplyItemArmTransform(hfo instance, fua poseStack, cem arm, Operation<Void> original) {
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
    private boolean labymod$cancelVanillaBlockRotation(hem instance, Operation<Boolean> original) {
        return this.event.isUsingItem();
    }

    @WrapOperation(method = {"swingArm"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V")})
    private void labymod$applyItemArmAttackTransformPre(hfo instance, fua poseStack, cem arm, float attackProgress, Operation<Void> original) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            a(poseStack, arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }

    @WrapOperation(method = {"renderArmWithItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V")})
    private void labymod$renderItemPreRender(hfo instance, cew livingEntity, dhp itemStack, dhn itemDisplayContext, fua poseStack, hgy collector, int packedLightCoords, Operation<Void> original) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            original.call(new Object[]{instance, livingEntity, itemStack, itemDisplayContext, poseStack, collector, Integer.valueOf(packedLightCoords)});
        }
    }

    private void fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}

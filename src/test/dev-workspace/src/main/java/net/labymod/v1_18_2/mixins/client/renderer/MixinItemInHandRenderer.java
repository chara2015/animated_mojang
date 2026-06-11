package net.labymod.v1_18_2.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/MixinItemInHandRenderer.class */
@Mixin({eqo.class})
public abstract class MixinItemInHandRenderer {
    private RenderFirstPersonItemInHandEvent event;

    @Shadow
    protected abstract void b(dtm dtmVar, axt axtVar, float f);

    @Shadow
    protected abstract void a(dtm dtmVar, axt axtVar, float f);

    @Inject(method = {"renderArmWithItem"}, at = {@At("HEAD")})
    private void labymod$renderArmWithItemPre(ept player, float partialTicks, float yaw, awg hand, float attackProgress, buw itemStack, float equipProgress, dtm poseStack, eqs source, int packedLight, CallbackInfo callbackInfo) {
        boolean isMainHand = hand == awg.a;
        axt humanoidArm = isMainHand ? player.eL() : player.eL().a();
        boolean isRightArm = humanoidArm == axt.b;
        boolean isUsingItem = player.eM() && player.eP() > 0 && player.eN() == hand;
        Player apiPlayer = (Player) player;
        this.event = new RenderFirstPersonItemInHandEvent(((VanillaStackAccessor) poseStack).stack(source), apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.q()), partialTicks, equipProgress, attackProgress, isUsingItem);
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }

    @Redirect(method = {"renderArmWithItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    private void labymod$applyItemArmTransformPre(eqo renderer, dtm poseStack, axt arm, float equipProgress) {
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
    private boolean labymod$cancelVanillaBlockRotation(ept instance, Operation<Boolean> original) {
        return this.event.isUsingItem();
    }

    @Redirect(method = {"renderArmWithItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    private void labymod$applyItemArmAttackTransformPre(eqo renderer, dtm poseStack, axt arm, float attackProgress) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            a(poseStack, arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }

    @Redirect(method = {"renderArmWithItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void labymod$renderItemPreRender(eqo renderer, axy entity, buw itemStack, b type, boolean isLeft, dtm stack, eqs source, int packedLight) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            renderer.a(entity, itemStack, type, isLeft, stack, source, packedLight);
        }
    }

    private void fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}

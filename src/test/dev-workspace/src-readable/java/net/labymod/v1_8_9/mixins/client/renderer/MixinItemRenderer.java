package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinItemRenderer.class */
@Mixin({bfn.class})
public abstract class MixinItemRenderer {

    @Shadow
    @Final
    private ave c;

    @Shadow
    private zx d;

    @Shadow
    private float e;

    @Shadow
    private float f;
    private RenderFirstPersonItemInHandEvent event;

    @Unique
    private final Stack labymod$stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Unique
    private FloatVector3 labymod$offset = FloatVector3.ZERO;

    @Shadow
    protected abstract void d(float f);

    @Shadow
    protected abstract void b(float f, float f2);

    @Inject(method = {"renderPlayerArms"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderLeftArm(Lnet/minecraft/client/renderer/entity/RenderPlayer;)V", shift = At.Shift.BEFORE)})
    private void labyMod$rebindSkin(bet player, CallbackInfo ci) {
        this.c.P().a(player.i());
    }

    @Inject(method = {"renderItemInFirstPerson(F)V"}, at = {@At("HEAD")})
    private void labymod$renderItemInFirstPersonPre(float partialTicks, CallbackInfo callbackInfo) {
        if (this.d == null) {
            return;
        }
        Player player = this.c.h;
        float equipProgress = 1.0f - (this.f + ((this.e - this.f) * partialTicks));
        float swingProgress = player.l(partialTicks);
        boolean isUsingItem = player.bR() > 0;
        Player apiPlayer = player;
        this.event = new RenderFirstPersonItemInHandEvent(VersionedStackProvider.DEFAULT_STACK, apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), LivingEntity.Hand.MAIN_HAND, LivingEntity.HandSide.RIGHT, MinecraftUtil.fromMinecraft(this.d), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(this.d.m()), partialTicks, equipProgress, swingProgress, isUsingItem);
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }

    @Redirect(method = {"renderItemInFirstPerson(F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doItemUsedTransformations(F)V"))
    private void labymod$doItemUsedTransformationsPre(bfn itemRenderer, float partialTicks) {
        if (this.d == null) {
            return;
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            d(this.event.getAttackProgress());
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
    }

    @Redirect(method = {"renderItemInFirstPerson(F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"))
    private void labymod$transformFirstPersonItemPre(bfn itemRenderer, float equippedProgress, float attackProgress) {
        if (this.d == null) {
            return;
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            b(this.event.getEquipProgress(), this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }

    @Redirect(method = {"renderItemInFirstPerson(F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I"))
    private int labymod$cancelVanillaBlockRotation(bet player) {
        return this.event.isUsingItem() ? 1 : 0;
    }

    @Redirect(method = {"renderItemInFirstPerson(F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void labymod$renderItemPreRender(bfn itemRenderer, pr entity, zx itemStack, b type) {
        if (this.d == null) {
            return;
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            itemRenderer.a(entity, itemStack, type);
        }
    }

    @Inject(method = {"renderOverlays(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$getViewBlockingStateHeadSetup(float partialTicks, CallbackInfo ci) {
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f);
        this.labymod$stack.pop();
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D"))
    private double labyMod$getViewBlockingStateX(wn instance) {
        return instance.s - ((double) this.labymod$offset.getX());
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D"))
    private double labyMod$getViewBlockingStateY(wn instance) {
        return instance.t - ((double) this.labymod$offset.getY());
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D"))
    private double labyMod$getViewBlockingStateZ(wn instance) {
        return instance.u - ((double) this.labymod$offset.getZ());
    }

    private void fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}

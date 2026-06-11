package net.labymod.v1_12_2.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinItemRenderer.class */
@Mixin({buu.class})
public abstract class MixinItemRenderer {
    private RenderFirstPersonItemInHandEvent event;

    @Unique
    private final Stack labymod$stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Unique
    private FloatVector3 labymod$offset = FloatVector3.ZERO;

    @Shadow
    protected abstract void b(vo voVar, float f);

    @Shadow
    protected abstract void a(vo voVar, float f);

    @Inject(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = {@At("HEAD")})
    private void labymod$renderArmWithItemPre(bua player, float partialTicks, float yaw, ub hand, float attackProgress, aip itemStack, float equipProgress, CallbackInfo callbackInfo) {
        vo voVarA;
        boolean isMainHand = hand == ub.a;
        if (isMainHand) {
            voVarA = player.cF();
        } else {
            voVarA = player.cF().a();
        }
        vo humanoidArm = voVarA;
        boolean isRightArm = humanoidArm == vo.b;
        boolean isUsingItem = player.cG() && player.cK() > 0 && player.cH() == hand;
        Player apiPlayer = (Player) player;
        this.event = new RenderFirstPersonItemInHandEvent(VersionedStackProvider.DEFAULT_STACK, apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.n()), partialTicks, equipProgress, attackProgress, isUsingItem);
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }

    @Redirect(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformSideFirstPerson(Lnet/minecraft/util/EnumHandSide;F)V"))
    private void labymod$transformSideFirstPersonPre(buu itemRenderer, vo arm, float equipProgress) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            b(arm, this.event.getEquipProgress());
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
    }

    @Redirect(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPerson(Lnet/minecraft/util/EnumHandSide;F)V"))
    private void labymod$transformFirstPersonPre(buu itemRenderer, vo arm, float attackProgress) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            a(arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }

    @WrapOperation(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isHandActive()Z")})
    private boolean labymod$cancelVanillaBlockRotation(bua instance, Operation<Boolean> original) {
        return this.event.isUsingItem();
    }

    @Redirect(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"))
    private void labymod$renderItemSidePreRender(buu itemRenderer, vp entity, aip itemStack, b type, boolean isLeft) {
        fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            itemRenderer.a(entity, itemStack, type, isLeft);
        }
    }

    @Inject(method = {"renderOverlays(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$getViewBlockingStateHeadSetup(float partialTicks, CallbackInfo ci) {
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f);
        this.labymod$stack.pop();
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D"))
    private double labyMod$getViewBlockingStateX(aed instance) {
        return instance.p - ((double) this.labymod$offset.getX());
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D"))
    private double labyMod$getViewBlockingStateY(aed instance) {
        return instance.q - ((double) this.labymod$offset.getY());
    }

    @Redirect(method = {"renderOverlays(F)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D"))
    private double labyMod$getViewBlockingStateZ(aed instance) {
        return instance.r - ((double) this.labymod$offset.getZ());
    }

    private void fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}

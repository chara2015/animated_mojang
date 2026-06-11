package net.labymod.v1_21_11.mixins.client.model;

import chl;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/model/MixinHumanoidModelEvents.class */
@Mixin({gzo.class})
public class MixinHumanoidModelEvents<T extends chl> {
    public a storedRightArmPose;
    public a storedLeftArmPose;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At("HEAD")})
    protected void labyMod$preSetupAnim(idy state, CallbackInfo ci) {
        chl chlVarLabyMod$getEntity;
        EntityRenderStateAccessor<chl> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (chlVarLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) chlVarLabyMod$getEntity, (HumanoidModel) this, Phase.PRE));
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;mainArm:Lnet/minecraft/world/entity/HumanoidArm;")})
    private void labyMod$prePose(idy state, CallbackInfo ci, @Local(ordinal = 0) LocalRef<a> leftArmPose, @Local(ordinal = 1) LocalRef<a> rightArmPose) {
        chl chlVarLabyMod$getEntity;
        EntityRenderStateAccessor<chl> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (chlVarLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        this.storedLeftArmPose = (a) leftArmPose.get();
        this.storedRightArmPose = (a) rightArmPose.get();
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) chlVarLabyMod$getEntity, LivingEntity.HandSide.LEFT))).isCancelled()) {
            leftArmPose.set(a.a);
        }
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) chlVarLabyMod$getEntity, LivingEntity.HandSide.RIGHT))).isCancelled()) {
            rightArmPose.set(a.a);
        }
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", shift = At.Shift.BEFORE)})
    private void labyMod$postPose(idy state, CallbackInfo ci, @Local(ordinal = 0) LocalRef<a> leftArmPose, @Local(ordinal = 1) LocalRef<a> rightArmPose) {
        chl chlVarLabyMod$getEntity;
        EntityRenderStateAccessor<chl> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (chlVarLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        leftArmPose.set(this.storedLeftArmPose);
        rightArmPose.set(this.storedRightArmPose);
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) chlVarLabyMod$getEntity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) chlVarLabyMod$getEntity, LivingEntity.HandSide.RIGHT));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At("TAIL")})
    protected void labyMod$postSetupAnim(idy state, CallbackInfo ci) {
        chl chlVarLabyMod$getEntity;
        EntityRenderStateAccessor<chl> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (chlVarLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) chlVarLabyMod$getEntity, (HumanoidModel) this, Phase.POST));
    }
}

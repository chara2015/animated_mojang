package net.labymod.v26_2_snapshot_8.mixins.client.model;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.v26_2_snapshot_8.client.util.EntityRenderStateAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/model/MixinHumanoidModelEvents.class */
@Mixin({HumanoidModel.class})
public class MixinHumanoidModelEvents<T extends LivingEntity> {
    public HumanoidModel.ArmPose storedRightArmPose;
    public HumanoidModel.ArmPose storedLeftArmPose;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At("HEAD")})
    protected void labyMod$preSetupAnim(HumanoidRenderState state, CallbackInfo ci) {
        LivingEntity livingEntityLabyMod$getEntity;
        EntityRenderStateAccessor<LivingEntity> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (livingEntityLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, (net.labymod.api.client.render.model.entity.HumanoidModel) this, Phase.PRE));
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;mainArm:Lnet/minecraft/world/entity/HumanoidArm;")})
    private void labyMod$prePose(HumanoidRenderState state, CallbackInfo ci, @Local(ordinal = 0) LocalRef<HumanoidModel.ArmPose> leftArmPose, @Local(ordinal = 1) LocalRef<HumanoidModel.ArmPose> rightArmPose) {
        LivingEntity livingEntityLabyMod$getEntity;
        EntityRenderStateAccessor<LivingEntity> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (livingEntityLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        this.storedLeftArmPose = (HumanoidModel.ArmPose) leftArmPose.get();
        this.storedRightArmPose = (HumanoidModel.ArmPose) rightArmPose.get();
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, LivingEntity.HandSide.LEFT))).isCancelled()) {
            leftArmPose.set(HumanoidModel.ArmPose.EMPTY);
        }
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, LivingEntity.HandSide.RIGHT))).isCancelled()) {
            rightArmPose.set(HumanoidModel.ArmPose.EMPTY);
        }
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", shift = At.Shift.BEFORE)})
    private void labyMod$postPose(HumanoidRenderState state, CallbackInfo ci, @Local(ordinal = 0) LocalRef<HumanoidModel.ArmPose> leftArmPose, @Local(ordinal = 1) LocalRef<HumanoidModel.ArmPose> rightArmPose) {
        net.minecraft.world.entity.LivingEntity livingEntityLabyMod$getEntity;
        EntityRenderStateAccessor<net.minecraft.world.entity.LivingEntity> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (livingEntityLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        leftArmPose.set(this.storedLeftArmPose);
        rightArmPose.set(this.storedRightArmPose);
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, LivingEntity.HandSide.RIGHT));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"}, at = {@At("TAIL")})
    protected void labyMod$postSetupAnim(HumanoidRenderState state, CallbackInfo ci) {
        net.minecraft.world.entity.LivingEntity livingEntityLabyMod$getEntity;
        EntityRenderStateAccessor<net.minecraft.world.entity.LivingEntity> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null || (livingEntityLabyMod$getEntity = livingEntityState.labyMod$getEntity()) == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((net.labymod.api.client.entity.LivingEntity) livingEntityLabyMod$getEntity, (net.labymod.api.client.render.model.entity.HumanoidModel) this, Phase.POST));
    }
}

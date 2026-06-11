package net.labymod.v1_21.mixins.client.model;

import btn;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/model/MixinHumanoidModelEvents.class */
@Mixin({fvx.class})
public class MixinHumanoidModelEvents<T extends btn> {

    @Shadow
    public a r;

    @Shadow
    public a s;
    public a storedRightArmPose;
    public a storedLeftArmPose;

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at = @At("HEAD"))
    protected void labyMod$preSetupAnim(T entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.PRE));
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainArm()Lnet/minecraft/world/entity/HumanoidArm;", ordinal = 0, shift = At.Shift.BEFORE)})
    private void labyMod$prePose(T entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, CallbackInfo callbackInfo) {
        this.storedLeftArmPose = this.r;
        this.storedRightArmPose = this.s;
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.LEFT))).isCancelled()) {
            this.r = a.a;
        }
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.RIGHT))).isCancelled()) {
            this.s = a.a;
        }
    }

    @Inject(method = {"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V", ordinal = 0, shift = At.Shift.BEFORE)})
    private void labyMod$postPose(T entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, CallbackInfo callbackInfo) {
        this.r = this.storedLeftArmPose;
        this.s = this.storedRightArmPose;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.RIGHT));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at = @At("TAIL"))
    protected void labyMod$postSetupAnim(T entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.POST));
    }
}

package net.labymod.v1_12_2.mixins.client.model;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/model/MixinModelBipedEvents.class */
@Mixin({bpx.class})
public class MixinModelBipedEvents {

    @Shadow
    public a l;

    @Shadow
    public a m;
    public a storedRightArmPose;
    public a storedLeftArmPose;

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setRotationAngles"}, at = @At("HEAD"))
    protected void labyMod$preSetRotationAngles(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, vg entity, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.PRE));
    }

    @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;leftArmPose:Lnet/minecraft/client/model/ModelBiped$ArmPose;", ordinal = 0, shift = At.Shift.BEFORE)})
    private void prePose(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, vg entity, CallbackInfo callbackInfo) {
        this.storedLeftArmPose = this.l;
        this.storedRightArmPose = this.m;
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.LEFT))).isCancelled()) {
            this.l = a.a;
        }
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.RIGHT))).isCancelled()) {
            this.m = a.a;
        }
    }

    @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F", ordinal = 0, shift = At.Shift.BEFORE)})
    private void postPose(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, vg entity, CallbackInfo callbackInfo) {
        this.l = this.storedLeftArmPose;
        this.m = this.storedRightArmPose;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.RIGHT));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setRotationAngles"}, at = @At("TAIL"))
    protected void labyMod$postSetRotationAngles(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, vg entity, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.POST));
    }
}

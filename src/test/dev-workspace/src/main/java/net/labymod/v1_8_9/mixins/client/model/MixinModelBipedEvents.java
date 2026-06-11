package net.labymod.v1_8_9.mixins.client.model;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/model/MixinModelBipedEvents.class */
@Mixin({bbj.class})
public class MixinModelBipedEvents {

    @Shadow
    public int l;

    @Shadow
    public int m;
    public int storedHeldItemLeft;
    public int storedHeldItemRight;

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setRotationAngles"}, at = @At("HEAD"))
    protected void labyMod$preSetRotationAngles(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, pk entity, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.PRE));
    }

    @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;heldItemLeft:I", ordinal = 0, shift = At.Shift.BEFORE)})
    private void prePose(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, pk entity, CallbackInfo callbackInfo) {
        this.storedHeldItemLeft = this.l;
        this.storedHeldItemRight = this.m;
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.LEFT))).isCancelled()) {
            this.l = 0;
        }
        if (((HumanoidModelPoseAnimationEvent) Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity) entity, LivingEntity.HandSide.RIGHT))).isCancelled()) {
            this.m = 0;
        }
    }

    @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F", ordinal = 0, shift = At.Shift.BEFORE)})
    private void postPose(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, pk entity, CallbackInfo callbackInfo) {
        this.l = this.storedHeldItemLeft;
        this.m = this.storedHeldItemRight;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity) entity, LivingEntity.HandSide.RIGHT));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Insert(method = {"setRotationAngles"}, at = @At("TAIL"))
    protected void labyMod$postSetRotationAngles(float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, pk entity, InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity) entity, (HumanoidModel) this, Phase.POST));
    }
}

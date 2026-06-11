package net.labymod.v1_19_4.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/renderer/MixinGameRendererCamera.class */
@Mixin({fhz.class})
public class MixinGameRendererCamera {
    @Inject(method = {"renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = {@At("HEAD")})
    public void labyMod$preSetupCamera(float param0, long param1, ehe stack, CallbackInfo callbackInfo) {
        labyMod$fireCameraSetupEvent(stack, Phase.PRE);
    }

    @Inject(method = {"renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;prepareCullFrustum(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Matrix4f;)V", shift = At.Shift.BEFORE)})
    public void labyMod$postSetupCamera(float param0, long param1, ehe stack, CallbackInfo callbackInfo) {
        labyMod$fireCameraSetupEvent(stack, Phase.POST);
    }

    private void labyMod$fireCameraSetupEvent(ehe poseStack, Phase phase) {
        Laby.fireEvent(new CameraSetupEvent(((VanillaStackAccessor) poseStack).stack(), phase));
    }
}

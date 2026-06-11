package net.labymod.v1_21_10.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinGameRenderer.class */
@Mixin({hfk.class})
public abstract class MixinGameRenderer {
    @WrapOperation(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotation(Lorg/joml/Quaternionfc;)Lorg/joml/Matrix4f;", remap = false)})
    private Matrix4f labyMod$setViewMatrix(Matrix4f self, Quaternionfc quaternion, Operation<Matrix4f> original, fzp deltaTracker) {
        ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera) Laby.labyAPI().minecraft().getCamera();
        Matrix4f self2 = (Matrix4f) original.call(new Object[]{self, quaternion});
        if (camera != null) {
            float partialTicks = deltaTracker.a(false);
            float eyeHeight = camera.getEyeHeight(partialTicks);
            CameraEyeHeightEvent event = (CameraEyeHeightEvent) Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
            self2.translate(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
        }
        return MinecraftUtil.setViewMatrix(self2);
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$firePreRenderHandEvent(float $$0, boolean $$1, Matrix4f $$2, CallbackInfo ci) {
        RenderHandEvent event = RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("TAIL")})
    private void labyMod$firePostRenderHandEvent(float $$0, boolean $$1, Matrix4f $$2, CallbackInfo ci) {
        RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.POST);
    }

    @WrapWithCondition(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V")})
    private boolean labyMod$noViewBobbing(hfk instance, fua stack, float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get().booleanValue();
    }
}

package net.labymod.v1_20_6.mixins.client.renderer;

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
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinGameRenderer.class */
@Mixin({gdj.class})
public abstract class MixinGameRenderer {
    @WrapOperation(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotationXYZ(FFF)Lorg/joml/Matrix4f;")})
    private Matrix4f labyMod$setViewMatrix(Matrix4f self, float angleX, float angleY, float angleZ, Operation<Matrix4f> original, float partialTicks) {
        ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera) Laby.labyAPI().minecraft().getCamera();
        Matrix4f self2 = (Matrix4f) original.call(new Object[]{self, Float.valueOf(angleX), Float.valueOf(angleY), Float.valueOf(angleZ)});
        if (camera != null) {
            float eyeHeight = camera.getEyeHeight(partialTicks);
            CameraEyeHeightEvent event = (CameraEyeHeightEvent) Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
            self2.translate(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
        }
        return MinecraftUtil.setViewMatrix(self2);
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$firePreRenderHandEvent(fes camera, float partialTicks, Matrix4f viewMatrix, CallbackInfo ci) {
        RenderHandEvent event = RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("TAIL")})
    private void labyMod$firePostRenderHandEvent(fes camera, float partialTicks, Matrix4f viewMatrix, CallbackInfo ci) {
        RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.POST);
    }

    @WrapWithCondition(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V")})
    private boolean labyMod$noViewBobbing(gdj instance, faa stack, float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get().booleanValue();
    }
}

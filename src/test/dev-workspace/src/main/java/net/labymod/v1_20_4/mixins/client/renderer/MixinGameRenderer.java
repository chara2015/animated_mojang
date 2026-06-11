package net.labymod.v1_20_4.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/MixinGameRenderer.class */
@Mixin({fta.class})
public abstract class MixinGameRenderer {

    @Shadow
    @Final
    private evi k;

    @Inject(method = {"renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V", shift = At.Shift.BEFORE)})
    private void labyMod$fireCameraEyeHeight(float partialTicks, long param, eqb stack, CallbackInfo ci) {
        ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera) Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        float eyeHeight = camera.getEyeHeight(partialTicks);
        CameraEyeHeightEvent event = (CameraEyeHeightEvent) Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        stack.a(0.0d, eyeHeight - event.getEyeHeight(), 0.0d);
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$firePreRenderHandEvent(eqb stack, eut camera, float partialTicks, CallbackInfo ci) {
        RenderHandEvent event = RenderHandEventCaller.call(((VanillaStackAccessor) stack).stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("TAIL")})
    private void labyMod$firePostRenderHandEvent(eqb stack, eut camera, float partialTicks, CallbackInfo ci) {
        RenderHandEventCaller.call(((VanillaStackAccessor) stack).stack(), Phase.POST);
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z"))
    private boolean labyMod$fireHiddenOverlayRenderEvent(evm options) {
        boolean hideGui = options.Z;
        if (hideGui && this.k.y == null) {
            Stack stack = new eqb().stack();
            Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
                Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, true));
                Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, true));
            });
        }
        return hideGui;
    }

    @WrapWithCondition(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V")})
    private boolean labyMod$noViewBobbing(fta instance, eqb stack, float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get().booleanValue();
    }
}

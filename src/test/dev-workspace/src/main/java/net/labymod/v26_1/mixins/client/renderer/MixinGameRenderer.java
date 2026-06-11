package net.labymod.v26_1.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.core.main.LabyMod;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/renderer/MixinGameRenderer.class */
@Mixin({GameRenderer.class})
public abstract class MixinGameRenderer {
    @Inject(method = {"renderItemInHand"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$firePreRenderHandEvent(CallbackInfo ci) {
        RenderHandEvent event = RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderItemInHand"}, at = {@At("TAIL")})
    private void labyMod$firePostRenderHandEvent(CallbackInfo ci) {
        RenderHandEventCaller.call(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Phase.POST);
    }

    @WrapWithCondition(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V")})
    private boolean labyMod$noViewBobbing(GameRenderer instance, CameraRenderState cameraRenderState, PoseStack poseStack) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get().booleanValue();
    }
}

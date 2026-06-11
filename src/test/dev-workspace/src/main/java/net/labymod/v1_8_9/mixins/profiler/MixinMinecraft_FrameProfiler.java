package net.labymod.v1_8_9.mixins.profiler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.profiler.frame.FrameProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/profiler/MixinMinecraft_FrameProfiler.class */
@Mixin({ave.class})
public class MixinMinecraft_FrameProfiler {
    @WrapOperation(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;updateCameraAndRender(FJ)V")})
    private void labyMod$frameProfiler$render(bfk instance, float lvt_1_1_, long lvt_2_1_, Operation<Void> original) {
        FrameProfiler.push("render");
        original.call(new Object[]{instance, Float.valueOf(lvt_1_1_), Long.valueOf(lvt_2_1_)});
        FrameProfiler.pop();
    }

    @WrapOperation(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;runTick()V")})
    private void labyMod$frameProfiler$tick(ave instance, Operation<Void> original) {
        FrameProfiler.push("tick");
        original.call(new Object[]{instance});
        FrameProfiler.pop();
    }
}

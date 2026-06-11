package net.labymod.v1_17_1.mixins.profiler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.profiler.frame.FrameProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/profiler/MixinMinecraft_FrameProfiler.class */
@Mixin({dvp.class})
public class MixinMinecraft_FrameProfiler {
    @WrapOperation(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V")})
    private void labyMod$frameProfiler$render(enb instance, float v, long l, boolean b, Operation<Void> original) {
        FrameProfiler.push("render");
        original.call(new Object[]{instance, Float.valueOf(v), Long.valueOf(l), Boolean.valueOf(b)});
        FrameProfiler.pop();
    }

    @WrapOperation(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;tick()V")})
    private void labyMod$frameProfiler$tick(dvp instance, Operation<Void> original) {
        FrameProfiler.push("tick");
        original.call(new Object[]{instance});
        FrameProfiler.pop();
    }
}

package net.labymod.v26_1_1.mixins.profiler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/profiler/MixinMinecraft_FrameProfiler.class */
@Mixin({Minecraft.class})
public class MixinMinecraft_FrameProfiler {
    @WrapOperation(method = {"renderFrame"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V")})
    private void labyMod$frameProfiler$render(GameRenderer instance, DeltaTracker $$0, boolean $$1, Operation<Void> original) {
        FrameProfiler.push("render");
        original.call(new Object[]{instance, $$0, Boolean.valueOf($$1)});
        FrameProfiler.pop();
    }

    @WrapOperation(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;tick()V")})
    private void labyMod$frameProfiler$tick(Minecraft instance, Operation<Void> original) {
        FrameProfiler.push("tick");
        original.call(new Object[]{instance});
        FrameProfiler.pop();
    }
}

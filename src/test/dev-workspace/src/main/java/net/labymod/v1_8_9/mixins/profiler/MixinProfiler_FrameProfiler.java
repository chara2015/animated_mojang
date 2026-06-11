package net.labymod.v1_8_9.mixins.profiler;

import net.labymod.api.profiler.frame.FrameProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/profiler/MixinProfiler_FrameProfiler.class */
@Mixin({nt.class})
public class MixinProfiler_FrameProfiler {
    @Inject(method = {"startSection"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$push(String name, CallbackInfo ci) {
        FrameProfiler.push(name);
    }

    @Inject(method = {"endStartSection"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$popPush(String name, CallbackInfo ci) {
        FrameProfiler.swap(name);
    }

    @Inject(method = {"endSection"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$pop(CallbackInfo ci) {
        FrameProfiler.pop();
    }
}

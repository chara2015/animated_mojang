package net.labymod.v1_19_4.mixins.profiler;

import java.util.function.Supplier;
import net.labymod.api.profiler.frame.FrameProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/profiler/MixinProfiler_FrameProfiler.class */
@Mixin({bah.class, bal.class})
public class MixinProfiler_FrameProfiler {
    @Inject(method = {"push(Ljava/lang/String;)V"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$push(String name, CallbackInfo ci) {
        FrameProfiler.push(name);
    }

    @Inject(method = {"push(Ljava/util/function/Supplier;)V"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$pushSupplier(Supplier<String> supplier, CallbackInfo ci) {
        FrameProfiler.push(supplier);
    }

    @Inject(method = {"popPush(Ljava/lang/String;)V"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$popPush(String name, CallbackInfo ci) {
        FrameProfiler.swap(name);
    }

    @Inject(method = {"popPush(Ljava/util/function/Supplier;)V"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$popPushSupplier(Supplier<String> supplier, CallbackInfo ci) {
        FrameProfiler.swap(supplier);
    }

    @Inject(method = {"pop"}, at = {@At("HEAD")})
    private void labyMod$frameProfiler$pop(CallbackInfo ci) {
        FrameProfiler.pop();
    }
}

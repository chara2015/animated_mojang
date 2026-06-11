package net.labymod.v1_21_10.mixins.watchdog;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.core.main.util.RenderThreadWatchdog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/watchdog/MixinMinecraft_Watchdog.class */
@Mixin({fzz.class})
public class MixinMinecraft_Watchdog {
    @Inject(method = {"run"}, at = {@At("HEAD")})
    private void labyMod$initializeWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.initialize(RenderSystem.renderThread);
    }

    @Inject(method = {"run"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;handleDelayedCrash()V")})
    private void labyMod$sendHeartbeat(CallbackInfo ci) {
        RenderThreadWatchdog.heartbeat();
    }

    @Inject(method = {"close"}, at = {@At("TAIL")})
    private void labyMod$shutdownWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.shutdown();
    }
}

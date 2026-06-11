package net.labymod.v1_12_2.mixins.watchdog;

import net.labymod.core.main.util.RenderThreadWatchdog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/watchdog/MixinMinecraft_Watchdog.class */
@Mixin({bib.class})
public class MixinMinecraft_Watchdog {
    @Inject(method = {"run"}, at = {@At("HEAD")})
    private void labyMod$initializeWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.initialize(Thread.currentThread());
    }

    @Inject(method = {"run"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;hasCrashed:Z", opcode = 180, ordinal = 0)})
    private void labyMod$sendHeartbeat(CallbackInfo ci) {
        RenderThreadWatchdog.heartbeat();
    }

    @Inject(method = {"shutdownMinecraftApplet"}, at = {@At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V")})
    private void labyMod$shutdownWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.shutdown();
    }
}

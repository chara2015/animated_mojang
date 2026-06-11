package net.labymod.v26_1_1.mixins.watchdog;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.core.main.util.RenderThreadWatchdog;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/watchdog/MixinMinecraft_Watchdog.class */
@Mixin({Minecraft.class})
public class MixinMinecraft_Watchdog {
    @Inject(method = {"run"}, at = {@At("HEAD")})
    private void labyMod$initializeWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.initialize(RenderSystem.renderThread);
    }

    @Inject(method = {"run"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/SingleTickProfiler;createTickProfiler(Ljava/lang/String;)Lnet/minecraft/util/profiling/SingleTickProfiler;")})
    private void labyMod$sendHeartbeat(CallbackInfo ci) {
        RenderThreadWatchdog.heartbeat();
    }

    @Inject(method = {"close"}, at = {@At("TAIL")})
    private void labyMod$shutdownWatchdog(CallbackInfo ci) {
        RenderThreadWatchdog.shutdown();
    }
}

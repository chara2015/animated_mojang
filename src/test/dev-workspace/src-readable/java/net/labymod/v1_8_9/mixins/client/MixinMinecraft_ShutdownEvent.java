package net.labymod.v1_8_9.mixins.client;

import net.labymod.core.event.client.lifecycle.ShutdownEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinMinecraft_ShutdownEvent.class */
@Mixin({ave.class})
public class MixinMinecraft_ShutdownEvent {
    @Inject(method = {"shutdownMinecraftApplet"}, at = {@At("HEAD")})
    private void labyMod$destroyPre(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPreEvent();
    }

    @Inject(method = {"shutdownMinecraftApplet"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;unloadSounds()V")})
    private void labyMod$destroyPost(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPostEvent();
    }
}

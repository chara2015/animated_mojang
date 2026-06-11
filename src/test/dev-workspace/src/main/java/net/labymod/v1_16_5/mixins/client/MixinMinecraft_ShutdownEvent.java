package net.labymod.v1_16_5.mixins.client;

import net.labymod.core.event.client.lifecycle.ShutdownEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinMinecraft_ShutdownEvent.class */
@Mixin({djz.class})
public class MixinMinecraft_ShutdownEvent {
    @Inject(method = {"destroy"}, at = {@At("HEAD")})
    private void labyMod$destroyPre(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPreEvent();
    }

    @Inject(method = {"destroy"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;close()V")})
    private void labyMod$destroyPost(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPostEvent();
    }
}

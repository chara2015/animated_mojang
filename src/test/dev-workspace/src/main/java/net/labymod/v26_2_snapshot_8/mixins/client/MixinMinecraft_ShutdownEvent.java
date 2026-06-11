package net.labymod.v26_2_snapshot_8.mixins.client;

import net.labymod.core.event.client.lifecycle.ShutdownEventCaller;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinMinecraft_ShutdownEvent.class */
@Mixin({Minecraft.class})
public class MixinMinecraft_ShutdownEvent {
    @Inject(method = {"exitWorldAndClose"}, at = {@At("HEAD")})
    private void labyMod$destroyPre(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPreEvent();
    }

    @Inject(method = {"exitWorldAndClose"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;close()V")})
    private void labyMod$destroyPost(CallbackInfo ci) {
        ShutdownEventCaller.callShutdownPostEvent();
    }
}

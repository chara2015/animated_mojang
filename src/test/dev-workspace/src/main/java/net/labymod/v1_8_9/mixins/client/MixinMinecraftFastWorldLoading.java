package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinMinecraftFastWorldLoading.class */
@Mixin({ave.class})
public class MixinMinecraftFastWorldLoading {
    @Redirect(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V"))
    private void labyMod$respawnCode() {
        if (Laby.labyAPI().config().ingame().fastWorldLoading().get().booleanValue()) {
            return;
        }
        System.gc();
    }
}

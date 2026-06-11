package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MinecraftMinecraftThreadSafe.class */
@Mixin({ave.class})
public class MinecraftMinecraftThreadSafe {
    @Inject(method = {"displayGuiScreen"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$setScreenThreadSafe(axu screen, CallbackInfo ci) {
        if (!ThreadSafe.isRenderThread()) {
            ThreadSafe.executeOnRenderThread(() -> {
                ave.A().a(screen);
            });
            ci.cancel();
        }
    }
}

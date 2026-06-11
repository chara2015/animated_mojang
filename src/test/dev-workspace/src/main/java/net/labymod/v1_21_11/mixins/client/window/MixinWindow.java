package net.labymod.v1_21_11.mixins.client.window;

import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/window/MixinWindow.class */
@Mixin({fyk.class})
public abstract class MixinWindow {
    @Inject(method = {"setErrorSection"}, at = {@At("TAIL")})
    private void labyMod$firePostStartupInitializeEvent(String errorSection, CallbackInfo ci) {
        if (!errorSection.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }
}

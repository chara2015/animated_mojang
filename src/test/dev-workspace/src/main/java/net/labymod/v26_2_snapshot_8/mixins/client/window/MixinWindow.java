package net.labymod.v26_2_snapshot_8.mixins.client.window;

import com.mojang.blaze3d.platform.Window;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v26_2_snapshot_8.client.window.WindowAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/window/MixinWindow.class */
@Mixin({Window.class})
public abstract class MixinWindow implements WindowAccessor {
    @Inject(method = {"setErrorSection"}, at = {@At("TAIL")})
    private void labyMod$firePostStartupInitializeEvent(String errorSection, CallbackInfo ci) {
        if (!errorSection.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }

    @Override // net.labymod.v26_2_snapshot_8.client.window.WindowAccessor
    public void labyMod$dirtyResize() {
    }
}

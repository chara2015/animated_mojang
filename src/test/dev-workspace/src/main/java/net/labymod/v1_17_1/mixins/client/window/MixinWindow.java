package net.labymod.v1_17_1.mixins.client.window;

import java.io.InputStream;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/window/MixinWindow.class */
@Mixin({dpr.class})
public abstract class MixinWindow {
    @Inject(method = {"setErrorSection"}, at = {@At("TAIL")})
    private void labyMod$firePostStartupInitializeEvent(String errorSection, CallbackInfo ci) {
        if (!errorSection.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }

    @Inject(method = {"setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$disableMacOSIcon(InputStream first, InputStream second, CallbackInfo ci) {
        if (OperatingSystem.isOSX()) {
            IOUtil.closeSilent(first);
            IOUtil.closeSilent(second);
            ci.cancel();
        }
    }
}

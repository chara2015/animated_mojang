package net.labymod.v1_8_9.mixins.sentry;

import io.sentry.Sentry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/sentry/MixinMinecraftSentry.class */
@Mixin({ave.class})
public class MixinMinecraftSentry {
    @Inject(method = {"displayCrashReport"}, at = {@At("HEAD")})
    private void labyMod$captureException(b report, CallbackInfo ci) {
        Sentry.captureException(report.b());
    }
}

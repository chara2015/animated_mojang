package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinCrashReport.class */
@Mixin({b.class})
public class MixinCrashReport {
    private CrashReportAppenderIterable labyMod$crashReportAppenderIterable;

    @Inject(method = {"getSectionsInStringBuilder"}, at = {@At("TAIL")})
    private void labyMod$appendCrashReport(StringBuilder builder, CallbackInfo ci) {
        CrashReportAppenderIterable crashReportAppenderIterable = labyMod$getCrashReportAppenderRegistry();
        if (!(crashReportAppenderIterable instanceof DefaultCrashReportAppenderIterable)) {
            return;
        }
        ((DefaultCrashReportAppenderIterable) crashReportAppenderIterable).append(builder);
    }

    private CrashReportAppenderIterable labyMod$getCrashReportAppenderRegistry() {
        if (this.labyMod$crashReportAppenderIterable == null) {
            this.labyMod$crashReportAppenderIterable = Laby.references().crashReportAppenderIterable();
        }
        return this.labyMod$crashReportAppenderIterable;
    }
}

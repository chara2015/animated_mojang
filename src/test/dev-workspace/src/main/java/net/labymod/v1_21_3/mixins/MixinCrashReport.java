package net.labymod.v1_21_3.mixins;

import net.labymod.api.Laby;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/MixinCrashReport.class */
@Mixin({o.class})
public class MixinCrashReport {
    private CrashReportAppenderIterable labyMod$crashReportAppenderIterable;

    @Inject(method = {"getDetails(Ljava/lang/StringBuilder;)V"}, at = {@At("TAIL")})
    private void labyMod$appendCrashReport(StringBuilder builder, CallbackInfo ci) {
        CrashReportAppenderIterable crashReportAppenderRegistry = labyMod$getCrashReportAppenderRegistry();
        if (!(crashReportAppenderRegistry instanceof DefaultCrashReportAppenderIterable)) {
            return;
        }
        DefaultCrashReportAppenderIterable appenderRegistry = (DefaultCrashReportAppenderIterable) crashReportAppenderRegistry;
        appenderRegistry.append(builder);
    }

    private CrashReportAppenderIterable labyMod$getCrashReportAppenderRegistry() {
        if (this.labyMod$crashReportAppenderIterable == null) {
            this.labyMod$crashReportAppenderIterable = Laby.references().crashReportAppenderIterable();
        }
        return this.labyMod$crashReportAppenderIterable;
    }
}

package net.labymod.v1_21_11.mixins;

import net.labymod.api.Laby;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import net.minecraft.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/MixinCrashReport.class */
@Mixin({CrashReport.class})
public class MixinCrashReport {
    private CrashReportAppenderIterable labyMod$crashReportAppenderIterable;

    @Inject(method = {"getDetails(Ljava/lang/StringBuilder;)V"}, at = {@At("TAIL")})
    private void labyMod$appendCrashReport(StringBuilder builder, CallbackInfo ci) {
        DefaultCrashReportAppenderIterable defaultCrashReportAppenderIterableLabyMod$getCrashReportAppenderRegistry = labyMod$getCrashReportAppenderRegistry();
        if (!(defaultCrashReportAppenderIterableLabyMod$getCrashReportAppenderRegistry instanceof DefaultCrashReportAppenderIterable)) {
            return;
        }
        DefaultCrashReportAppenderIterable appenderRegistry = defaultCrashReportAppenderIterableLabyMod$getCrashReportAppenderRegistry;
        appenderRegistry.append(builder);
    }

    private CrashReportAppenderIterable labyMod$getCrashReportAppenderRegistry() {
        if (this.labyMod$crashReportAppenderIterable == null) {
            this.labyMod$crashReportAppenderIterable = Laby.references().crashReportAppenderIterable();
        }
        return this.labyMod$crashReportAppenderIterable;
    }
}

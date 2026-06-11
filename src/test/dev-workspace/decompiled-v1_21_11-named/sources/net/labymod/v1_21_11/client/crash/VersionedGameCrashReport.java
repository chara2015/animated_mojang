package net.labymod.v1_21_11.client.crash;

import net.labymod.api.client.crash.GameCrashReport;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/crash/VersionedGameCrashReport.class */
public class VersionedGameCrashReport implements GameCrashReport {
    private final CrashReport crashReport;

    public VersionedGameCrashReport(String title, Throwable throwable) {
        this.crashReport = CrashReport.forThrowable(throwable, title);
    }

    public GameCrashReport.Category addCategory(String name) {
        CrashReportCategory category = this.crashReport.addCategory(name);
        return new VersionedGameCrashReportCategory(category);
    }

    public <T> T crashReport() {
        return (T) this.crashReport;
    }
}

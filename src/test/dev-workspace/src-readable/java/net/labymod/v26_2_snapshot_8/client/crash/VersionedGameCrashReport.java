package net.labymod.v26_2_snapshot_8.client.crash;

import net.labymod.api.client.crash.GameCrashReport;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/crash/VersionedGameCrashReport.class */
public class VersionedGameCrashReport implements GameCrashReport {
    private final CrashReport crashReport;

    public VersionedGameCrashReport(String title, Throwable throwable) {
        this.crashReport = CrashReport.forThrowable(throwable, title);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public GameCrashReport.Category addCategory(String name) {
        CrashReportCategory category = this.crashReport.addCategory(name);
        return new VersionedGameCrashReportCategory(category);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public <T> T crashReport() {
        return (T) this.crashReport;
    }
}

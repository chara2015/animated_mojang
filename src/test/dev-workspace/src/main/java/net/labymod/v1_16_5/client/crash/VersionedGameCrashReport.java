package net.labymod.v1_16_5.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/crash/VersionedGameCrashReport.class */
public class VersionedGameCrashReport implements GameCrashReport {
    private final l crashReport;

    public VersionedGameCrashReport(String title, Throwable throwable) {
        this.crashReport = l.a(throwable, title);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public GameCrashReport.Category addCategory(String name) {
        m category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public <T> T crashReport() {
        return (T) this.crashReport;
    }
}

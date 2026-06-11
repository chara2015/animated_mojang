package net.labymod.v1_21_8.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/crash/VersionedGameCrashReport.class */
public class VersionedGameCrashReport implements GameCrashReport {
    private final p crashReport;

    public VersionedGameCrashReport(String title, Throwable throwable) {
        this.crashReport = p.a(throwable, title);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public GameCrashReport.Category addCategory(String name) {
        q category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public <T> T crashReport() {
        return (T) this.crashReport;
    }
}

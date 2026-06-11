package net.labymod.v1_17_1.client.crash;

import net.labymod.api.client.crash.GameCrashReport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/crash/VersionedGameCrashReport.class */
public class VersionedGameCrashReport implements GameCrashReport {
    private final q crashReport;

    public VersionedGameCrashReport(String title, Throwable throwable) {
        this.crashReport = q.a(throwable, title);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public GameCrashReport.Category addCategory(String name) {
        r category = this.crashReport.a(name);
        return new VersionedGameCrashReportCategory(category);
    }

    @Override // net.labymod.api.client.crash.GameCrashReport
    public <T> T crashReport() {
        return (T) this.crashReport;
    }
}

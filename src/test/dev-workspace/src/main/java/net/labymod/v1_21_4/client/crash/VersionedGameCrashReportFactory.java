package net.labymod.v1_21_4.client.crash;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/crash/VersionedGameCrashReportFactory.class */
@Singleton
@Implements(GameCrashReport.Factory.class)
public class VersionedGameCrashReportFactory implements GameCrashReport.Factory {
    @Inject
    public VersionedGameCrashReportFactory() {
    }

    @Override // net.labymod.api.client.crash.GameCrashReport.Factory
    public GameCrashReport create(@NotNull String title, Throwable throwable) {
        Objects.requireNonNull(title);
        return new VersionedGameCrashReport(title, throwable);
    }
}

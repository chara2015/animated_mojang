package net.labymod.api.thirdparty;

import net.labymod.api.Laby;
import net.labymod.api.util.Lazy;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/thirdparty/LabySentry.class */
public final class LabySentry {
    private static final Lazy<SentryService> SENTRY_SERVICE = Lazy.of(() -> {
        return Laby.references().sentryService();
    });

    public static void capture(Throwable throwable) {
        SENTRY_SERVICE.get().capture(throwable);
    }
}

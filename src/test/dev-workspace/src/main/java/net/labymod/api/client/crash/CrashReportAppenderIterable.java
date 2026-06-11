package net.labymod.api.client.crash;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/crash/CrashReportAppenderIterable.class */
@Referenceable
public interface CrashReportAppenderIterable extends Iterable<CrashReportAppender> {
    void addAppender(CrashReportAppender crashReportAppender);
}

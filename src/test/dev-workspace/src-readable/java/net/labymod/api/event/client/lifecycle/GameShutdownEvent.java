package net.labymod.api.event.client.lifecycle;

import java.io.File;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/GameShutdownEvent.class */
@LabyEvent(background = true)
@Deprecated(since = "4.3.10", forRemoval = true)
public class GameShutdownEvent implements Event {

    @Nullable
    private final Throwable cause;

    @Nullable
    private final File crashReport;

    public GameShutdownEvent(@Nullable Throwable cause, @Nullable File crashReport) {
        this.cause = cause;
        this.crashReport = crashReport;
    }

    public GameShutdownEvent() {
        this(null, null);
    }

    public boolean isCrash() {
        return this.cause != null;
    }

    @Nullable
    public Throwable getCause() {
        return this.cause;
    }

    @Nullable
    public File getCrashReportFile() {
        return this.crashReport;
    }
}

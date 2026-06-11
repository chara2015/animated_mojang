package net.labymod.core.event.client.lifecycle;

import java.io.File;
import java.nio.file.Path;
import net.labymod.api.Laby;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.client.lifecycle.ShutdownEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/lifecycle/ShutdownEventCaller.class */
public final class ShutdownEventCaller {
    public static void callShutdownPreEvent() {
        Laby.fireEvent(new GameShutdownEvent());
        Laby.fireEvent(new ShutdownEvent(ShutdownEvent.Stage.PRE, new ShutdownEvent.NormalContext()));
    }

    public static void callShutdownPostEvent() {
        Laby.fireEvent(new ShutdownEvent(ShutdownEvent.Stage.POST, new ShutdownEvent.NormalContext()));
    }

    public static void callShutdownCrashEvent(Throwable throwable, Path crashReport) {
        File file;
        if (crashReport != null) {
            file = crashReport.toFile();
        } else {
            file = null;
        }
        Laby.fireEvent(new GameShutdownEvent(throwable, file));
        Laby.fireEvent(new ShutdownEvent(ShutdownEvent.Stage.CRASH, new ShutdownEvent.CrashContext(throwable, crashReport)));
    }
}

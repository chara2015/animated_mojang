package net.labymod.api.event.client.misc;

import java.io.File;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/misc/VanillaOptionsSaveEvent.class */
public class VanillaOptionsSaveEvent implements Event, Cancellable {
    private final Phase phase;
    private final File optionsFile;
    private boolean cancelled;

    public VanillaOptionsSaveEvent(Phase phase, File optionsFile) {
        this.phase = phase;
        this.optionsFile = optionsFile;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public File getFile() {
        return this.optionsFile;
    }

    @Override // net.labymod.api.event.Cancellable
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

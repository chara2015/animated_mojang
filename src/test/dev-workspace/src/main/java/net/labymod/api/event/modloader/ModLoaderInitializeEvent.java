package net.labymod.api.event.modloader;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import net.labymod.api.modloader.ModLoader;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/modloader/ModLoaderInitializeEvent.class */
public class ModLoaderInitializeEvent implements Event {
    private final ModLoader modLoader;
    private final Phase phase;

    public ModLoaderInitializeEvent(@NotNull ModLoader modLoader, @NotNull Phase phase) {
        this.modLoader = modLoader;
        this.phase = phase;
    }

    @NotNull
    public ModLoader modLoader() {
        return this.modLoader;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }
}

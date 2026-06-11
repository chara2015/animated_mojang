package net.labymod.api.event.addon.lifecycle;

import java.util.Objects;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/addon/lifecycle/AddonPostEnableEvent.class */
@LabyEvent(background = true, classLoaderExclusive = true, allowAllExceptions = true)
public class AddonPostEnableEvent implements Event {
    private final LoadedAddon addon;

    public AddonPostEnableEvent(@NotNull LoadedAddon addon) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        this.addon = addon;
    }

    public LoadedAddon addon() {
        return this.addon;
    }

    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}

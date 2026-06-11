package net.labymod.api.event.addon.lifecycle;

import java.util.Objects;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/addon/lifecycle/GlobalAddonEnableEvent.class */
public class GlobalAddonEnableEvent implements Event {
    private final LoadedAddon addon;
    private final Object instance;

    public GlobalAddonEnableEvent(@NotNull LoadedAddon addon, @NotNull Object instance) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        Objects.requireNonNull(instance, "Addon instance cannot be null!");
        this.addon = addon;
        this.instance = instance;
    }

    public Object getInstance() {
        return this.instance;
    }

    public LoadedAddon addon() {
        return this.addon;
    }

    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}

package net.labymod.api.event.addon.lifecycle;

import java.util.Objects;
import net.labymod.api.event.Event;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/addon/lifecycle/GlobalAddonPostEnableEvent.class */
public class GlobalAddonPostEnableEvent implements Event {
    private final InstalledAddonInfo addonInfo;

    public GlobalAddonPostEnableEvent(@NotNull InstalledAddonInfo addonInfo) {
        Objects.requireNonNull(addonInfo, "Addon info must not be null");
        this.addonInfo = addonInfo;
    }

    public InstalledAddonInfo addonInfo() {
        return this.addonInfo;
    }
}

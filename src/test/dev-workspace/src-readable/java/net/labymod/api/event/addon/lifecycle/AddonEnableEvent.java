package net.labymod.api.event.addon.lifecycle;

import java.util.Objects;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.reference.ReferenceStorageAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/addon/lifecycle/AddonEnableEvent.class */
@LabyEvent(background = true, classLoaderExclusive = true, allowAllExceptions = true)
public class AddonEnableEvent implements Event {
    private final LoadedAddon addon;
    private final Object instance;
    private final ReferenceStorageAccessor referenceStorageAccessor;

    public AddonEnableEvent(@NotNull LoadedAddon addon, @NotNull Object instance, @Nullable ReferenceStorageAccessor referenceStorageAccessor) {
        Objects.requireNonNull(addon, "Loaded addon cannot be null!");
        Objects.requireNonNull(instance, "Addon instance cannot be null!");
        this.addon = addon;
        this.instance = instance;
        this.referenceStorageAccessor = referenceStorageAccessor;
    }

    public Object getInstance() {
        return this.instance;
    }

    public ReferenceStorageAccessor getReferenceStorageAccessor() {
        return this.referenceStorageAccessor;
    }

    public LoadedAddon addon() {
        return this.addon;
    }

    public InstalledAddonInfo addonInfo() {
        return this.addon.info();
    }
}

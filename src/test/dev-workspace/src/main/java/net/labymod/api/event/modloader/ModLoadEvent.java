package net.labymod.api.event.modloader;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.mod.ModInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/modloader/ModLoadEvent.class */
public class ModLoadEvent extends DefaultCancellable implements Event {
    private final ModInfo modInfo;
    private final ModLoader modLoader;

    public ModLoadEvent(@NotNull ModInfo modInfo, @NotNull ModLoader modLoader) {
        this.modInfo = modInfo;
        this.modLoader = modLoader;
    }

    @NotNull
    public ModInfo modInfo() {
        return this.modInfo;
    }

    @NotNull
    public ModLoader modLoader() {
        return this.modLoader;
    }
}

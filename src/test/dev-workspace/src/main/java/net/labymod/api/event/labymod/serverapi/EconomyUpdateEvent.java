package net.labymod.api.event.labymod.serverapi;

import java.util.Objects;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.serverapi.core.model.display.EconomyDisplay;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/serverapi/EconomyUpdateEvent.class */
public class EconomyUpdateEvent extends DefaultCancellable implements Event {
    private EconomyDisplay economy;

    public EconomyUpdateEvent(@NotNull EconomyDisplay economy) {
        Objects.requireNonNull(economy, "Economy cannot be null");
        this.economy = economy;
    }

    public EconomyDisplay economy() {
        return this.economy;
    }

    public void setEconomy(@NotNull EconomyDisplay economy) {
        Objects.requireNonNull(economy, "Economy cannot be null");
        this.economy = economy;
    }
}

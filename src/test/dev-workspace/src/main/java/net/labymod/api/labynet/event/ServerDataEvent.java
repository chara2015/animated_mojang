package net.labymod.api.labynet.event;

import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/event/ServerDataEvent.class */
public class ServerDataEvent implements Event {
    private final String type;
    private final String[] arguments;

    public ServerDataEvent(@NotNull String type, @NotNull String[] arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    @NotNull
    public String type() {
        return this.type;
    }

    @NotNull
    public String[] arguments() {
        return this.arguments;
    }
}

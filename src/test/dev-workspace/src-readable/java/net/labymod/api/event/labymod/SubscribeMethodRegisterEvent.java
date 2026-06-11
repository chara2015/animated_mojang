package net.labymod.api.event.labymod;

import net.labymod.api.event.Event;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.method.SubscribeMethod;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/SubscribeMethodRegisterEvent.class */
public class SubscribeMethodRegisterEvent implements Event {
    private final ListenerRegistry registry;
    private final SubscribeMethod method;

    public SubscribeMethodRegisterEvent(@NotNull ListenerRegistry registry, @NotNull SubscribeMethod method) {
        this.registry = registry;
        this.method = method;
    }

    @NotNull
    public ListenerRegistry registry() {
        return this.registry;
    }

    @NotNull
    public SubscribeMethod method() {
        return this.method;
    }
}

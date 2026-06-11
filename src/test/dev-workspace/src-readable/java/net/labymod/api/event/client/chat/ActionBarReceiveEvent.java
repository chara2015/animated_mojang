package net.labymod.api.event.client.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ActionBarReceiveEvent.class */
public class ActionBarReceiveEvent extends DefaultCancellable implements Event {
    private final Phase phase;
    private final boolean animatedMessage;
    private Component message;

    public ActionBarReceiveEvent(@NotNull Phase phase, @Nullable Component message, boolean animatedMessage) {
        this.phase = phase;
        this.message = message;
        this.animatedMessage = animatedMessage;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }

    @Nullable
    public Component getMessage() {
        return this.message;
    }

    public void setMessage(Component message) {
        if (this.phase != Phase.PRE) {
            throw new IllegalStateException("Message cannot be modified after PRE phase");
        }
        this.message = message;
    }

    public boolean isAnimatedMessage() {
        return this.animatedMessage;
    }

    @Override // net.labymod.api.event.DefaultCancellable, net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        if (this.phase != Phase.PRE) {
            throw new IllegalStateException("ActionBar cannot be cancelled after PRE phase");
        }
        super.setCancelled(cancelled);
    }

    @Deprecated(forRemoval = true, since = "4.1.0")
    @Nullable
    public Component message() {
        return getMessage();
    }

    @Deprecated(forRemoval = true, since = "4.1.0")
    public void message(@Nullable Component message) {
        setMessage(message);
    }
}

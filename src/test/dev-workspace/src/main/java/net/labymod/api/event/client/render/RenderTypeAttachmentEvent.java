package net.labymod.api.event.client.render;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/RenderTypeAttachmentEvent.class */
@Deprecated(forRemoval = true, since = "4.3.0")
@ApiStatus.Experimental
public class RenderTypeAttachmentEvent extends DefaultCancellable implements Event {
    private final String name;
    private final State state;
    private final Phase phase;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/RenderTypeAttachmentEvent$State.class */
    public enum State {
        APPLY,
        CLEAR
    }

    public RenderTypeAttachmentEvent(String name, State state, Phase phase) {
        this.name = name;
        this.state = state;
        this.phase = phase;
    }

    public String name() {
        return this.name;
    }

    public State state() {
        return this.state;
    }

    public Phase phase() {
        return this.phase;
    }
}

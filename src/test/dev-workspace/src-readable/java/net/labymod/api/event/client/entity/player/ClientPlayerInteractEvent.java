package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/ClientPlayerInteractEvent.class */
public class ClientPlayerInteractEvent extends DefaultCancellable implements Event {
    private final ClientPlayer clientPlayer;
    private final InteractionType interactionType;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/ClientPlayerInteractEvent$InteractionType.class */
    public enum InteractionType {
        INTERACT,
        ATTACK,
        CONTINUE_ATTACK,
        PICK_BLOCK
    }

    public ClientPlayerInteractEvent(ClientPlayer clientPlayer, InteractionType interactionType) {
        this.clientPlayer = clientPlayer;
        this.interactionType = interactionType;
    }

    public ClientPlayer clientPlayer() {
        return this.clientPlayer;
    }

    public InteractionType type() {
        return this.interactionType;
    }
}

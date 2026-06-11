package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/ClientPlayerTurnEvent.class */
public class ClientPlayerTurnEvent extends DefaultCancellable implements Event {
    private final ClientPlayer clientPlayer;
    private double x;
    private double y;

    public ClientPlayerTurnEvent(ClientPlayer clientPlayer, double x, double y) {
        this.clientPlayer = clientPlayer;
        this.x = x;
        this.y = y;
    }

    public ClientPlayer clientPlayer() {
        return this.clientPlayer;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

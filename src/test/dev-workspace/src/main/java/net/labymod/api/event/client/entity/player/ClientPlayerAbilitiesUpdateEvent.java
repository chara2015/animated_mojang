package net.labymod.api.event.client.entity.player;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/ClientPlayerAbilitiesUpdateEvent.class */
public final class ClientPlayerAbilitiesUpdateEvent extends Record implements Event {
    private final ClientPlayer clientPlayer;

    public ClientPlayerAbilitiesUpdateEvent(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientPlayerAbilitiesUpdateEvent.class), ClientPlayerAbilitiesUpdateEvent.class, "clientPlayer", "FIELD:Lnet/labymod/api/event/client/entity/player/ClientPlayerAbilitiesUpdateEvent;->clientPlayer:Lnet/labymod/api/client/entity/player/ClientPlayer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientPlayerAbilitiesUpdateEvent.class), ClientPlayerAbilitiesUpdateEvent.class, "clientPlayer", "FIELD:Lnet/labymod/api/event/client/entity/player/ClientPlayerAbilitiesUpdateEvent;->clientPlayer:Lnet/labymod/api/client/entity/player/ClientPlayer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientPlayerAbilitiesUpdateEvent.class, Object.class), ClientPlayerAbilitiesUpdateEvent.class, "clientPlayer", "FIELD:Lnet/labymod/api/event/client/entity/player/ClientPlayerAbilitiesUpdateEvent;->clientPlayer:Lnet/labymod/api/client/entity/player/ClientPlayer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ClientPlayer clientPlayer() {
        return this.clientPlayer;
    }

    public PlayerAbilities abilities() {
        return this.clientPlayer.abilities();
    }
}

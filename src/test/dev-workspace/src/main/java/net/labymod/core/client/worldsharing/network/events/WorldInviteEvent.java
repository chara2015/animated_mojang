package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/WorldInviteEvent.class */
@NetworkEvent.Subject("world.invite")
public final class WorldInviteEvent extends Record implements NetworkEvent {
    private final UUID player;
    private final boolean add;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldInviteEvent.class), WorldInviteEvent.class, "player;add", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->player:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->add:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldInviteEvent.class), WorldInviteEvent.class, "player;add", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->player:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->add:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldInviteEvent.class, Object.class), WorldInviteEvent.class, "player;add", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->player:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldInviteEvent;->add:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public UUID player() {
        return this.player;
    }

    public boolean add() {
        return this.add;
    }

    public WorldInviteEvent(UUID player, boolean add) {
        this.player = player;
        this.add = add;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
    }
}

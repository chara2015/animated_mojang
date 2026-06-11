package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/WorldConfigEvent.class */
@NetworkEvent.Subject("world.config")
public final class WorldConfigEvent extends Record implements NetworkEvent {
    private final byte maxPlayers;
    private final byte privacy;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldConfigEvent.class), WorldConfigEvent.class, "maxPlayers;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->maxPlayers:B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->privacy:B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldConfigEvent.class), WorldConfigEvent.class, "maxPlayers;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->maxPlayers:B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->privacy:B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldConfigEvent.class, Object.class), WorldConfigEvent.class, "maxPlayers;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->maxPlayers:B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldConfigEvent;->privacy:B").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public byte maxPlayers() {
        return this.maxPlayers;
    }

    public byte privacy() {
        return this.privacy;
    }

    public WorldConfigEvent(byte maxPlayers, byte privacy) {
        this.maxPlayers = maxPlayers;
        this.privacy = privacy;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
    }
}

package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/WorldStopEvent.class */
@NetworkEvent.Subject("world.stop")
public final class WorldStopEvent extends Record implements NetworkEvent {
    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldStopEvent.class), WorldStopEvent.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldStopEvent.class), WorldStopEvent.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldStopEvent.class, Object.class), WorldStopEvent.class, "").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
    }
}

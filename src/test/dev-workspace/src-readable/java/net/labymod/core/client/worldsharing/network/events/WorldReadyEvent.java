package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/WorldReadyEvent.class */
@NetworkEvent.Subject("world.ready")
public final class WorldReadyEvent extends Record implements NetworkEvent {
    private final String domain;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldReadyEvent.class), WorldReadyEvent.class, "domain", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldReadyEvent;->domain:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldReadyEvent.class), WorldReadyEvent.class, "domain", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldReadyEvent;->domain:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldReadyEvent.class, Object.class), WorldReadyEvent.class, "domain", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldReadyEvent;->domain:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String domain() {
        return this.domain;
    }

    public WorldReadyEvent(String domain) {
        this.domain = domain;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
        handler.handle(this);
    }
}

package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/TunnelRequestEvent.class */
@NetworkEvent.Subject("world.request")
public final class TunnelRequestEvent extends Record implements NetworkEvent {
    private final String endpoint;
    private final byte[] sessionId;
    private final JoinWorldEvent.Type type;

    @Nullable
    private final UUID targetId;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TunnelRequestEvent.class), TunnelRequestEvent.class, "endpoint;sessionId;type;targetId", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->endpoint:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->sessionId:[B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->targetId:Ljava/util/UUID;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TunnelRequestEvent.class), TunnelRequestEvent.class, "endpoint;sessionId;type;targetId", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->endpoint:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->sessionId:[B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->targetId:Ljava/util/UUID;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TunnelRequestEvent.class, Object.class), TunnelRequestEvent.class, "endpoint;sessionId;type;targetId", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->endpoint:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->sessionId:[B", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/TunnelRequestEvent;->targetId:Ljava/util/UUID;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String endpoint() {
        return this.endpoint;
    }

    public byte[] sessionId() {
        return this.sessionId;
    }

    public JoinWorldEvent.Type type() {
        return this.type;
    }

    @Nullable
    public UUID targetId() {
        return this.targetId;
    }

    public TunnelRequestEvent(String endpoint, byte[] sessionId, JoinWorldEvent.Type type, @Nullable UUID targetId) {
        this.endpoint = endpoint;
        this.sessionId = sessionId;
        this.type = type;
        this.targetId = targetId;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
        handler.handle(this);
    }
}

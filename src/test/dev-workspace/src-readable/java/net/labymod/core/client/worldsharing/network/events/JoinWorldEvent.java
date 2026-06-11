package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/JoinWorldEvent.class */
public final class JoinWorldEvent {
    private JoinWorldEvent() {
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request.class */
    @NetworkEvent.Subject("join.request")
    public static final class Request extends Record implements NetworkEvent {
        private final UUID targetId;
        private final Type type;

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Request.class), Request.class, "targetId;type", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->targetId:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Request.class), Request.class, "targetId;type", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->targetId:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Request.class, Object.class), Request.class, "targetId;type", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->targetId:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Request;->type:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public UUID targetId() {
            return this.targetId;
        }

        public Type type() {
            return this.type;
        }

        public Request(UUID targetId, Type type) {
            this.targetId = targetId;
            this.type = type;
        }

        @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
        public void handle(NetEventHandler handler) {
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response.class */
    @NetworkEvent.Subject("join.response")
    public static final class Response extends Record implements NetworkEvent {

        @Nullable
        private final String error;

        @Nullable
        private final String endpoint;

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Response.class), Response.class, "error;endpoint", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->error:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->endpoint:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Response.class), Response.class, "error;endpoint", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->error:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->endpoint:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Response.class, Object.class), Response.class, "error;endpoint", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->error:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Response;->endpoint:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @Nullable
        public String error() {
            return this.error;
        }

        @Nullable
        public String endpoint() {
            return this.endpoint;
        }

        public Response(@Nullable String error, @Nullable String endpoint) {
            this.error = error;
            this.endpoint = endpoint;
        }

        @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
        public void handle(NetEventHandler handler) {
            handler.handle(this);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/JoinWorldEvent$Type.class */
    public enum Type {
        LOCAL_SERVER,
        WORLD;

        private static final Type[] VALUES = values();

        @Nullable
        public static Type fromString(String type) {
            for (Type value : VALUES) {
                if (value.name().equalsIgnoreCase(type)) {
                    return value;
                }
            }
            return null;
        }
    }
}

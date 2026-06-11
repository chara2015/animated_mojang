package net.labymod.core.client.worldsharing.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.InetSocketAddress;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/LocalPlayer.class */
public final class LocalPlayer extends Record {
    private final UUID uuid;
    private final String name;
    private final InetSocketAddress server;

    @Nullable
    private final Runnable close;

    public LocalPlayer(UUID uuid, String name, InetSocketAddress server, @Nullable Runnable close) {
        this.uuid = uuid;
        this.name = name;
        this.server = server;
        this.close = close;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LocalPlayer.class), LocalPlayer.class, "uuid;name;server;close", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->server:Ljava/net/InetSocketAddress;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->close:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LocalPlayer.class), LocalPlayer.class, "uuid;name;server;close", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->server:Ljava/net/InetSocketAddress;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->close:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LocalPlayer.class, Object.class), LocalPlayer.class, "uuid;name;server;close", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->uuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->server:Ljava/net/InetSocketAddress;", "FIELD:Lnet/labymod/core/client/worldsharing/model/LocalPlayer;->close:Ljava/lang/Runnable;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String name() {
        return this.name;
    }

    public InetSocketAddress server() {
        return this.server;
    }

    @Nullable
    public Runnable close() {
        return this.close;
    }
}

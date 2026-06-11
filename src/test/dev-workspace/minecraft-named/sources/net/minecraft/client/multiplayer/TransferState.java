package net.minecraft.client.multiplayer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/TransferState.class */
public final class TransferState extends Record {
    private final Map<Identifier, byte[]> cookies;
    private final Map<UUID, PlayerInfo> seenPlayers;
    private final boolean seenInsecureChatWarning;

    public TransferState(Map<Identifier, byte[]> $$0, Map<UUID, PlayerInfo> $$1, boolean $$2) {
        this.cookies = $$0;
        this.seenPlayers = $$1;
        this.seenInsecureChatWarning = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TransferState.class), TransferState.class, "cookies;seenPlayers;seenInsecureChatWarning", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->cookies:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenPlayers:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenInsecureChatWarning:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TransferState.class), TransferState.class, "cookies;seenPlayers;seenInsecureChatWarning", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->cookies:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenPlayers:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenInsecureChatWarning:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TransferState.class, Object.class), TransferState.class, "cookies;seenPlayers;seenInsecureChatWarning", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->cookies:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenPlayers:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/multiplayer/TransferState;->seenInsecureChatWarning:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<Identifier, byte[]> cookies() {
        return this.cookies;
    }

    public Map<UUID, PlayerInfo> seenPlayers() {
        return this.seenPlayers;
    }

    public boolean seenInsecureChatWarning() {
        return this.seenInsecureChatWarning;
    }
}

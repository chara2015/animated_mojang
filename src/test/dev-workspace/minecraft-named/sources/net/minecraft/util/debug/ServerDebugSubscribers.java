package net.minecraft.util.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/ServerDebugSubscribers.class */
public class ServerDebugSubscribers {
    private final MinecraftServer server;
    private final Map<DebugSubscription<?>, List<ServerPlayer>> enabledSubscriptions = new HashMap();

    public ServerDebugSubscribers(MinecraftServer $$0) {
        this.server = $$0;
    }

    private List<ServerPlayer> getSubscribersFor(DebugSubscription<?> $$0) {
        return this.enabledSubscriptions.getOrDefault($$0, List.of());
    }

    public void tick() {
        this.enabledSubscriptions.values().forEach((v0) -> {
            v0.clear();
        });
        for (ServerPlayer $$0 : this.server.getPlayerList().getPlayers()) {
            for (DebugSubscription<?> $$1 : $$0.debugSubscriptions()) {
                this.enabledSubscriptions.computeIfAbsent($$1, $$02 -> {
                    return new ArrayList();
                }).add($$0);
            }
        }
        this.enabledSubscriptions.values().removeIf((v0) -> {
            return v0.isEmpty();
        });
    }

    public void broadcastToAll(DebugSubscription<?> $$0, Packet<?> $$1) {
        for (ServerPlayer $$2 : getSubscribersFor($$0)) {
            $$2.connection.send($$1);
        }
    }

    public Set<DebugSubscription<?>> enabledSubscriptions() {
        return Set.copyOf(this.enabledSubscriptions.keySet());
    }

    public boolean hasAnySubscriberFor(DebugSubscription<?> $$0) {
        return !getSubscribersFor($$0).isEmpty();
    }

    public boolean hasRequiredPermissions(ServerPlayer $$0) {
        NameAndId $$1 = $$0.nameAndId();
        if (SharedConstants.IS_RUNNING_IN_IDE && this.server.isSingleplayerOwner($$1)) {
            return true;
        }
        return this.server.getPlayerList().isOp($$1);
    }
}

package net.minecraft.server.jsonrpc.internalapi;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.jsonrpc.methods.ClientInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/internalapi/MinecraftPlayerListService.class */
public interface MinecraftPlayerListService {
    List<ServerPlayer> getPlayers();

    ServerPlayer getPlayer(UUID uuid);

    Optional<NameAndId> fetchUserByName(String str);

    Optional<NameAndId> fetchUserById(UUID uuid);

    Optional<NameAndId> getCachedUserById(UUID uuid);

    Optional<ServerPlayer> getPlayer(Optional<UUID> optional, Optional<String> optional2);

    List<ServerPlayer> getPlayersWithAddress(String str);

    ServerPlayer getPlayerByName(String str);

    void remove(ServerPlayer serverPlayer, ClientInfo clientInfo);

    default CompletableFuture<Optional<NameAndId>> getUser(Optional<UUID> $$0, Optional<String> $$1) {
        if ($$0.isPresent()) {
            Optional<NameAndId> $$2 = getCachedUserById($$0.get());
            if ($$2.isPresent()) {
                return CompletableFuture.completedFuture($$2);
            }
            return CompletableFuture.supplyAsync(() -> {
                return fetchUserById((UUID) $$0.get());
            }, Util.nonCriticalIoPool());
        }
        if ($$1.isPresent()) {
            return CompletableFuture.supplyAsync(() -> {
                return fetchUserByName((String) $$1.get());
            }, Util.nonCriticalIoPool());
        }
        return CompletableFuture.completedFuture(Optional.empty());
    }
}

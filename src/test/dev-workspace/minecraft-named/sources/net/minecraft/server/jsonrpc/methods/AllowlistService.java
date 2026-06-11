package net.minecraft.server.jsonrpc.methods;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.server.jsonrpc.api.PlayerDto;
import net.minecraft.server.jsonrpc.internalapi.MinecraftApi;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/AllowlistService.class */
public class AllowlistService {
    public static List<PlayerDto> get(MinecraftApi $$0) {
        return $$0.allowListService().getEntries().stream().filter($$02 -> {
            return $$02.getUser() != null;
        }).map($$03 -> {
            return PlayerDto.from($$03.getUser());
        }).toList();
    }

    public static List<PlayerDto> add(MinecraftApi $$0, List<PlayerDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<NameAndId>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.id(), $$12.name());
        }).toList();
        for (Optional<NameAndId> $$4 : (List) Util.sequence($$3).join()) {
            $$4.ifPresent($$22 -> {
                $$0.allowListService().add(new UserWhiteListEntry($$22), $$2);
            });
        }
        return get($$0);
    }

    public static List<PlayerDto> clear(MinecraftApi $$0, ClientInfo $$1) {
        $$0.allowListService().clear($$1);
        return get($$0);
    }

    public static List<PlayerDto> remove(MinecraftApi $$0, List<PlayerDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<NameAndId>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.id(), $$12.name());
        }).toList();
        for (Optional<NameAndId> $$4 : (List) Util.sequence($$3).join()) {
            $$4.ifPresent($$22 -> {
                $$0.allowListService().remove($$22, $$2);
            });
        }
        $$0.allowListService().kickUnlistedPlayers($$2);
        return get($$0);
    }

    public static List<PlayerDto> set(MinecraftApi $$0, List<PlayerDto> $$1, ClientInfo $$2) {
        List<CompletableFuture<Optional<NameAndId>>> $$3 = $$1.stream().map($$12 -> {
            return $$0.playerListService().getUser($$12.id(), $$12.name());
        }).toList();
        Set<NameAndId> $$4 = (Set) ((List) Util.sequence($$3).join()).stream().flatMap((v0) -> {
            return v0.stream();
        }).collect(Collectors.toSet());
        Set<NameAndId> $$5 = (Set) $$0.allowListService().getEntries().stream().map((v0) -> {
            return v0.getUser();
        }).collect(Collectors.toSet());
        $$5.stream().filter($$13 -> {
            return !$$4.contains($$13);
        }).forEach($$22 -> {
            $$0.allowListService().remove($$22, $$2);
        });
        $$4.stream().filter($$14 -> {
            return !$$5.contains($$14);
        }).forEach($$23 -> {
            $$0.allowListService().add(new UserWhiteListEntry($$23), $$2);
        });
        $$0.allowListService().kickUnlistedPlayers($$2);
        return get($$0);
    }
}

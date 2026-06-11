package net.minecraft.server.players;

import java.util.Optional;
import java.util.UUID;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/UserNameToIdResolver.class */
public interface UserNameToIdResolver {
    void add(NameAndId nameAndId);

    Optional<NameAndId> get(String str);

    Optional<NameAndId> get(UUID uuid);

    void resolveOfflineUsers(boolean z);

    void save();
}

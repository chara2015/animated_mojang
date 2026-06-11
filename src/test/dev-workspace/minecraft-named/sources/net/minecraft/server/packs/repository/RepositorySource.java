package net.minecraft.server.packs.repository;

import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/repository/RepositorySource.class */
@FunctionalInterface
public interface RepositorySource {
    void loadPacks(Consumer<Pack> consumer);
}

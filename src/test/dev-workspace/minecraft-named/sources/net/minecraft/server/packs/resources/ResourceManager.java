package net.minecraft.server.packs.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ResourceManager.class */
public interface ResourceManager extends ResourceProvider {
    Set<String> getNamespaces();

    List<Resource> getResourceStack(Identifier identifier);

    Map<Identifier, Resource> listResources(String str, Predicate<Identifier> predicate);

    Map<Identifier, List<Resource>> listResourceStacks(String str, Predicate<Identifier> predicate);

    Stream<PackResources> listPacks();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ResourceManager$Empty.class */
    public enum Empty implements ResourceManager {
        INSTANCE;

        @Override // net.minecraft.server.packs.resources.ResourceManager
        public Set<String> getNamespaces() {
            return Set.of();
        }

        @Override // net.minecraft.server.packs.resources.ResourceProvider
        public Optional<Resource> getResource(Identifier $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.server.packs.resources.ResourceManager
        public List<Resource> getResourceStack(Identifier $$0) {
            return List.of();
        }

        @Override // net.minecraft.server.packs.resources.ResourceManager
        public Map<Identifier, Resource> listResources(String $$0, Predicate<Identifier> $$1) {
            return Map.of();
        }

        @Override // net.minecraft.server.packs.resources.ResourceManager
        public Map<Identifier, List<Resource>> listResourceStacks(String $$0, Predicate<Identifier> $$1) {
            return Map.of();
        }

        @Override // net.minecraft.server.packs.resources.ResourceManager
        public Stream<PackResources> listPacks() {
            return Stream.of((Object[]) new PackResources[0]);
        }
    }
}

package net.minecraft.server.packs.resources;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/MultiPackResourceManager.class */
public class MultiPackResourceManager implements CloseableResourceManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Map<String, FallbackResourceManager> namespacedManagers;
    private final List<PackResources> packs;

    public MultiPackResourceManager(PackType $$0, List<PackResources> $$1) {
        this.packs = List.copyOf($$1);
        Map<String, FallbackResourceManager> $$2 = new HashMap<>();
        List<String> $$3 = $$1.stream().flatMap($$12 -> {
            return $$12.getNamespaces($$0).stream();
        }).distinct().toList();
        for (PackResources $$4 : $$1) {
            ResourceFilterSection $$5 = getPackFilterSection($$4);
            Set<String> $$6 = $$4.getNamespaces($$0);
            Predicate<Identifier> $$7 = $$5 != null ? $$13 -> {
                return $$5.isPathFiltered($$13.getPath());
            } : null;
            for (String $$8 : $$3) {
                boolean $$9 = $$6.contains($$8);
                boolean $$10 = $$5 != null && $$5.isNamespaceFiltered($$8);
                if ($$9 || $$10) {
                    FallbackResourceManager $$11 = $$2.get($$8);
                    if ($$11 == null) {
                        $$11 = new FallbackResourceManager($$0, $$8);
                        $$2.put($$8, $$11);
                    }
                    if ($$9 && $$10) {
                        $$11.push($$4, $$7);
                    } else if ($$9) {
                        $$11.push($$4);
                    } else {
                        $$11.pushFilterOnly($$4.packId(), $$7);
                    }
                }
            }
        }
        this.namespacedManagers = $$2;
    }

    private ResourceFilterSection getPackFilterSection(PackResources $$0) {
        try {
            return (ResourceFilterSection) $$0.getMetadataSection(ResourceFilterSection.TYPE);
        } catch (IOException e) {
            LOGGER.error("Failed to get filter section from pack {}", $$0.packId());
            return null;
        }
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Set<String> getNamespaces() {
        return this.namespacedManagers.keySet();
    }

    @Override // net.minecraft.server.packs.resources.ResourceProvider
    public Optional<Resource> getResource(Identifier $$0) {
        ResourceManager $$1 = this.namespacedManagers.get($$0.getNamespace());
        if ($$1 != null) {
            return $$1.getResource($$0);
        }
        return Optional.empty();
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public List<Resource> getResourceStack(Identifier $$0) {
        ResourceManager $$1 = this.namespacedManagers.get($$0.getNamespace());
        if ($$1 != null) {
            return $$1.getResourceStack($$0);
        }
        return List.of();
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, Resource> listResources(String $$0, Predicate<Identifier> $$1) {
        checkTrailingDirectoryPath($$0);
        Map<Identifier, Resource> $$2 = new TreeMap<>();
        for (FallbackResourceManager $$3 : this.namespacedManagers.values()) {
            $$2.putAll($$3.listResources($$0, $$1));
        }
        return $$2;
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, List<Resource>> listResourceStacks(String $$0, Predicate<Identifier> $$1) {
        checkTrailingDirectoryPath($$0);
        Map<Identifier, List<Resource>> $$2 = new TreeMap<>();
        for (FallbackResourceManager $$3 : this.namespacedManagers.values()) {
            $$2.putAll($$3.listResourceStacks($$0, $$1));
        }
        return $$2;
    }

    private static void checkTrailingDirectoryPath(String $$0) {
        if ($$0.endsWith("/")) {
            throw new IllegalArgumentException("Trailing slash in path " + $$0);
        }
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Stream<PackResources> listPacks() {
        return this.packs.stream();
    }

    @Override // net.minecraft.server.packs.resources.CloseableResourceManager, java.lang.AutoCloseable
    public void close() {
        this.packs.forEach((v0) -> {
            v0.close();
        });
    }
}

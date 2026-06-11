package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Unit;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ReloadableResourceManager.class */
public class ReloadableResourceManager implements ResourceManager, AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private CloseableResourceManager resources;
    private final List<PreparableReloadListener> listeners = Lists.newArrayList();
    private final PackType type;

    public ReloadableResourceManager(PackType $$0) {
        this.type = $$0;
        this.resources = new MultiPackResourceManager($$0, List.of());
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.resources.close();
    }

    public void registerReloadListener(PreparableReloadListener $$0) {
        this.listeners.add($$0);
    }

    public ReloadInstance createReload(Executor $$0, Executor $$1, CompletableFuture<Unit> $$2, List<PackResources> $$3) {
        LOGGER.info("Reloading ResourceManager: {}", LogUtils.defer(() -> {
            return $$3.stream().map((v0) -> {
                return v0.packId();
            }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT));
        }));
        this.resources.close();
        this.resources = new MultiPackResourceManager(this.type, $$3);
        return SimpleReloadInstance.create(this.resources, this.listeners, $$0, $$1, $$2, LOGGER.isDebugEnabled());
    }

    @Override // net.minecraft.server.packs.resources.ResourceProvider
    public Optional<Resource> getResource(Identifier $$0) {
        return this.resources.getResource($$0);
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Set<String> getNamespaces() {
        return this.resources.getNamespaces();
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public List<Resource> getResourceStack(Identifier $$0) {
        return this.resources.getResourceStack($$0);
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, Resource> listResources(String $$0, Predicate<Identifier> $$1) {
        return this.resources.listResources($$0, $$1);
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Map<Identifier, List<Resource>> listResourceStacks(String $$0, Predicate<Identifier> $$1) {
        return this.resources.listResourceStacks($$0, $$1);
    }

    @Override // net.minecraft.server.packs.resources.ResourceManager
    public Stream<PackResources> listPacks() {
        return this.resources.listPacks();
    }
}

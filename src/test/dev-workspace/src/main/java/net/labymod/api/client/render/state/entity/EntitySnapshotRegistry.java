package net.labymod.api.client.render.state.entity;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.service.DependencyInjectingCreator;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/EntitySnapshotRegistry.class */
public class EntitySnapshotRegistry {
    private static final Logging LOGGER = Logging.getLogger();
    private final Reference2ObjectMap<ExtraKey<?>, LabySnapshotFactory<?, ?>> factories = new Reference2ObjectOpenHashMap();
    private final Reference2ObjectMap<Class<?>, Object> dependencies = new Reference2ObjectOpenHashMap();
    private final List<EntitySnapshotProcessor<?>> processors = new ArrayList();

    public EntitySnapshotRegistry() {
        registerDependency(EntitySnapshotRegistry.class, this);
        registerDependency(LabyAPI.class, Laby.labyAPI());
    }

    public void populateRegistry(ClassLoader classLoader) {
        populateEntityProcessors(classLoader);
        populateSnapshotFactories(classLoader);
    }

    public void process(Entity entity, float partialTicks, ExtrasWriter writer) {
        for (EntitySnapshotProcessor<?> entitySnapshotProcessor : this.processors) {
            if (entitySnapshotProcessor.supports(entity)) {
                entitySnapshotProcessor.process(entity, partialTicks, writer);
            }
        }
    }

    public <T, S extends LabySnapshot> void captureSnapshot(ExtrasWriter writer, ExtraKey<S> key, T t) {
        writer.put(key, takeSnapshot(key, t));
    }

    public <T, S extends LabySnapshot> S takeSnapshot(ExtraKey<S> extraKey, T t) {
        LabySnapshotFactory labySnapshotFactory = (LabySnapshotFactory) this.factories.get(extraKey);
        if (labySnapshotFactory == null) {
            return null;
        }
        return (S) labySnapshotFactory.takeSnapshot(t);
    }

    private void registerDependency(Class<?> cls, Object instance) {
        if (instance == null) {
            LOGGER.error("Tried to register a null dependency for class: {}", cls.getName());
        } else {
            this.dependencies.put(cls, instance);
        }
    }

    private void populateEntityProcessors(ClassLoader classLoader) {
        Iterable<EntitySnapshotProcessor> processors = loadServiceInstances(EntitySnapshotProcessor.class, classLoader);
        Iterator<EntitySnapshotProcessor> it = processors.iterator();
        while (it.hasNext()) {
            this.processors.add(it.next());
        }
    }

    private void populateSnapshotFactories(ClassLoader classLoader) {
        Iterable<LabySnapshotFactory> factories = loadServiceInstances(LabySnapshotFactory.class, classLoader);
        for (LabySnapshotFactory factory : factories) {
            this.factories.put(factory.extraKey(), factory);
        }
    }

    private <T> Iterable<T> loadServiceInstances(Class<T> serviceClass, ClassLoader classLoader) {
        Optional<LoadedAddon> addon = Laby.labyAPI().addonService().getAddon(classLoader);
        if (addon.isPresent()) {
            LoadedAddon loadedAddon = addon.get();
            Object addonInstance = loadedAddon.getInstance();
            if (addonInstance == null) {
                return Collections.emptyList();
            }
            registerDependency(loadedAddon.getMainClass(), addonInstance);
        }
        return CustomServiceLoader.load(serviceClass, classLoader, CustomServiceLoader.ServiceType.ADVANCED, new DependencyInjectingCreator(this.dependencies));
    }
}

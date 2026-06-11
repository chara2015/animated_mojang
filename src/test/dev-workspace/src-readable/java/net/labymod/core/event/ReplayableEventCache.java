package net.labymod.core.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.api.event.method.ListenerRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/ReplayableEventCache.class */
public class ReplayableEventCache {
    private final Map<Class<?>, Event> globalEvents = new ConcurrentHashMap();
    private final Map<ClassLoader, Map<Class<?>, Event>> loaderEvents = new ConcurrentHashMap();
    private final Map<Class<?>, Boolean> replayableFlagCache = new ConcurrentHashMap();

    public <T extends Event> void cacheEventIfReplayable(@Nullable ClassLoader loader, @NotNull Class<?> eventClass, @NotNull T event) {
        if (!isReplayable(eventClass)) {
            return;
        }
        if (loader == null) {
            this.globalEvents.put(eventClass, event);
        } else {
            this.loaderEvents.computeIfAbsent(loader, k -> {
                return new ConcurrentHashMap();
            }).put(eventClass, event);
        }
    }

    private boolean isReplayable(Class<?> eventClass) {
        return this.replayableFlagCache.computeIfAbsent(eventClass, clazz -> {
            return Boolean.valueOf(clazz.isAnnotationPresent(ReplayableEvent.class));
        }).booleanValue();
    }

    public void replayCachedEvents(ListenerRegistry listenerRegistry) {
        invokeListeners(listenerRegistry, null, this.globalEvents);
        for (Map.Entry<ClassLoader, Map<Class<?>, Event>> loaderEntry : this.loaderEvents.entrySet()) {
            ClassLoader loader = loaderEntry.getKey();
            invokeListeners(listenerRegistry, loader, loaderEntry.getValue());
        }
    }

    private void invokeListeners(ListenerRegistry registry, ClassLoader loader, Map<Class<?>, Event> events) {
        for (Map.Entry<Class<?>, Event> entry : events.entrySet()) {
            invokeListeners(registry, loader, entry.getKey(), entry.getValue());
        }
    }

    private void invokeListeners(ListenerRegistry registry, ClassLoader loader, Class<?> eventClass, Event event) {
        registry.invokeListeners(loader, eventClass, event);
    }
}

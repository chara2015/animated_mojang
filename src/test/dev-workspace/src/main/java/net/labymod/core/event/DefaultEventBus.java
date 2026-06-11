package net.labymod.core.event;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.Event;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.method.SubscribeMethodList;
import net.labymod.api.event.method.SubscribeMethodResolver;
import net.labymod.api.models.Implements;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.event.method.DefaultListenerRegistry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/DefaultEventBus.class */
@Singleton
@Implements(EventBus.class)
public class DefaultEventBus implements EventBus {
    private final SubscribeMethodResolver methodResolver;
    private final Set<Object> registeredListeners = ConcurrentHashMap.newKeySet();
    private final ListenerRegistry listenerRegistry = new DefaultListenerRegistry();
    private final ReplayableEventCache replayableEventCache = new ReplayableEventCache();

    @Inject
    public DefaultEventBus(SubscribeMethodResolver methodResolver) {
        this.methodResolver = methodResolver;
    }

    @Override // net.labymod.api.event.EventBus
    public <T extends Event> void fire(T event) {
        fire(null, event);
    }

    @Override // net.labymod.api.event.EventBus
    public <T extends Event> void fire(ClassLoader classLoader, T event) {
        if (event == null) {
            throw new NullPointerException("The event which should be fired is null!");
        }
        Class<?> eventClass = event.getClass();
        this.replayableEventCache.cacheEventIfReplayable(classLoader, eventClass, event);
        if (!this.listenerRegistry.invokeListeners(classLoader, eventClass, event)) {
            return;
        }
        invokeActivityListeners(classLoader, eventClass, event);
    }

    private void invokeActivityListeners(ClassLoader classLoader, Class<?> eventClass, Event event) {
        Activity[] activities = Laby.references().activityController().getOpenActivities();
        if (activities.length == 0) {
            return;
        }
        boolean anyListens = false;
        int length = activities.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Activity activity = activities[i];
            if (!activity.listenForEvents() || !activity.listenerRegistry().hasListeners(eventClass)) {
                i++;
            } else {
                anyListens = true;
                break;
            }
        }
        if (!anyListens) {
            return;
        }
        if (ThreadSafe.isRenderThread()) {
            invokeActivityListenersDirect(classLoader, eventClass, event);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                invokeActivityListenersDirect(classLoader, eventClass, event);
            });
        }
    }

    private void invokeActivityListenersDirect(ClassLoader classLoader, Class<?> eventClass, Event event) {
        Activity[] activities = Laby.references().activityController().getOpenActivities();
        for (Activity openActivity : activities) {
            if (openActivity.listenForEvents() && !openActivity.listenerRegistry().invokeListeners(classLoader, eventClass, event)) {
                return;
            }
        }
    }

    @Override // net.labymod.api.event.EventBus
    public void registerListener(Object listener) {
        if (listener == null) {
            throw new IllegalStateException("The listener cannot be registered because it is null");
        }
        ListenerRegistry listeners = this.methodResolver.resolve(listener);
        this.listenerRegistry.merge(listeners);
        this.registeredListeners.add(listener);
        this.replayableEventCache.replayCachedEvents(listeners);
    }

    @Override // net.labymod.api.event.EventBus
    public boolean isListenerRegistered(Object listener) {
        return this.registeredListeners.contains(listener);
    }

    @Override // net.labymod.api.event.EventBus
    public boolean hasListeners(Class<? extends Event> eventClass) {
        if (this.listenerRegistry.hasListeners(eventClass)) {
            return true;
        }
        for (Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            if (openActivity.listenForEvents() && openActivity.listenerRegistry().hasListeners(eventClass)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.event.EventBus
    public void unregisterListener(Object listener) {
        AddonClassLoader classLoader = getClassLoader(listener);
        for (SubscribeMethodList list : this.listenerRegistry.getListeners().values()) {
            list.removeIf(subscribeMethod -> {
                if (listener != subscribeMethod.getListener()) {
                    return false;
                }
                if (classLoader == null || subscribeMethod.getClassLoader() == null) {
                    return classLoader == subscribeMethod.getClassLoader();
                }
                InstalledAddonInfo methodAddonInfo = ((AddonClassLoader) subscribeMethod.getClassLoader()).getAddonInfo();
                InstalledAddonInfo addonInfo = classLoader.getAddonInfo();
                return addonInfo.getNamespace().equals(methodAddonInfo.getNamespace());
            });
        }
        this.registeredListeners.remove(listener);
    }

    @Override // net.labymod.api.event.EventBus
    public void unregisterListeners(Object addon) {
        AddonClassLoader addonClassLoader = getClassLoader(addon);
        if (addonClassLoader == null) {
            throw new IllegalStateException(String.format(Locale.ROOT, "The class \"%s\" was not loaded by any AddonClassLoader and therefore the listener cannot be registered.", addon.getClass().getName()));
        }
        String addonNamespace = addonClassLoader.getAddonInfo().getNamespace();
        for (SubscribeMethodList list : this.listenerRegistry.getListeners().values()) {
            list.removeIf(subscribeMethod -> {
                ClassLoader patt0$temp = subscribeMethod.getClassLoader();
                if (!(patt0$temp instanceof AddonClassLoader)) {
                    return false;
                }
                AddonClassLoader methodClassLoader = (AddonClassLoader) patt0$temp;
                return methodClassLoader.getAddonInfo().getNamespace().equals(addonNamespace);
            });
        }
        this.registeredListeners.removeIf(registeredListener -> {
            ClassLoader cl = registeredListener.getClass().getClassLoader();
            if (!(cl instanceof AddonClassLoader)) {
                return false;
            }
            AddonClassLoader listenerClassLoader = (AddonClassLoader) cl;
            return listenerClassLoader.getAddonInfo().getNamespace().equals(addonNamespace);
        });
    }

    @Nullable
    private AddonClassLoader getClassLoader(@Nullable Object addon) {
        if (addon == null) {
            throw new IllegalStateException("No AddonClassLoader was found because the Addon is null");
        }
        ClassLoader classLoader = addon.getClass().getClassLoader();
        if (!(classLoader instanceof AddonClassLoader)) {
            return null;
        }
        return (AddonClassLoader) classLoader;
    }

    @Override // net.labymod.api.event.EventBus
    public ListenerRegistry registry() {
        return this.listenerRegistry;
    }
}

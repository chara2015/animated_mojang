package net.labymod.core.event.method;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.Event;
import net.labymod.api.event.EventInterruptException;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.labymod.SubscribeMethodRegisterEvent;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.method.SubscribeMethodList;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/DefaultListenerRegistry.class */
public class DefaultListenerRegistry implements ListenerRegistry {
    private static final DefaultAddonService ADDON_SERVICE = DefaultAddonService.getInstance();
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) DefaultListenerRegistry.class);
    private final Map<Class<?>, SubscribeMethodList> listeners = new ConcurrentHashMap();

    @Override // net.labymod.api.event.method.ListenerRegistry
    public Map<Class<?>, SubscribeMethodList> getListeners() {
        return this.listeners;
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public boolean hasListeners(Class<?> eventClass) {
        SubscribeMethodList methods = this.listeners.get(eventClass);
        return (methods == null || methods.isEmpty()) ? false : true;
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public boolean invokeListeners(ClassLoader classLoader, Class<?> eventClass, Event event) {
        SubscribeMethodList list;
        if (this.listeners.isEmpty() || (list = this.listeners.get(eventClass)) == null || list.isEmpty()) {
            return true;
        }
        SubscribeMethod[] methods = list.getSubscribeMethods();
        for (SubscribeMethod method : methods) {
            if ((classLoader == null || method.isInClassLoader(classLoader)) && (classLoader != null || !method.classLoaderExclusive())) {
                event.preInvoke(method);
                try {
                    invokeMethod(null, method, eventClass, event);
                    event.postInvoke(method);
                } catch (EventInterruptException e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public void invokeMethod(SubscribeMethod method, Class<?> eventClass, Event event) {
        invokeMethod(method.getLabyEvent(), method, eventClass, event);
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public void register(SubscribeMethod method) {
        registerInternal(method);
        Laby.fireEvent(new SubscribeMethodRegisterEvent(this, method));
    }

    public void registerInternal(SubscribeMethod method) {
        SubscribeMethodList methods = getMethods(method.getEventType());
        methods.add(method);
        methods.sort();
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public void unregister(SubscribeMethod method) {
        SubscribeMethodList methods = this.listeners.get(method.getEventType());
        if (methods == null) {
            return;
        }
        methods.remove(method);
    }

    @Override // net.labymod.api.event.method.ListenerRegistry
    public void merge(ListenerRegistry registry) {
        for (Map.Entry<Class<?>, SubscribeMethodList> entry : registry.getListeners().entrySet()) {
            Class<?> eventClass = entry.getKey();
            SubscribeMethodList methods = getMethods(eventClass);
            methods.mergeSort(entry.getValue());
            for (SubscribeMethod method : entry.getValue().getSubscribeMethods()) {
                Laby.fireEvent(new SubscribeMethodRegisterEvent(this, method));
            }
        }
    }

    private SubscribeMethodList getMethods(Class<?> eventClass) {
        return this.listeners.computeIfAbsent(eventClass, list -> {
            return new DefaultSubscribeMethodList();
        });
    }

    private void invokeMethod(LabyEvent labyEvent, SubscribeMethod method, Class<?> eventClass, Event event) {
        String name;
        LoadedAddon loadedAddon;
        InstalledAddonInfo addon = method.getAddon();
        if (addon != null && !method.background() && !ADDON_SERVICE.isEnabled(addon, true)) {
            return;
        }
        try {
            method.invoke(event);
        } catch (EventInterruptException exception) {
            throw exception;
        } catch (Throwable throwable) {
            if (labyEvent == null) {
                labyEvent = method.getLabyEvent();
            }
            if (labyEvent != null) {
                if (labyEvent.allowAllExceptions()) {
                    throw new RuntimeException(throwable);
                }
                for (Class<? extends RuntimeException> allowedException : labyEvent.allowExceptions()) {
                    if (allowedException.isInstance(throwable)) {
                        throw ((RuntimeException) throwable);
                    }
                }
            }
            LabyModLoader loader = Laby.labyAPI().labyModLoader();
            if (method.getListener() != null) {
                name = method.getListener().getClass().getName();
            } else {
                name = "<no listener>";
            }
            String listenerName = name;
            String eventName = event.getClass().getName();
            if (loader.isLabyModDevelopmentEnvironment()) {
                if (!loader.isAddonDevelopmentEnvironment() && addon == null) {
                    IdeUtil.doPauseOrCrashGame(String.format(Locale.ROOT, "Failed to handle %s listener %s for event %s", Constants.Branding.NAME, listenerName, eventName), throwable);
                    return;
                } else if (loader.isAddonDevelopmentEnvironment() && addon != null && (loadedAddon = Laby.labyAPI().addonService().getAddon(addon.getNamespace()).orElse(null)) != null && loadedAddon.isClasspath()) {
                    IdeUtil.doPauseOrCrashGame(String.format(Locale.ROOT, "Failed to handle listener %s for event %s from addon %s", listenerName, eventName, addon.getDisplayName()), throwable);
                    return;
                }
            }
            if (addon != null) {
                LOGGER.error("Failed to handle listener {} for event {} from addon {}", method.getListener().getClass().getName(), event.getClass().getName(), addon.getDisplayName(), throwable);
            } else {
                LOGGER.error("Failed to handle {} listener {} for event {}", Constants.Branding.NAME, method.getListener().getClass().getName(), event.getClass().getName(), throwable);
            }
        }
    }
}

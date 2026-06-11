package net.labymod.api.event.method;

import java.util.Map;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/method/ListenerRegistry.class */
public interface ListenerRegistry {
    Map<Class<?>, SubscribeMethodList> getListeners();

    boolean hasListeners(Class<?> cls);

    boolean invokeListeners(ClassLoader classLoader, Class<?> cls, Event event);

    void invokeMethod(SubscribeMethod subscribeMethod, Class<?> cls, Event event);

    void register(SubscribeMethod subscribeMethod);

    void unregister(SubscribeMethod subscribeMethod);

    void merge(ListenerRegistry listenerRegistry);

    default void invokeMethod(SubscribeMethod method, Event event) {
        invokeMethod(method, method.getEventType(), event);
    }
}

package net.labymod.api.event.labymod;

import net.labymod.api.event.Event;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.util.CrossVersionServiceLoader;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/ServiceLoadEvent.class */
public class ServiceLoadEvent implements Event {
    private final ClassLoader classLoader;
    private final State state;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/ServiceLoadEvent$State.class */
    public enum State {
        START,
        ADDON_LOADED,
        LISTENER_REGISTERED
    }

    public ServiceLoadEvent(@NotNull ClassLoader classLoader, @NotNull State state) {
        this.classLoader = classLoader;
        this.state = state;
    }

    @NotNull
    public ClassLoader classLoader() {
        return this.classLoader;
    }

    @NotNull
    public State state() {
        return this.state;
    }

    public <T> CustomServiceLoader<T> load(Class<T> serviceClass, CustomServiceLoader.ServiceType serviceType) {
        return CustomServiceLoader.load(serviceClass, this.classLoader, serviceType);
    }

    public <T> CustomServiceLoader<T> load(Class<T> serviceClass, CustomServiceLoader.ServiceType serviceType, CustomServiceLoader.ConstructorCreator<T> constructorCreator) {
        return CustomServiceLoader.load(serviceClass, this.classLoader, serviceType, constructorCreator);
    }

    @Deprecated
    public <T> CrossVersionServiceLoader<T> load(Class<T> serviceClass) {
        return CrossVersionServiceLoader.load(serviceClass, this.classLoader);
    }
}

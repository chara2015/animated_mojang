package net.labymod.api.event.method;

import java.lang.reflect.Method;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/method/SubscribeMethod.class */
public interface SubscribeMethod {
    void invoke(Event event) throws Throwable;

    @Nullable
    ClassLoader getClassLoader();

    @Nullable
    InstalledAddonInfo getAddon();

    @Nullable
    Object getListener();

    byte getPriority();

    @Nullable
    Method getMethod();

    @NotNull
    Class<?> getEventType();

    @Nullable
    LabyEvent getLabyEvent();

    boolean isInClassLoader(ClassLoader classLoader);

    SubscribeMethod copy(Object obj);

    default boolean classLoaderExclusive() {
        LabyEvent labyEvent = getLabyEvent();
        return labyEvent != null && labyEvent.classLoaderExclusive();
    }

    default boolean background() {
        LabyEvent labyEvent = getLabyEvent();
        return labyEvent != null && labyEvent.background();
    }
}

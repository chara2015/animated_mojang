package net.labymod.api.event.method;

import java.util.function.Consumer;
import net.labymod.api.event.Event;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/method/SubscribeMethodResolver.class */
@Referenceable
public interface SubscribeMethodResolver {
    ListenerRegistry resolve(@NotNull Object obj);

    SubscribeMethod createCustom(byte b, Class<?> cls, @NotNull Consumer<Event> consumer);

    SubscribeMethod createCustom(ClassLoader classLoader, byte b, Class<?> cls, @NotNull Consumer<Event> consumer);
}

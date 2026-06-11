package net.labymod.api.event;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/EventBus.class */
@Referenceable
public interface EventBus {
    <T extends Event> void fire(T t);

    <T extends Event> void fire(ClassLoader classLoader, T t);

    void registerListener(Object obj);

    void unregisterListener(Object obj);

    void unregisterListeners(Object obj);

    boolean isListenerRegistered(Object obj);

    boolean hasListeners(Class<? extends Event> cls);

    ListenerRegistry registry();

    default <T extends Event> void fireNextTick(@NotNull T event) {
        fireNextTick(event, null);
    }

    default <T extends Event> void fireNextTick(@NotNull T event, @Nullable Consumer<T> eventConsumer) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            fire(event);
            if (eventConsumer != null) {
                eventConsumer.accept(event);
                return;
            }
            return;
        }
        minecraft.executeNextTick(() -> {
            fire(event);
            if (eventConsumer != null) {
                eventConsumer.accept(event);
            }
        });
    }
}

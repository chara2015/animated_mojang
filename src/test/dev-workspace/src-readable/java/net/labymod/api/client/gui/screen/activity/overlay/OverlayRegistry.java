package net.labymod.api.client.gui.screen.activity.overlay;

import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.overlay.initializer.EmptyScreenInitializer;
import net.labymod.api.client.gui.screen.activity.overlay.initializer.InstanceScreenInitializer;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/overlay/OverlayRegistry.class */
@Referenceable
public interface OverlayRegistry extends Registry<RegisteredReplacement> {
    Activity toOverlay(ScreenInstance screenInstance);

    Object toRawScreen(Activity activity);

    default void register(String id, Class<?> clazz, InstanceScreenInitializer initializer) {
        register(id, new RegisteredReplacement(clazz, initializer));
    }

    default void register(GameScreen screen, Class<?> clazz, InstanceScreenInitializer initializer) {
        register(screen.getId(), clazz, initializer);
    }

    default void register(NamedScreen screen, Class<?> clazz, InstanceScreenInitializer initializer) {
        register((GameScreen) screen, clazz, initializer);
    }

    default void register(String id, Class<?> clazz, EmptyScreenInitializer initializer) {
        register(id, clazz, parentScreen -> {
            return initializer.create();
        });
    }

    default void register(GameScreen screen, Class<?> clazz, EmptyScreenInitializer initializer) {
        register(screen.getId(), clazz, initializer);
    }

    default void register(NamedScreen screen, Class<?> clazz, EmptyScreenInitializer initializer) {
        register((GameScreen) screen, clazz, initializer);
    }

    default RegisteredReplacement get(GameScreen screen) {
        return getById(screen.getId());
    }

    default RegisteredReplacement get(NamedScreen screen) {
        return get((GameScreen) screen);
    }
}

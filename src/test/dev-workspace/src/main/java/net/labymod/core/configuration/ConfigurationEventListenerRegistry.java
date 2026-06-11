package net.labymod.core.configuration;

import net.labymod.api.Laby;
import net.labymod.api.event.EventBus;
import net.labymod.core.configuration.loader.serial.JsonConfigLoaderInitializeListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/ConfigurationEventListenerRegistry.class */
public final class ConfigurationEventListenerRegistry {
    private static boolean registered;

    private ConfigurationEventListenerRegistry() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;
        EventBus eventBus = Laby.references().eventBus();
        eventBus.registerListener(new JsonConfigLoaderInitializeListener());
        eventBus.registerListener(new ConfigurationVersionUpdateListener());
    }
}

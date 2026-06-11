package net.labymod.api.client.resources;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/ResourcesReloadWatcher.class */
@Referenceable
public interface ResourcesReloadWatcher {
    boolean isInitialized();

    void addInitializeListener(Runnable runnable, boolean z);

    default void addOrExecuteInitializeListener(Runnable task) {
        if (isInitialized()) {
            task.run();
        } else {
            addInitializeListener(task);
        }
    }

    default void addInitializeListener(Runnable task) {
        addInitializeListener(task, false);
    }
}

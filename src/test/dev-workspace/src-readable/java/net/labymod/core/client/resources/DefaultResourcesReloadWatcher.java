package net.labymod.core.client.resources;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.client.resources.transform.RegisterResourceTransformerEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultResourcesReloadWatcher.class */
@Singleton
@Implements(ResourcesReloadWatcher.class)
public class DefaultResourcesReloadWatcher implements ResourcesReloadWatcher {
    private final ResourceTransformerRegistry transformerRegistry;
    private final List<Runnable> tasks = new ArrayList();
    private boolean initialized;

    @Inject
    public DefaultResourcesReloadWatcher(ResourceTransformerRegistry transformerRegistry) {
        this.transformerRegistry = transformerRegistry;
    }

    @Override // net.labymod.api.client.resources.ResourcesReloadWatcher
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override // net.labymod.api.client.resources.ResourcesReloadWatcher
    public void addInitializeListener(Runnable task, boolean skipCollection) {
        if (skipCollection && this.initialized) {
            task.run();
            return;
        }
        this.tasks.add(task);
        if (this.initialized) {
            task.run();
        }
    }

    @Subscribe(-127)
    public void executeTasks(ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        IdeUtil.setResourcesLoaded(true);
        this.initialized = true;
        this.tasks.forEach((v0) -> {
            v0.run();
        });
        this.tasks.clear();
    }

    @Subscribe
    public void registerResourceTransformerOnStart(GameInitializeEvent event) {
        if (event.getLifecycle() == GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION) {
            return;
        }
        ((DefaultResourceTransformerRegistry) this.transformerRegistry).clear();
        Laby.fireEvent(new RegisterResourceTransformerEvent(this.transformerRegistry));
    }

    @Subscribe
    public void registerResourceTransformerOnReload(ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        ((DefaultResourceTransformerRegistry) this.transformerRegistry).clear();
        Laby.fireEvent(new RegisterResourceTransformerEvent(this.transformerRegistry));
    }
}

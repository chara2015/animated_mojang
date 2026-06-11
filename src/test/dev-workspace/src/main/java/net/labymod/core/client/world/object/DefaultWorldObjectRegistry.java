package net.labymod.core.client.world.object;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.client.world.object.WorldObjectRegistry;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/object/DefaultWorldObjectRegistry.class */
@Singleton
@Implements(WorldObjectRegistry.class)
public class DefaultWorldObjectRegistry extends DefaultRegistry<WorldObject> implements WorldObjectRegistry {
    private final IngameActivityOverlay activityOverlay;
    private WorldObjectOverlay overlay;

    public DefaultWorldObjectRegistry(@NotNull EventBus eventBus, @NotNull IngameActivityOverlay activityOverlay) {
        this.activityOverlay = activityOverlay;
        eventBus.registerListener(this);
    }

    @Override // net.labymod.api.service.DefaultRegistry
    protected void onRegister(@NotNull KeyValue<WorldObject> value) {
        ThreadSafe.ensureRenderThread();
        worldObjectOverlay().addObject(value.getValue());
    }

    @Override // net.labymod.api.service.DefaultRegistry
    protected void onUnregister(@NotNull KeyValue<WorldObject> value) {
        ThreadSafe.ensureRenderThread();
        worldObjectOverlay().removeObject(value.getValue());
    }

    private WorldObjectOverlay worldObjectOverlay() {
        if (this.overlay == null) {
            this.overlay = new WorldObjectOverlay();
            this.activityOverlay.registerActivity(this.overlay);
        }
        return this.overlay;
    }
}

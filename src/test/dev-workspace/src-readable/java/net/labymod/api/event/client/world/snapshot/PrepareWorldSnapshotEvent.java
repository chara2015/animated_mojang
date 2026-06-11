package net.labymod.api.event.client.world.snapshot;

import net.labymod.api.client.render.state.world.WorldSnapshot;
import net.labymod.api.client.render.state.world.WorldSnapshotContext;
import net.labymod.api.event.Event;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/snapshot/PrepareWorldSnapshotEvent.class */
public final class PrepareWorldSnapshotEvent implements Event {
    private final WorldSnapshot.Builder builder;
    private final WorldSnapshotContext context;

    public PrepareWorldSnapshotEvent(@NotNull WorldSnapshot.Builder builder, @NotNull WorldSnapshotContext context) {
        this.builder = builder;
        this.context = context;
    }

    @NotNull
    public WorldSnapshotContext context() {
        return this.context;
    }

    @NotNull
    public <T> PrepareWorldSnapshotEvent put(@NotNull ExtraKey<T> key, @NotNull T value) {
        this.builder.put(key, value);
        return this;
    }
}

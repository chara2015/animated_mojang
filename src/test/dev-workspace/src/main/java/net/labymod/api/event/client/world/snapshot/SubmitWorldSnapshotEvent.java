package net.labymod.api.event.client.world.snapshot;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.WorldSnapshot;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/snapshot/SubmitWorldSnapshotEvent.class */
public final class SubmitWorldSnapshotEvent implements Event {
    private final Phase phase;
    private final WorldSnapshot snapshot;
    private final Stack stack;
    private final SubmissionCollector collector;

    public SubmitWorldSnapshotEvent(@NotNull Phase phase, @NotNull WorldSnapshot snapshot, @NotNull Stack stack, @NotNull SubmissionCollector collector) {
        this.phase = phase;
        this.snapshot = snapshot;
        this.stack = stack;
        this.collector = collector;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }

    @NotNull
    public WorldSnapshot snapshot() {
        return this.snapshot;
    }

    @NotNull
    public Stack stack() {
        return this.stack;
    }

    @NotNull
    public SubmissionCollector collector() {
        return this.collector;
    }

    @Nullable
    public <T> T get(@NotNull ExtraKey<T> extraKey) {
        return (T) this.snapshot.get(extraKey);
    }

    public boolean has(@NotNull ExtraKey<?> key) {
        return this.snapshot.has(key);
    }
}

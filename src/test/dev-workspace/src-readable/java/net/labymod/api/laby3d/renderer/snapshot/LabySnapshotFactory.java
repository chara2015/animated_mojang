package net.labymod.api.laby3d.renderer.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/LabySnapshotFactory.class */
public abstract class LabySnapshotFactory<T, S extends LabySnapshot> {
    private final ExtraKey<S> extraKey;

    protected abstract S create(T t, Extras extras);

    public LabySnapshotFactory(ExtraKey<S> extraKey) {
        this.extraKey = extraKey;
    }

    public final S takeSnapshot(T t) {
        Extras.Builder builder = Extras.builder();
        writeExtras(t, builder);
        return (S) create(t, builder.build());
    }

    protected void writeExtras(T t, ExtrasWriter writer) {
    }

    public ExtraKey<S> extraKey() {
        return this.extraKey;
    }
}

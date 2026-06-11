package net.labymod.api.laby3d.renderer.snapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/AbstractLabySnapshot.class */
public abstract class AbstractLabySnapshot implements LabySnapshot {
    private final Extras extras;

    protected AbstractLabySnapshot(Extras extras) {
        this.extras = extras;
    }

    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshot
    public Extras extras() {
        return this.extras;
    }
}

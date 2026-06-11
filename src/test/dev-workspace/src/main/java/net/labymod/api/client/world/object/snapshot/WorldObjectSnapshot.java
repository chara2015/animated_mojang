package net.labymod.api.client.world.object.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/snapshot/WorldObjectSnapshot.class */
public interface WorldObjectSnapshot extends LabySnapshot {
    double x();

    double y();

    double z();

    int lightCoords();
}

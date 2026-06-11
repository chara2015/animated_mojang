package net.labymod.api.client.render.state.entity;

import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/EntitySnapshot.class */
public interface EntitySnapshot extends LabySnapshot {
    double x();

    double y();

    double z();

    float eyeHeight();

    boolean isInvisible();

    boolean isDiscrete();

    int lightCoords();
}

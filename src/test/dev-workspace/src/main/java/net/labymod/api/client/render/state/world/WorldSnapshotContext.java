package net.labymod.api.client.render.state.world;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.util.Frustum;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/world/WorldSnapshotContext.class */
public interface WorldSnapshotContext {
    @NotNull
    Frustum frustum();

    @NotNull
    CameraSnapshot camera();

    float partialTicks();

    @NotNull
    Stack stack();

    int getLightCoords(double d, double d2, double d3);
}

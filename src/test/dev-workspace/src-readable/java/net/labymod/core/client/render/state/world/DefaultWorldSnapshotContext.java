package net.labymod.core.client.render.state.world;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.render.state.world.WorldSnapshotContext;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.laby3d.util.Frustum;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/world/DefaultWorldSnapshotContext.class */
public class DefaultWorldSnapshotContext implements WorldSnapshotContext {
    private final Frustum frustum;
    private final CameraSnapshot camera;
    private final Stack stack;
    private final float partialTicks;
    private final ClientWorld level = Laby.references().clientWorld();

    public DefaultWorldSnapshotContext(@NotNull Frustum frustum, @NotNull CameraSnapshot camera, @NotNull Stack stack, float partialTicks) {
        this.frustum = frustum;
        this.camera = camera;
        this.stack = stack;
        this.partialTicks = partialTicks;
    }

    @Override // net.labymod.api.client.render.state.world.WorldSnapshotContext
    @NotNull
    public Frustum frustum() {
        return this.frustum;
    }

    @Override // net.labymod.api.client.render.state.world.WorldSnapshotContext
    @NotNull
    public CameraSnapshot camera() {
        return this.camera;
    }

    @Override // net.labymod.api.client.render.state.world.WorldSnapshotContext
    public float partialTicks() {
        return this.partialTicks;
    }

    @Override // net.labymod.api.client.render.state.world.WorldSnapshotContext
    @NotNull
    public Stack stack() {
        return this.stack;
    }

    @Override // net.labymod.api.client.render.state.world.WorldSnapshotContext
    public int getLightCoords(double x, double y, double z) {
        return this.level.getPackedLight(x, y, z);
    }
}

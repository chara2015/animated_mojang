package net.labymod.api.client.render.state.world;

import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/world/CameraSnapshot.class */
public class CameraSnapshot extends AbstractLabySnapshot {
    private final DoubleVector3 position;
    private final DoubleVector3 renderPosition;
    private final Quaternionf rotation;
    private final float yaw;
    private final float pitch;

    public CameraSnapshot(@NotNull MinecraftCamera camera) {
        super(Extras.empty());
        this.position = new DoubleVector3(camera.position());
        this.renderPosition = new DoubleVector3(camera.renderPosition());
        this.rotation = new Quaternionf(camera.cameraRotation());
        this.yaw = camera.getYaw();
        this.pitch = camera.getPitch();
    }

    @NotNull
    public DoubleVector3 position() {
        return this.position;
    }

    @NotNull
    public DoubleVector3 renderPosition() {
        return this.renderPosition;
    }

    @NotNull
    public Quaternionf rotation() {
        return this.rotation;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }
}

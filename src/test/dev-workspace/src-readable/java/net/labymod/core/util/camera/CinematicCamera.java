package net.labymod.core.util.camera;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.util.camera.spline.Spline;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.laby3d.api.math.Axis;
import net.labymod.laby3d.api.pipeline.DepthConventionHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/camera/CinematicCamera.class */
public class CinematicCamera implements MinecraftCamera {
    private final Spline spline;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f previousProjectionMatrix;
    private final Matrix4f previousViewMatrix;
    private final Int2ObjectMap<Location> positionModifiers;
    private final float fov;
    private final float near;
    private final float far;
    private CubicBezier cubicBezier;
    private long timePassed;
    private long duration;

    public CinematicCamera(float fov) {
        this(fov, 0.05f, 1024.0f);
    }

    public CinematicCamera(float fov, float near, float far) {
        this.spline = new Spline();
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.previousProjectionMatrix = new Matrix4f();
        this.previousViewMatrix = new Matrix4f();
        this.positionModifiers = new Int2ObjectOpenHashMap();
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    public void teleport(Location position) {
        moveTo(0L, CubicBezier.LINEAR, position);
    }

    public void restart() {
        this.timePassed = 0L;
    }

    public void moveTo(long duration, CubicBezier cubicBezier, Location... keyframes) {
        Location source = location();
        this.spline.reset();
        this.spline.add(source);
        for (Location position : keyframes) {
            this.spline.add(position);
        }
        this.spline.calculate();
        this.timePassed = 0L;
        this.duration = duration;
        this.cubicBezier = cubicBezier;
    }

    public void setup(float left, float top, float right, float bottom, float partialTicks) {
        this.previousViewMatrix.set(this.viewMatrix);
        this.viewMatrix.identity();
        Location location = location();
        ObjectIterator it = this.positionModifiers.values().iterator();
        while (it.hasNext()) {
            Location modification = (Location) it.next();
            modifyViewMatrixRotationRoll(this.viewMatrix, modification);
        }
        modifyViewMatrixRotationRoll(this.viewMatrix, location);
        ObjectIterator it2 = this.positionModifiers.values().iterator();
        while (it2.hasNext()) {
            Location modification2 = (Location) it2.next();
            modifyViewMatrixRotationPitch(this.viewMatrix, modification2);
        }
        modifyViewMatrixRotationPitch(this.viewMatrix, location);
        ObjectIterator it3 = this.positionModifiers.values().iterator();
        while (it3.hasNext()) {
            Location modification3 = (Location) it3.next();
            modifyViewMatrixRotationYaw(this.viewMatrix, modification3, false);
        }
        modifyViewMatrixRotationYaw(this.viewMatrix, location, true);
        ObjectIterator it4 = this.positionModifiers.values().iterator();
        while (it4.hasNext()) {
            Location modification4 = (Location) it4.next();
            modifyViewMatrixPosition(this.viewMatrix, modification4, false);
        }
        modifyViewMatrixPosition(this.viewMatrix, location, true);
        float width = right - left;
        float height = bottom - top;
        float aspect = width / height;
        this.previousProjectionMatrix.set(this.projectionMatrix);
        float near = this.near;
        float far = this.far;
        boolean zZeroToOne = false;
        if (DepthConventionHolder.get().zZeroToOne()) {
            near = this.far;
            far = this.near;
            zZeroToOne = true;
        }
        this.projectionMatrix.setPerspective(this.fov * 0.017453292f, aspect, near, far, zZeroToOne);
    }

    private void modifyViewMatrixPosition(Matrix4f viewMatrix, Location position, boolean main) {
        float x = (float) position.getX();
        float y = (float) position.getY();
        float z = (float) position.getZ();
        if (main) {
            x *= -1.0f;
            y *= -1.0f;
            z *= -1.0f;
        }
        viewMatrix.translate(x, y, z);
    }

    private void modifyViewMatrixRotation(Matrix4f viewMatrix, Location position, boolean main) {
        float roll = (float) position.getRoll();
        float pitch = (float) position.getPitch();
        float yaw = (float) position.getYaw();
        viewMatrix.rotate(Axis.ZP.rotationDegrees(roll));
        viewMatrix.rotate(Axis.XP.rotationDegrees(pitch));
        viewMatrix.rotate(Axis.YP.rotationDegrees(yaw));
        if (main) {
            viewMatrix.rotate(Axis.YP.rotationDegrees(180.0f));
        }
    }

    private void modifyViewMatrixRotationYaw(Matrix4f viewMatrix, Location position, boolean main) {
        viewMatrix.rotate(Axis.YP.rotationDegrees((float) position.getYaw()));
        if (main) {
            viewMatrix.rotate(Axis.YP.rotationDegrees(180.0f));
        }
    }

    private void modifyViewMatrixRotationPitch(Matrix4f viewMatrix, Location position) {
        viewMatrix.rotate(Axis.XP.rotationDegrees((float) position.getPitch()));
    }

    private void modifyViewMatrixRotationRoll(Matrix4f viewMatrix, Location position) {
        viewMatrix.rotate(Axis.ZP.rotationDegrees((float) position.getRoll()));
    }

    public void update(float left, float top, float right, float bottom, float tickDelta) {
        this.timePassed = (long) (this.timePassed + TimeUtil.convertDeltaToMilliseconds(tickDelta));
    }

    public Matrix4f viewMatrix() {
        return this.viewMatrix;
    }

    public Matrix4f previousViewMatrix() {
        return this.previousViewMatrix;
    }

    public Matrix4f projectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f previousProjectionMatrix() {
        return this.previousProjectionMatrix;
    }

    public Location location() {
        return this.spline.isValid() ? this.spline.get(getProgress()) : new Location();
    }

    public Location positionModifier(int channel) {
        Location location = (Location) this.positionModifiers.get(channel);
        if (location == null) {
            location = new Location();
            this.positionModifiers.put(channel, location);
        }
        return location;
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public DoubleVector3 position() {
        return location().position();
    }

    @Override // net.labymod.api.client.world.MinecraftCamera
    @NotNull
    public Quaternionf cameraRotation() {
        return location().rotation();
    }

    public float getProgress() {
        float f;
        if (this.duration == 0 || this.timePassed >= this.duration) {
            f = 1.0f;
        } else {
            f = this.timePassed / this.duration;
        }
        float progress = f;
        return (float) this.cubicBezier.curve(progress);
    }

    public static float getZLevel() {
        if (MinecraftVersions.V1_21_2_pre2.orNewer()) {
            return 11000.0f;
        }
        if (MinecraftVersions.V1_21_2_pre1.orNewer()) {
            return 10000.0f;
        }
        if (MinecraftVersions.V1_20_pre3.orNewer()) {
            return 11000.0f;
        }
        if (MinecraftVersions.V1_13.orNewer()) {
            return 2000.0f;
        }
        return 0.0f;
    }
}

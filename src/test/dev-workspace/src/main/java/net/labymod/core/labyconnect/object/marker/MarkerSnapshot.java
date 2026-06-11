package net.labymod.core.labyconnect.object.marker;

import java.util.UUID;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.object.snapshot.AbstractWorldObjectSnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/marker/MarkerSnapshot.class */
public final class MarkerSnapshot extends AbstractWorldObjectSnapshot {
    private final UUID owner;
    private final CompletableResourceLocation headLocation;
    private final boolean large;
    private final float transition;
    private final float scale;
    private final float lineHeight;
    private final float lineWidth;
    private final float faceScale;
    private final float facePadding;
    private final float cameraYaw;
    private final float cameraPitch;
    private final int argb;

    public MarkerSnapshot(double x, double y, double z, int lightCoords, @NotNull UUID owner, @NotNull CompletableResourceLocation headLocation, boolean large, float transition, float scale, float lineHeight, float cameraYaw, float cameraPitch, int argb) {
        super(x, y, z, lightCoords, Extras.empty());
        this.owner = owner;
        this.headLocation = headLocation;
        this.large = large;
        this.transition = transition;
        this.scale = scale;
        this.lineHeight = lineHeight;
        this.lineWidth = 0.4f * scale;
        this.faceScale = 1.3f * scale;
        this.facePadding = 0.05f * scale;
        this.cameraYaw = cameraYaw;
        this.cameraPitch = cameraPitch;
        this.argb = argb;
    }

    @NotNull
    public UUID owner() {
        return this.owner;
    }

    @Nullable
    public ResourceLocation ownerTexture() {
        return this.headLocation.getCompleted();
    }

    public boolean large() {
        return this.large;
    }

    public float transition() {
        return this.transition;
    }

    public float scale() {
        return this.scale;
    }

    public float lineHeight() {
        return this.lineHeight;
    }

    public float lineWidth() {
        return this.lineWidth;
    }

    public float faceScale() {
        return this.faceScale;
    }

    public float facePadding() {
        return this.facePadding;
    }

    public float cameraYaw() {
        return this.cameraYaw;
    }

    public float cameraPitch() {
        return this.cameraPitch;
    }

    public int argb() {
        return this.argb;
    }
}

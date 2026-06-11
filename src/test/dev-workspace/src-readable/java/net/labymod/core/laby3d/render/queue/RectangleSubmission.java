package net.labymod.core.laby3d.render.queue;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.AbstractSubmission;
import net.labymod.api.util.color.GradientDirection;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/RectangleSubmission.class */
public class RectangleSubmission extends AbstractSubmission {
    private final Matrix4f pose;
    private final GradientDirection direction;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final int startArgb;
    private final int endArgb;
    private final int lightCoords;

    public RectangleSubmission(@NotNull Material material, @NotNull Matrix4f pose, @NotNull GradientDirection direction, float x, float y, float width, float height, int startArgb, int endArgb, int lightCoords) {
        super(material);
        this.pose = pose;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startArgb = startArgb;
        this.endArgb = endArgb;
        this.lightCoords = lightCoords;
    }

    @NotNull
    public Matrix4f pose() {
        return this.pose;
    }

    @NotNull
    public GradientDirection direction() {
        return this.direction;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public int startArgb() {
        return this.startArgb;
    }

    public int endArgb() {
        return this.endArgb;
    }

    public int lightCoords() {
        return this.lightCoords;
    }
}

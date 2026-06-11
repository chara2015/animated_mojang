package net.labymod.core.laby3d.render.queue;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.AbstractSubmission;
import net.labymod.api.laby3d.render.queue.SortHint;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/ModelSubmission.class */
public class ModelSubmission extends AbstractSubmission {
    private final Model model;
    private final Matrix4f pose;
    private final int lightCoords;
    private final int overlayCoords;

    public ModelSubmission(@NotNull Material material, @NotNull Model model, @NotNull Matrix4f pose, int lightCoords, int overlayCoords) {
        super(material);
        this.model = model;
        this.pose = pose;
        this.lightCoords = lightCoords;
        this.overlayCoords = overlayCoords;
    }

    @NotNull
    public Model model() {
        return this.model;
    }

    @NotNull
    public Matrix4f pose() {
        return this.pose;
    }

    public int lightCoords() {
        return this.lightCoords;
    }

    public int overlayCoords() {
        return this.overlayCoords;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/ModelSubmission$Translucent.class */
    public static class Translucent extends ModelSubmission implements SortHint {
        private final Vector3fc position;
        private final float depth;

        public Translucent(@NotNull ModelSubmission submission, @NotNull Vector3fc position) {
            super(submission.material(), submission.model(), submission.pose(), submission.lightCoords(), submission.overlayCoords());
            this.position = position;
            this.depth = position.lengthSquared();
        }

        @NotNull
        public Vector3fc position() {
            return this.position;
        }

        @Override // net.labymod.api.laby3d.render.queue.SortHint
        public float sortValue() {
            return -this.depth;
        }
    }
}

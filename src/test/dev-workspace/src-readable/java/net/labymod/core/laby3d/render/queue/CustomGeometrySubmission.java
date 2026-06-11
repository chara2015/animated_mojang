package net.labymod.core.laby3d.render.queue;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.AbstractSubmission;
import net.labymod.api.laby3d.render.queue.CustomGeometryRenderer;
import net.labymod.api.laby3d.render.queue.SortHint;
import org.joml.Matrix4f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/CustomGeometrySubmission.class */
public class CustomGeometrySubmission extends AbstractSubmission {
    private final Matrix4f pose;
    private final CustomGeometryRenderer renderer;

    public CustomGeometrySubmission(Material material, Matrix4f pose, CustomGeometryRenderer renderer) {
        super(material);
        this.pose = pose;
        this.renderer = renderer;
    }

    public Matrix4f pose() {
        return this.pose;
    }

    public CustomGeometryRenderer renderer() {
        return this.renderer;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/CustomGeometrySubmission$Translucent.class */
    public static class Translucent extends CustomGeometrySubmission implements SortHint {
        private final Vector3fc position;
        private final float depth;

        public Translucent(CustomGeometrySubmission submission, Vector3fc position) {
            super(submission.material(), submission.pose(), submission.renderer());
            this.position = position;
            this.depth = position.lengthSquared();
        }

        public Vector3fc position() {
            return this.position;
        }

        @Override // net.labymod.api.laby3d.render.queue.SortHint
        public float sortValue() {
            return -this.depth;
        }
    }
}

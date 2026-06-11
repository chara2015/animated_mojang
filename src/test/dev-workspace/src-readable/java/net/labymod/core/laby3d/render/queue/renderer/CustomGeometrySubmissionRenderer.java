package net.labymod.core.laby3d.render.queue.renderer;

import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.core.laby3d.render.queue.CustomGeometrySubmission;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/renderer/CustomGeometrySubmissionRenderer.class */
public class CustomGeometrySubmissionRenderer implements SubmissionRenderer<CustomGeometrySubmission> {
    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public void render(RenderBufferSource source, CustomGeometrySubmission submission) {
        VertexConsumer consumer = source.getBuffer(submission.material());
        submission.renderer().render(submission.pose(), consumer);
        GameVertexConsumer.flushPendingVertex(consumer);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public Class<CustomGeometrySubmission> type() {
        return CustomGeometrySubmission.class;
    }
}

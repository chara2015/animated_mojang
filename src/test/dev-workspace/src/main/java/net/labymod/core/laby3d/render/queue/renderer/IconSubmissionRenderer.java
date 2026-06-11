package net.labymod.core.laby3d.render.queue.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/renderer/IconSubmissionRenderer.class */
public class IconSubmissionRenderer implements SubmissionRenderer<IconSubmission>, VanillaSubmissionRenderer<IconSubmission> {
    private final ResourceRenderContext context = Laby.references().resourceRenderContext();

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public void render(RenderBufferSource source, IconSubmission submission) {
        VertexConsumer consumer = source.getBuffer(submission.material());
        renderSubmission(consumer, submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public Class<IconSubmission> type() {
        return IconSubmission.class;
    }

    @Override // net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer
    public void renderVanilla(RenderBufferSource source, IconSubmission submission) {
        VertexConsumer consumer = source.getBuffer(submission.material());
        renderSubmission(consumer, submission);
        GameVertexConsumer.flushPendingVertex(consumer);
    }

    private void renderSubmission(VertexConsumer consumer, IconSubmission submission) {
        Icon icon = submission.icon();
        this.context.begin(submission.pose(), consumer);
        icon.render(this.context, submission.x(), submission.y(), submission.width(), submission.height(), false, submission.argb());
    }
}

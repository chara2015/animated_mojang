package net.labymod.core.laby3d.render.queue.renderer;

import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.core.laby3d.render.queue.RectangleSubmission;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/renderer/RectangleSubmissionRenderer.class */
public class RectangleSubmissionRenderer implements SubmissionRenderer<RectangleSubmission>, VanillaSubmissionRenderer<RectangleSubmission> {
    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public void render(@NotNull RenderBufferSource source, @NotNull RectangleSubmission submission) {
        renderSubmission(source, submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer
    public void renderVanilla(@NotNull RenderBufferSource source, @NotNull RectangleSubmission submission) {
        renderSubmission(source, submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    @NotNull
    public Class<RectangleSubmission> type() {
        return RectangleSubmission.class;
    }

    private void renderSubmission(RenderBufferSource source, RectangleSubmission submission) {
        int bottomRight;
        int bottomLeft;
        int topRight;
        int topLeft;
        VertexConsumer consumer = source.getBuffer(submission.material());
        Matrix4f pose = submission.pose();
        float x = submission.x();
        float y = submission.y();
        float width = submission.width();
        float height = submission.height();
        int lightCoords = submission.lightCoords();
        GradientDirection direction = submission.direction();
        int startArgb = submission.startArgb();
        int endArgb = submission.endArgb();
        switch (direction) {
            case TOP_TO_BOTTOM:
                topRight = startArgb;
                topLeft = startArgb;
                bottomRight = endArgb;
                bottomLeft = endArgb;
                break;
            case BOTTOM_TO_TOP:
                topRight = endArgb;
                topLeft = endArgb;
                bottomRight = startArgb;
                bottomLeft = startArgb;
                break;
            case LEFT_TO_RIGHT:
                bottomLeft = startArgb;
                topLeft = startArgb;
                bottomRight = endArgb;
                topRight = endArgb;
                break;
            case RIGHT_TO_LEFT:
                bottomLeft = endArgb;
                topLeft = endArgb;
                bottomRight = startArgb;
                topRight = startArgb;
                break;
            default:
                bottomRight = startArgb;
                bottomLeft = startArgb;
                topRight = startArgb;
                topLeft = startArgb;
                break;
        }
        consumer.addVertex(pose, x, y, 0.0f).setColor(topLeft).setBlankUv().setPackedLight(lightCoords);
        consumer.addVertex(pose, x, y + height, 0.0f).setColor(bottomLeft).setBlankUv().setPackedLight(lightCoords);
        consumer.addVertex(pose, x + width, y + height, 0.0f).setColor(bottomRight).setBlankUv().setPackedLight(lightCoords);
        consumer.addVertex(pose, x + width, y, 0.0f).setColor(topRight).setBlankUv().setPackedLight(lightCoords);
    }
}

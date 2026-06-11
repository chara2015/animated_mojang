package net.labymod.core.laby3d.render.queue.renderer;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer;
import net.labymod.core.laby3d.render.queue.TextSubmission;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/renderer/TextSubmissionRenderer.class */
public class TextSubmissionRenderer implements SubmissionRenderer<TextSubmission>, VanillaSubmissionRenderer<TextSubmission> {
    private static final GlyphProperties PROPERTIES = GlyphProperties.empty();

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public void render(RenderBufferSource source, TextSubmission submission) {
        renderSubmission(source, submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer
    public void renderVanilla(RenderBufferSource source, TextSubmission submission) {
        renderSubmission(source, submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public Class<TextSubmission> type() {
        return TextSubmission.class;
    }

    private void renderSubmission(RenderBufferSource source, TextSubmission submission) {
        TextState textState = submission.prepareText();
        Matrix4f pose = submission.pose();
        int lightCoords = submission.lightCoords();
        for (StyledGlyphRenderable glyph : textState.getGlyphs()) {
            submitGlyph(source, submission, glyph, pose, lightCoords);
        }
        for (GlyphRenderable effect : textState.getEffects()) {
            submitEffect(effect, pose, source.getBuffer(effect.material(submission.flags())), lightCoords);
        }
    }

    private void submitGlyph(RenderBufferSource source, TextSubmission submission, StyledGlyphRenderable glyph, Matrix4f pose, int lightCoords) {
        VertexConsumer consumer = source.getBuffer(glyph.material(submission.flags()));
        glyph.buildVertices(pose, consumer, lightCoords, false, PROPERTIES);
        GameVertexConsumer.flushPendingVertex(consumer);
    }

    private void submitEffect(GlyphRenderable effect, Matrix4f pose, VertexConsumer consumer, int lightCoords) {
        effect.buildVertices(pose, consumer, lightCoords, false, PROPERTIES);
        GameVertexConsumer.flushPendingVertex(consumer);
    }
}

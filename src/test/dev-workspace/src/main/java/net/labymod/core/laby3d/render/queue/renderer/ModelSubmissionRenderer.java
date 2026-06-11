package net.labymod.core.laby3d.render.queue.renderer;

import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.compiler.ModelCompiler;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.core.laby3d.render.queue.ModelSubmission;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/renderer/ModelSubmissionRenderer.class */
public class ModelSubmissionRenderer implements SubmissionRenderer<ModelSubmission> {
    private final ModelCompiler modelCompiler = ModelCompiler.create();
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    public void render(@NotNull RenderBufferSource source, @NotNull ModelSubmission submission) {
        VertexConsumer consumer = source.getBuffer(submission.material());
        this.stack.push();
        this.stack.mul(submission.pose());
        int lightCoords = submission.lightCoords();
        int overlayCoords = submission.overlayCoords();
        Model model = submission.model();
        for (ModelPart child : model.getChildren().values()) {
            this.modelCompiler.compile(this.stack, consumer, child, lightCoords, overlayCoords);
        }
        this.stack.pop();
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRenderer
    @NotNull
    public Class<ModelSubmission> type() {
        return ModelSubmission.class;
    }
}

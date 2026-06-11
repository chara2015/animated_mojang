package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiBlitRenderTargetRenderState.class */
@ApiStatus.Internal
public class GuiBlitRenderTargetRenderState extends AbstractGuiRenderState {
    private static final CachedOrthoProjectionMatrix<Matrix4f> BLIT_RENDER_TARGET_MATRIX = CachedOrthoProjectionMatrix.simple(0.1f, 1000.0f, false);
    private final Matrix4f projectionMatrix;
    private final RenderTarget destination;

    public GuiBlitRenderTargetRenderState(Matrix4f pose, float x, float y, float width, float height, RenderTarget destination, RenderTarget... targets) {
        super(buildMaterial(targets), pose, x, y, width, height, null);
        this.destination = destination;
        this.projectionMatrix = BLIT_RENDER_TARGET_MATRIX.getCached(destination.width(), destination.height());
    }

    private static Material buildMaterial(RenderTarget... targets) {
        RenderState renderState = RenderStates.buildCustomBlitTarget(ensureValidTargets(targets));
        GuiMaterial.Builder builder = GuiMaterial.builder(renderState);
        for (int index = 0; index < targets.length; index++) {
            builder.setTexture(index, targets[index].findColorTexture(0));
        }
        return builder.build();
    }

    private static int ensureValidTargets(RenderTarget... targets) {
        if (targets == null || targets.length == 0) {
            throw new IllegalArgumentException("The targets must not be null or empty.");
        }
        return targets.length;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        consumer.addVertex2D(this.left, this.top).setUv(0.0f, 0.0f).setColor(-1);
        consumer.addVertex2D(this.right, this.top).setUv(1.0f, 0.0f).setColor(-1);
        consumer.addVertex2D(this.right, this.bottom).setUv(1.0f, 1.0f).setColor(-1);
        consumer.addVertex2D(this.left, this.bottom).setUv(0.0f, 1.0f).setColor(-1);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void consumeCommand(@NotNull DrawCommandContext context) {
        super.consumeCommand(context);
        ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData();
        projectionData.projectionMatrix().set(this.projectionMatrix);
        context.commandBuffer().bindUniformBlockData("Projection", projectionData);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    @NotNull
    public RenderTarget getDestination() {
        return this.destination;
    }
}

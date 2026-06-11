package net.labymod.api.client.gui.screen.state;

import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/DrawCommandContext.class */
public class DrawCommandContext {

    @NotNull
    private final CommandBuffer commandBuffer;

    @NotNull
    private final ProjectionUniformBlockData projectionData;

    @NotNull
    private final DynamicTransformsUniformBlockData dynamicTransformsData;

    public DrawCommandContext(@NotNull CommandBuffer commandBuffer, @NotNull ProjectionUniformBlockData projectionData, @NotNull DynamicTransformsUniformBlockData dynamicTransformsData) {
        this.commandBuffer = commandBuffer;
        this.projectionData = projectionData;
        this.dynamicTransformsData = dynamicTransformsData;
    }

    @NotNull
    public CommandBuffer commandBuffer() {
        return this.commandBuffer;
    }

    @NotNull
    public ProjectionUniformBlockData projectionData() {
        return new ProjectionUniformBlockData(this.projectionData);
    }

    @NotNull
    public DynamicTransformsUniformBlockData dynamicTransformsData() {
        return new DynamicTransformsUniformBlockData(this.dynamicTransformsData);
    }
}

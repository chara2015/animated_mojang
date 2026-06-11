package net.labymod.api.client.render.batch;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/batch/LineRenderContext.class */
@Referenceable
public interface LineRenderContext extends RenderContext<LineRenderContext> {
    LineRenderContext begin(Stack stack);

    LineRenderContext renderGradient(float f, float f2, float f3, float f4, int i, int i2);

    LineRenderContext render(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    default RenderContext<LineRenderContext> uploadToBuffer() {
        uploadToBuffer(RenderStates.GUI_LINES, cmd -> {
            DynamicTransformsUniformBlockData dynamicTransforms = new DynamicTransformsUniformBlockData();
            dynamicTransforms.setLineWidth(1.0f);
            cmd.bindUniformBlockData("DynamicTransforms", dynamicTransforms);
        });
        return this;
    }
}

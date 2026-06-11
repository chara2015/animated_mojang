package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.ResourceBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/batch/BatchResourceRenderer.class */
@Referenceable
public interface BatchResourceRenderer extends BatchRenderer<BatchResourceRenderer>, ResourceBuilder<BatchResourceRenderer> {
    BatchResourceRenderer beginBatch(Stack stack, ResourceLocation resourceLocation);

    BatchResourceRenderer beginBatch(Stack stack, RenderState renderState, ResourceLocation resourceLocation);
}

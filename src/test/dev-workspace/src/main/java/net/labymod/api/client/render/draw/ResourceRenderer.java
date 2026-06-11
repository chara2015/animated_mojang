package net.labymod.api.client.render.draw;

import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.builder.DirectRendererBuilder;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/ResourceRenderer.class */
@Referenceable
public interface ResourceRenderer extends ResourceBuilder<ResourceRenderer>, DirectRendererBuilder {
    PlayerHeadRenderer head();

    BatchResourceRenderer beginBatch(Stack stack, ResourceLocation resourceLocation);

    BatchResourceRenderer beginBatch(Stack stack, RenderState renderState, ResourceLocation resourceLocation);

    HeartRenderer heartRenderer();
}

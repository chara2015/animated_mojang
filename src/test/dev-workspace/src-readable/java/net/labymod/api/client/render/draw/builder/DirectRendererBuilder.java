package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/DirectRendererBuilder.class */
public interface DirectRendererBuilder {
    void render(Stack stack);

    void render(Stack stack, RenderState renderState);
}

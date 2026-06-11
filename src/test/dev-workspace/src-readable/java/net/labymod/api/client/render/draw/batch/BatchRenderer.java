package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/batch/BatchRenderer.class */
public interface BatchRenderer<T extends RectangleBuilder<T>> extends RectangleBuilder<T> {
    void upload();

    void upload(RenderState renderState);

    T build();
}

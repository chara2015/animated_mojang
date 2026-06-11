package net.labymod.api.client.render.batch;

import java.util.function.Consumer;
import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/batch/RenderContext.class */
public interface RenderContext<T extends RenderContext<T>> {
    float zOffset();

    void zOffset(float f);

    RenderContext<T> uploadToBuffer(RenderState renderState);

    RenderContext<T> uploadToBuffer(RenderState renderState, Consumer<CommandBuffer> consumer);
}

package net.labymod.api.laby3d.buffer;

import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/GameBufferProvider.class */
@Referenceable
public interface GameBufferProvider {
    GameVertexConsumer getBuffer(RenderState renderState, TextureBindingSet textureBindingSet);

    void endBatch();
}

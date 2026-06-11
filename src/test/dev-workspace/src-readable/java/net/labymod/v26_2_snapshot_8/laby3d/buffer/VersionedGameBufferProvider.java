package net.labymod.v26_2_snapshot_8.laby3d.buffer;

import javax.inject.Singleton;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/buffer/VersionedGameBufferProvider.class */
@Singleton
@Implements(GameBufferProvider.class)
public class VersionedGameBufferProvider implements GameBufferProvider {
    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public GameVertexConsumer getBuffer(RenderState renderState, TextureBindingSet bindingSet) {
        return new VersionedGameVertexConsumer(null);
    }

    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public void endBatch() {
    }
}

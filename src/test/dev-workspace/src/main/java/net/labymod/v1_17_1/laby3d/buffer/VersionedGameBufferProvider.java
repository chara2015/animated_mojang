package net.labymod.v1_17_1.laby3d.buffer;

import javax.inject.Singleton;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Lazy;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.v1_17_1.client.gfx.pipeline.blaze3d.program.LabyRenderType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/buffer/VersionedGameBufferProvider.class */
@Singleton
@Implements(GameBufferProvider.class)
public class VersionedGameBufferProvider implements GameBufferProvider {
    private final Lazy<a> bufferSource = Lazy.of(() -> {
        return dvp.C().aE().b();
    });

    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public GameVertexConsumer getBuffer(RenderState renderState, TextureBindingSet bindingSet) {
        return new VersionedGameVertexConsumer(this.bufferSource.get().getBuffer(LabyRenderType.entityTranslucent(bindingSet)));
    }

    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public void endBatch() {
        this.bufferSource.get().b();
    }
}

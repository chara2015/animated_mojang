package net.labymod.v1_21_11.laby3d.buffer;

import javax.inject.Singleton;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Lazy;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.v1_21_11.client.gfx.pipeline.blaze3d.program.LabyRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/buffer/VersionedGameBufferProvider.class */
@Singleton
@Implements(GameBufferProvider.class)
public class VersionedGameBufferProvider implements GameBufferProvider {
    private final Lazy<MultiBufferSource.BufferSource> bufferSource = Lazy.of(() -> {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    });

    public GameVertexConsumer getBuffer(RenderState renderState, TextureBindingSet bindingSet) {
        RenderType renderType = LabyRenderTypes.entityTranslucent(bindingSet);
        return new VersionedGameVertexConsumer(((MultiBufferSource.BufferSource) this.bufferSource.get()).getBuffer(renderType));
    }

    public void endBatch() {
        ((MultiBufferSource.BufferSource) this.bufferSource.get()).endBatch();
    }
}

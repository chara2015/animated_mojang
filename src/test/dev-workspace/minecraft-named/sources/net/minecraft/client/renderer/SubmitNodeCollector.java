package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.ParticleFeatureRenderer;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeCollector.class */
public interface SubmitNodeCollector extends OrderedSubmitNodeCollector {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer.class */
    public interface CustomGeometryRenderer {
        void render(PoseStack.Pose pose, VertexConsumer vertexConsumer);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeCollector$ParticleGroupRenderer.class */
    public interface ParticleGroupRenderer {
        QuadParticleRenderState.PreparedBuffers prepare(ParticleFeatureRenderer.ParticleBufferCache particleBufferCache);

        void render(QuadParticleRenderState.PreparedBuffers preparedBuffers, ParticleFeatureRenderer.ParticleBufferCache particleBufferCache, RenderPass renderPass, TextureManager textureManager, boolean z);
    }

    OrderedSubmitNodeCollector order(int i);
}

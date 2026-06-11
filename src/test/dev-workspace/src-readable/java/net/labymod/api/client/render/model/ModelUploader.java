package net.labymod.api.client.render.model;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelUploader.class */
@Referenceable
public interface ModelUploader {
    public static final ModelVertexWriter DEFAULT_WRITER = (consumer, x, y, z, u, v, argb, normalX, normalY, normalZ, modelId, glowing, rainbowCycle) -> {
        consumer.addVertex(x, y, z);
        consumer.setUv(u, v);
        consumer.setColor(argb);
        consumer.setNormal(MathHelper.normalIntValue(normalX), MathHelper.normalIntValue(normalY), MathHelper.normalIntValue(normalZ));
        consumer.setFloat(modelId);
        consumer.setFloat(glowing ? 1.0f : 0.0f);
        consumer.setFloat(rainbowCycle);
    };

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelUploader$ModelVertexWriter.class */
    @FunctionalInterface
    public interface ModelVertexWriter {
        void write(VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, float f5, int i, float f6, float f7, float f8, float f9, boolean z, long j);
    }

    ModelUploader model(Model model);

    ModelUploader modelVertexWriter(ModelVertexWriter modelVertexWriter);

    @Nullable
    GeometryData upload(RenderState renderState);
}

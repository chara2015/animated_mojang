package net.labymod.api.client.render.model.compiler;

import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/compiler/VertexCompiler.class */
public interface VertexCompiler {
    public static final VertexCompiler DEFAULT = (consumer, x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ) -> {
        consumer.addVertex(x, y, z).setUv(u, v).setColor(red, green, blue, alpha).setPackedOverlay(packedOverlay).setPackedLight(packedLight).setNormal(normalX, normalY, normalZ);
    };

    void compile(VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int i, int i2, float f10, float f11, float f12);
}

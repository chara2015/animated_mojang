package net.labymod.api.laby3d.render.queue;

import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/CustomGeometryRenderer.class */
public interface CustomGeometryRenderer {
    void render(Matrix4f matrix4f, VertexConsumer vertexConsumer);
}

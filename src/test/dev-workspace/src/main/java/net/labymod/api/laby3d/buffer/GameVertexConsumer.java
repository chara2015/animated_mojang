package net.labymod.api.laby3d.buffer;

import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/GameVertexConsumer.class */
public interface GameVertexConsumer extends VertexConsumer {
    static void flushPendingVertex(VertexConsumer consumer) {
        if (consumer instanceof GameVertexConsumer) {
            GameVertexConsumer gameVertexConsumer = (GameVertexConsumer) consumer;
            gameVertexConsumer.flushPendingVertex();
        }
    }

    default void flushPendingVertex() {
    }
}

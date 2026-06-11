package net.labymod.v1_8_9.client.renderer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/renderer/WorldRendererAccessor.class */
public interface WorldRendererAccessor {
    ByteBuffer getBuffer();

    int getNextElementPosition();

    boolean building();

    void updateVertexFormatIndex();
}

package net.labymod.v1_12_2.client.renderer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/renderer/WorldRendererAccessor.class */
public interface WorldRendererAccessor {
    ByteBuffer getBuffer();

    int getNextElementPosition();

    boolean building();

    void updateVertexFormatIndex();
}

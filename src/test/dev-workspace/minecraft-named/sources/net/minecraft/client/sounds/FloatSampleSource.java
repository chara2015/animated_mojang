package net.minecraft.client.sounds;

import com.mojang.blaze3d.opengl.GlConst;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/FloatSampleSource.class */
public interface FloatSampleSource extends FiniteAudioStream {
    public static final int EXPECTED_MAX_FRAME_SIZE = 8192;

    boolean readChunk(FloatConsumer floatConsumer) throws IOException;

    @Override // net.minecraft.client.sounds.AudioStream
    default ByteBuffer read(int $$0) throws IOException {
        ChunkedSampleByteBuf $$1 = new ChunkedSampleByteBuf($$0 + EXPECTED_MAX_FRAME_SIZE);
        while (readChunk($$1) && $$1.size() < $$0) {
        }
        return $$1.get();
    }

    @Override // net.minecraft.client.sounds.FiniteAudioStream
    default ByteBuffer readAll() throws IOException {
        ChunkedSampleByteBuf $$0 = new ChunkedSampleByteBuf(GlConst.GL_COLOR_BUFFER_BIT);
        while (readChunk($$0)) {
        }
        return $$0.get();
    }
}

package net.minecraft.client.sounds;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import net.minecraft.util.Mth;
import org.lwjgl.BufferUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/ChunkedSampleByteBuf.class */
public class ChunkedSampleByteBuf implements FloatConsumer {
    private final List<ByteBuffer> buffers = Lists.newArrayList();
    private final int bufferSize;
    private int byteCount;
    private ByteBuffer currentBuffer;

    public ChunkedSampleByteBuf(int $$0) {
        this.bufferSize = ($$0 + 1) & (-2);
        this.currentBuffer = BufferUtils.createByteBuffer($$0);
    }

    public void accept(float $$0) {
        if (this.currentBuffer.remaining() == 0) {
            this.currentBuffer.flip();
            this.buffers.add(this.currentBuffer);
            this.currentBuffer = BufferUtils.createByteBuffer(this.bufferSize);
        }
        int $$1 = Mth.clamp((int) (($$0 * 32767.5f) - 0.5f), -32768, 32767);
        this.currentBuffer.putShort((short) $$1);
        this.byteCount += 2;
    }

    public ByteBuffer get() {
        this.currentBuffer.flip();
        if (this.buffers.isEmpty()) {
            return this.currentBuffer;
        }
        ByteBuffer $$0 = BufferUtils.createByteBuffer(this.byteCount);
        List<ByteBuffer> list = this.buffers;
        Objects.requireNonNull($$0);
        list.forEach($$0::put);
        $$0.put(this.currentBuffer);
        $$0.flip();
        return $$0;
    }

    public int size() {
        return this.byteCount;
    }
}

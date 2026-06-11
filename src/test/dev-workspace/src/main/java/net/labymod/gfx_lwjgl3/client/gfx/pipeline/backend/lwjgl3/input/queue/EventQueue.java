package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.queue;

import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/input/queue/EventQueue.class */
public final class EventQueue {
    private static final int MAX_QUEUE_SIZE = 200;
    private final int eventSize;
    private final ByteBuffer queueBuffer;

    public EventQueue(int eventSize) {
        this.eventSize = eventSize;
        this.queueBuffer = MemoryUtil.memAlloc(MAX_QUEUE_SIZE * eventSize);
    }

    public void fireEvents(ByteBuffer destination) {
        this.queueBuffer.flip();
        int oldLimit = this.queueBuffer.limit();
        if (destination.remaining() < this.queueBuffer.remaining()) {
            this.queueBuffer.limit(destination.remaining() + this.queueBuffer.position());
        }
        destination.put(this.queueBuffer);
        this.queueBuffer.limit(oldLimit);
        this.queueBuffer.compact();
    }

    public boolean putEvent(ByteBuffer eventBuffer) {
        if (eventBuffer.remaining() != this.eventSize) {
            throw new IllegalStateException("Event size " + this.eventSize + " does not equal the given event size " + eventBuffer.remaining());
        }
        if (this.queueBuffer.remaining() >= eventBuffer.remaining()) {
            this.queueBuffer.put(eventBuffer);
            return true;
        }
        return false;
    }

    public void clear() {
        this.queueBuffer.clear();
    }
}

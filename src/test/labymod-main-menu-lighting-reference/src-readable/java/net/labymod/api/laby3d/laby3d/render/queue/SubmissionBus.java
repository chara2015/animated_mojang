package net.labymod.api.laby3d.render.queue;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionBus.class */
public final class SubmissionBus {
    private final Reference2ObjectMap<ChannelType, Channel> channels = new Reference2ObjectOpenHashMap();
    private final AtomicLong sequence = new AtomicLong(1);

    public SubmissionBus register(ChannelType type, Channel channel) {
        this.channels.put(type, channel);
        return this;
    }

    public Channel channel(ChannelType type) {
        Channel channel = (Channel) this.channels.get(type);
        if (channel == null) {
            throw new IllegalStateException("Channel not registered: " + String.valueOf(type));
        }
        return channel;
    }

    public long nextSequenceId() {
        return this.sequence.getAndIncrement();
    }
}

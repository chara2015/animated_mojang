package net.minecraft.util.debugchart;

import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import net.minecraft.util.debug.ServerDebugSubscribers;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debugchart/RemoteSampleLogger.class */
public class RemoteSampleLogger extends AbstractSampleLogger {
    private final ServerDebugSubscribers subscribers;
    private final RemoteDebugSampleType sampleType;

    public RemoteSampleLogger(int $$0, ServerDebugSubscribers $$1, RemoteDebugSampleType $$2) {
        this($$0, $$1, $$2, new long[$$0]);
    }

    public RemoteSampleLogger(int $$0, ServerDebugSubscribers $$1, RemoteDebugSampleType $$2, long[] $$3) {
        super($$0, $$3);
        this.subscribers = $$1;
        this.sampleType = $$2;
    }

    @Override // net.minecraft.util.debugchart.AbstractSampleLogger
    protected void useSample() {
        if (this.subscribers.hasAnySubscriberFor(this.sampleType.subscription())) {
            this.subscribers.broadcastToAll(this.sampleType.subscription(), new ClientboundDebugSamplePacket((long[]) this.sample.clone(), this.sampleType));
        }
    }
}

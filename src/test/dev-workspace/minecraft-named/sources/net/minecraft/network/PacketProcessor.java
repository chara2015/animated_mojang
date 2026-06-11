package net.minecraft.network;

import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import net.minecraft.ReportedException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketProcessor.class */
public class PacketProcessor implements AutoCloseable {
    static final Logger LOGGER = LogUtils.getLogger();
    private final Queue<ListenerAndPacket<?>> packetsToBeHandled = Queues.newConcurrentLinkedQueue();
    private final Thread runningThread;
    private boolean closed;

    public PacketProcessor(Thread $$0) {
        this.runningThread = $$0;
    }

    public boolean isSameThread() {
        return Thread.currentThread() == this.runningThread;
    }

    public <T extends PacketListener> void scheduleIfPossible(T $$0, Packet<T> $$1) {
        if (this.closed) {
            throw new RejectedExecutionException("Server already shutting down");
        }
        this.packetsToBeHandled.add(new ListenerAndPacket<>($$0, $$1));
    }

    public void processQueuedPackets() {
        if (!this.closed) {
            while (!this.packetsToBeHandled.isEmpty()) {
                this.packetsToBeHandled.poll().handle();
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.closed = true;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketProcessor$ListenerAndPacket.class */
    static final class ListenerAndPacket<T extends PacketListener> extends Record {
        private final T listener;
        private final Packet<T> packet;

        ListenerAndPacket(T $$0, Packet<T> $$1) {
            this.listener = $$0;
            this.packet = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ListenerAndPacket.class), ListenerAndPacket.class, "listener;packet", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->listener:Lnet/minecraft/network/PacketListener;", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->packet:Lnet/minecraft/network/protocol/Packet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ListenerAndPacket.class), ListenerAndPacket.class, "listener;packet", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->listener:Lnet/minecraft/network/PacketListener;", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->packet:Lnet/minecraft/network/protocol/Packet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ListenerAndPacket.class, Object.class), ListenerAndPacket.class, "listener;packet", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->listener:Lnet/minecraft/network/PacketListener;", "FIELD:Lnet/minecraft/network/PacketProcessor$ListenerAndPacket;->packet:Lnet/minecraft/network/protocol/Packet;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public T listener() {
            return this.listener;
        }

        public Packet<T> packet() {
            return this.packet;
        }

        public void handle() {
            if (this.listener.shouldHandleMessage(this.packet)) {
                try {
                    this.packet.handle(this.listener);
                    return;
                } catch (Exception e) {
                    if ((e instanceof ReportedException) && (((ReportedException) e).getCause() instanceof OutOfMemoryError)) {
                        throw PacketUtils.makeReportedException(e, this.packet, this.listener);
                    }
                    this.listener.onPacketError(this.packet, e);
                    return;
                }
            }
            PacketProcessor.LOGGER.debug("Ignoring packet due to disconnection: {}", this.packet);
        }
    }
}

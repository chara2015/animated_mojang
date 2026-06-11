package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/MonitoredLocalFrameDecoder.class */
public class MonitoredLocalFrameDecoder extends ChannelInboundHandlerAdapter {
    private final BandwidthDebugMonitor monitor;

    public MonitoredLocalFrameDecoder(BandwidthDebugMonitor $$0) {
        this.monitor = $$0;
    }

    public void channelRead(ChannelHandlerContext $$0, Object $$1) {
        Object $$12 = HiddenByteBuf.unpack($$1);
        if ($$12 instanceof ByteBuf) {
            ByteBuf $$2 = (ByteBuf) $$12;
            this.monitor.onReceive($$2.readableBytes());
        }
        $$0.fireChannelRead($$12);
    }
}

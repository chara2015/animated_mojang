package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/LocalFrameEncoder.class */
public class LocalFrameEncoder extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext $$0, Object $$1, ChannelPromise $$2) {
        $$0.write(HiddenByteBuf.pack($$1), $$2);
    }
}

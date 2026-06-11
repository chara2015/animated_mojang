package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/LocalFrameDecoder.class */
public class LocalFrameDecoder extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext $$0, Object $$1) {
        $$0.fireChannelRead(HiddenByteBuf.unpack($$1));
    }
}

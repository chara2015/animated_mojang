package net.labymod.core.client.worldsharing.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.util.concurrent.ExecutionException;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import net.labymod.core.client.worldsharing.network.events.TunnelRequestEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/TunnelInitializer.class */
public class TunnelInitializer {
    private Class<? extends SocketChannel> channelClass;

    protected void handle(final TunnelRequestEvent event) throws ExecutionException, InterruptedException {
        if (!event.type().equals(JoinWorldEvent.Type.WORLD)) {
            return;
        }
        final ChannelHandler channelHandler = Worldsharing.getChannelHandler();
        EventLoopGroup eventLoopGroup = Worldsharing.getEventLoopGroup();
        if (channelHandler == null || eventLoopGroup == null) {
            throw new IllegalStateException("Captured value(s) are null! null: eventloop=" + (eventLoopGroup == null) + ", channelhander=" + (channelHandler == null));
        }
        if (this.channelClass == null) {
            this.channelClass = Worldsharing.integratedServer().getChannelClass(eventLoopGroup);
        }
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(this.channelClass).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>(this) { // from class: net.labymod.core.client.worldsharing.network.TunnelInitializer.1
            /* JADX INFO: Access modifiers changed from: protected */
            public void initChannel(SocketChannel ch) {
                ChannelPipeline channelPipelinePipeline = ch.pipeline();
                final TunnelRequestEvent tunnelRequestEvent = event;
                channelPipelinePipeline.addFirst(new ChannelHandler[]{new ChannelInboundHandlerAdapter(this) { // from class: net.labymod.core.client.worldsharing.network.TunnelInitializer.1.1
                    private boolean sessionIdSent = false;

                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        if (!this.sessionIdSent) {
                            ByteBuf buf = ctx.alloc().buffer(tunnelRequestEvent.sessionId().length);
                            buf.writeBytes(tunnelRequestEvent.sessionId());
                            ctx.writeAndFlush(buf);
                            this.sessionIdSent = true;
                            ctx.pipeline().remove(this);
                        }
                        super.channelActive(ctx);
                    }
                }});
                ch.pipeline().addLast(new ChannelHandler[]{channelHandler});
            }
        });
        ServerAddress address = ServerAddress.parse(event.endpoint());
        bootstrap.connect(address.getAddress());
    }
}

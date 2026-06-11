package net.labymod.core.client.worldsharing.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/SocketBridge.class */
public final class SocketBridge {
    private static final Logging LOGGER = Logging.getLogger();
    public final Channel remoteChannel;
    private final Channel localChannel;
    private final Runnable closeCallback;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final EventLoopGroup group = new NioEventLoopGroup(1);

    SocketBridge(byte[] handshake, SocketAddress local, SocketAddress remote, Runnable closeCallback) {
        this.closeCallback = closeCallback;
        final ForwardHandler forwardHandler = new ForwardHandler();
        Bootstrap remoteBootstrap = new Bootstrap();
        remoteBootstrap.group(this.group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<Channel>(this) { // from class: net.labymod.core.client.worldsharing.network.SocketBridge.1
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new ChannelHandler[]{forwardHandler});
            }
        });
        ChannelFuture remoteFuture = remoteBootstrap.connect(remote);
        ChannelFuture localFuture = remoteBootstrap.connect(local);
        this.remoteChannel = remoteFuture.channel();
        this.localChannel = localFuture.channel();
        remoteFuture.addListener(future -> {
            if (future.isSuccess()) {
                ByteBuf buffer = Unpooled.wrappedBuffer(handshake);
                this.remoteChannel.writeAndFlush(buffer).addListener(writeFuture -> {
                    if (!writeFuture.isSuccess()) {
                        LOGGER.warn("Failed to send handshake to remote {}", remote, writeFuture.cause());
                        shutdown();
                    }
                });
            } else {
                LOGGER.error("Failed to connect to remote {}", remote, future.cause());
                shutdown();
            }
        });
        this.remoteChannel.closeFuture().addListener(f -> {
            shutdown();
        });
        this.localChannel.closeFuture().addListener(f2 -> {
            shutdown();
        });
    }

    public void shutdown() {
        if (!this.closed.compareAndSet(false, true)) {
            return;
        }
        if (this.remoteChannel.isOpen()) {
            this.remoteChannel.close();
        }
        if (this.localChannel.isOpen()) {
            this.localChannel.close();
        }
        this.group.shutdownGracefully();
        if (this.closeCallback != null) {
            this.closeCallback.run();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/SocketBridge$ForwardHandler.class */
    @ChannelHandler.Sharable
    private final class ForwardHandler extends ChannelInboundHandlerAdapter {
        private ForwardHandler() {
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            getPairedChannel(ctx).writeAndFlush(msg);
        }

        public void channelInactive(ChannelHandlerContext ctx) {
            getPairedChannel(ctx).close();
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            if (cause instanceof Error) {
                Error error = (Error) cause;
                throw error;
            }
            if (cause instanceof IOException) {
                SocketBridge.LOGGER.debug("I/O exception on channel {}, shutting down bridge: {}", ctx.channel().remoteAddress(), cause.getMessage());
            } else {
                SocketBridge.LOGGER.warn("Unexpected pipeline exception on channel {}, shutting down bridge", ctx.channel().remoteAddress(), cause);
            }
            SocketBridge.this.shutdown();
        }

        private Channel getPairedChannel(ChannelHandlerContext ctx) {
            return ctx.channel() == SocketBridge.this.remoteChannel ? SocketBridge.this.localChannel : SocketBridge.this.remoteChannel;
        }
    }
}

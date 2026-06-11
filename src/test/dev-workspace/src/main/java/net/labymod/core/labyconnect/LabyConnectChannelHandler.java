package net.labymod.core.labyconnect;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import net.labymod.core.labyconnect.pipeline.PacketDecoder;
import net.labymod.core.labyconnect.pipeline.PacketEncoder;
import net.labymod.core.labyconnect.pipeline.PacketPrepender;
import net.labymod.core.labyconnect.pipeline.PacketSplitter;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/LabyConnectChannelHandler.class */
public class LabyConnectChannelHandler extends ChannelInitializer<NioSocketChannel> {
    private final DefaultLabyConnect labyConnect;
    private final PacketHandler handler;
    private NioSocketChannel channel;

    public LabyConnectChannelHandler(DefaultLabyConnect labyConnect, PacketHandler handler) {
        this.labyConnect = labyConnect;
        this.handler = handler;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initChannel(NioSocketChannel channel) {
        this.channel = channel;
        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30L, TimeUnit.SECONDS)).addLast("splitter", new PacketPrepender()).addLast("decoder", new PacketDecoder(this.labyConnect)).addLast("prepender", new PacketSplitter()).addLast("encoder", new PacketEncoder(this.labyConnect)).addLast(new ChannelHandler[]{this.handler});
    }

    public NioSocketChannel getChannel() {
        return this.channel;
    }
}

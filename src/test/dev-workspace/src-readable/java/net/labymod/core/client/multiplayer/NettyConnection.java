package net.labymod.core.client.multiplayer;

import io.netty.channel.Channel;
import net.labymod.api.client.network.NettyConnectionAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/multiplayer/NettyConnection.class */
public interface NettyConnection extends NettyConnectionAccessor {
    Channel getChannel();
}

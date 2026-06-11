package net.labymod.v1_20_1.mixins.server.network;

import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import net.labymod.core.client.worldsharing.Worldsharing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/server/network/MixinServerConnectionListener.class */
@Mixin({aix.class})
public class MixinServerConnectionListener {
    @ModifyArg(method = {"startTcpServerListener"}, at = @At(value = "INVOKE", target = "Lio/netty/bootstrap/ServerBootstrap;group(Lio/netty/channel/EventLoopGroup;)Lio/netty/bootstrap/ServerBootstrap;"))
    public EventLoopGroup labymod$captureEventLoop(EventLoopGroup group) {
        Worldsharing.setEventLoopGroup(group);
        return group;
    }

    @ModifyArg(method = {"startTcpServerListener"}, at = @At(value = "INVOKE", target = "Lio/netty/bootstrap/ServerBootstrap;childHandler(Lio/netty/channel/ChannelHandler;)Lio/netty/bootstrap/ServerBootstrap;"))
    public ChannelHandler labymod$captureChannelHandler(ChannelHandler childHandler) {
        Worldsharing.setChannelHandler(childHandler);
        return childHandler;
    }
}

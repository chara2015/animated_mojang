package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.UnconfiguredPipelineHandler;
import net.minecraft.network.protocol.Packet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ProtocolSwapHandler.class */
public interface ProtocolSwapHandler {
    static void handleInboundTerminalPacket(ChannelHandlerContext $$0, Packet<?> $$1) {
        if ($$1.isTerminal()) {
            $$0.channel().config().setAutoRead(false);
            $$0.pipeline().addBefore($$0.name(), HandlerNames.INBOUND_CONFIG, new UnconfiguredPipelineHandler.Inbound());
            $$0.pipeline().remove($$0.name());
        }
    }

    static void handleOutboundTerminalPacket(ChannelHandlerContext $$0, Packet<?> $$1) {
        if ($$1.isTerminal()) {
            $$0.pipeline().addAfter($$0.name(), HandlerNames.OUTBOUND_CONFIG, new UnconfiguredPipelineHandler.Outbound());
            $$0.pipeline().remove($$0.name());
        }
    }
}

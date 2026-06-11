package net.minecraft.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketEncoder.class */
public class PacketEncoder<T extends PacketListener> extends MessageToByteEncoder<Packet<T>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ProtocolInfo<T> protocolInfo;

    public PacketEncoder(ProtocolInfo<T> $$0) {
        this.protocolInfo = $$0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext $$0, Packet<T> $$1, ByteBuf $$2) throws Exception {
        PacketType<? extends Packet<T>> packetTypeType = $$1.type();
        try {
            try {
                this.protocolInfo.codec().encode($$2, $$1);
                int $$4 = $$2.readableBytes();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(Connection.PACKET_SENT_MARKER, "OUT: [{}:{}] {} -> {} bytes", new Object[]{this.protocolInfo.id().id(), packetTypeType, $$1.getClass().getName(), Integer.valueOf($$4)});
                }
                JvmProfiler.INSTANCE.onPacketSent(this.protocolInfo.id(), packetTypeType, $$0.channel().remoteAddress(), $$4);
                ProtocolSwapHandler.handleOutboundTerminalPacket($$0, $$1);
            } catch (Throwable $$5) {
                LOGGER.error("Error sending packet {}", packetTypeType, $$5);
                if ($$1.isSkippable()) {
                    throw new SkipPacketEncoderException($$5);
                }
                throw $$5;
            }
        } catch (Throwable th) {
            ProtocolSwapHandler.handleOutboundTerminalPacket($$0, $$1);
            throw th;
        }
    }
}

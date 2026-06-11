package net.labymod.core.labyconnect.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/pipeline/PacketEncoder.class */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    private static final Logging LOGGER = Logging.getLogger();
    private final DefaultLabyConnect labyConnect;

    public PacketEncoder(DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        int id = this.labyConnect.getPacketRegistry().getPacketId(packet);
        if (id != 62 && id != 63) {
            LOGGER.debug("[LABYCONNECT] [OUT] " + id + " " + packet.getClass().getSimpleName(), new Object[0]);
        }
        PacketBuffer buffer = new PacketBuffer(byteBuf);
        buffer.writeVarIntToBuffer(id);
        packet.write(buffer);
    }
}

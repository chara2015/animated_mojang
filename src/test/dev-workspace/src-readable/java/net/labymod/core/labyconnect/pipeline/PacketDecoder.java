package net.labymod.core.labyconnect.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/pipeline/PacketDecoder.class */
public class PacketDecoder extends ByteToMessageDecoder {
    private static final Logging LOGGER = Logging.getLogger();
    private final DefaultLabyConnect labyConnect;

    public PacketDecoder(DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
        if (byteBuf.readableBytes() < 1) {
            return;
        }
        PacketBuffer buffer = new PacketBuffer(byteBuf);
        int id = buffer.readVarIntFromBuffer();
        Packet packet = this.labyConnect.getPacketRegistry().getPacket(id);
        if (id != 62 && id != 63) {
            LOGGER.debug("[LABYCONNECT] [IN] " + id + " " + packet.getClass().getSimpleName(), new Object[0]);
        }
        packet.read(buffer);
        if (byteBuf.readableBytes() > 0) {
            throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + byteBuf.readableBytes() + " bytes extra whilst reading packet " + String.valueOf(packet));
        }
        objects.add(packet);
    }
}

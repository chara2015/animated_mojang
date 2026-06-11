package net.labymod.core.labyconnect.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import net.labymod.core.labyconnect.protocol.PacketBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/pipeline/PacketPrepender.class */
public class PacketPrepender extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> objects) {
        buffer.markReaderIndex();
        byte[] a = new byte[3];
        for (int i = 0; i < a.length; i++) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }
            a[i] = buffer.readByte();
            if (a[i] >= 0) {
                PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(a));
                try {
                    int varInt = buf.readVarIntFromBuffer();
                    if (buffer.readableBytes() < varInt) {
                        buffer.resetReaderIndex();
                        buf.getBuffer().release();
                        return;
                    } else {
                        objects.add(buffer.readBytes(varInt));
                        buf.getBuffer().release();
                        return;
                    }
                } catch (Throwable th) {
                    buf.getBuffer().release();
                    throw th;
                }
            }
        }
        throw new RuntimeException("length wider than 21-bit");
    }
}

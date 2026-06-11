package net.labymod.core.labyconnect.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/pipeline/PacketEncryptingEncoder.class */
public class PacketEncryptingEncoder extends MessageToByteEncoder<ByteBuf> {
    private final EncryptionTranslator encryptionCodec;

    public PacketEncryptingEncoder(Cipher cipher) {
        this.encryptionCodec = new EncryptionTranslator(cipher);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext context, ByteBuf byteBuf, ByteBuf secondByteBuf) throws Exception {
        this.encryptionCodec.cipher(byteBuf, secondByteBuf);
    }
}

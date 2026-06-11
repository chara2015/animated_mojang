package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/CipherEncoder.class */
public class CipherEncoder extends MessageToByteEncoder<ByteBuf> {
    private final CipherBase cipher;

    public CipherEncoder(Cipher $$0) {
        this.cipher = new CipherBase($$0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext $$0, ByteBuf $$1, ByteBuf $$2) throws Exception {
        this.cipher.encipher($$1, $$2);
    }
}

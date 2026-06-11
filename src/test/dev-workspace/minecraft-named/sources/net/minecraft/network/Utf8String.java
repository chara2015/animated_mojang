package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/Utf8String.class */
public class Utf8String {
    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
    public static String read(ByteBuf $$0, int $$1) throws DecoderException {
        int $$2 = ByteBufUtil.utf8MaxBytes($$1);
        int $$3 = VarInt.read($$0);
        if ($$3 > $$2) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + $$3 + " > " + $$2 + ")");
        }
        if ($$3 < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        int $$4 = $$0.readableBytes();
        if ($$3 > $$4) {
            throw new DecoderException("Not enough bytes in buffer, expected " + $$3 + ", but got " + $$4);
        }
        String $$5 = $$0.toString($$0.readerIndex(), $$3, StandardCharsets.UTF_8);
        $$0.readerIndex($$0.readerIndex() + $$3);
        if ($$5.length() > $$1) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + $$5.length() + " > " + $$1 + ")");
        }
        return $$5;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
    public static void write(ByteBuf $$0, CharSequence $$1, int $$2) throws EncoderException {
        if ($$1.length() > $$2) {
            throw new EncoderException("String too big (was " + $$1.length() + " characters, max " + $$2 + ")");
        }
        int $$3 = ByteBufUtil.utf8MaxBytes($$1);
        ByteBuf $$4 = $$0.alloc().buffer($$3);
        try {
            int $$5 = ByteBufUtil.writeUtf8($$4, $$1);
            int $$6 = ByteBufUtil.utf8MaxBytes($$2);
            if ($$5 > $$6) {
                throw new EncoderException("String too big (was " + $$5 + " bytes encoded, max " + $$6 + ")");
            }
            VarInt.write($$0, $$5);
            $$0.writeBytes($$4);
            $$4.release();
        } catch (Throwable th) {
            $$4.release();
            throw th;
        }
    }
}

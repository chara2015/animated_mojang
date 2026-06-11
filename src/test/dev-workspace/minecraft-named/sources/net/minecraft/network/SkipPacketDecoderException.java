package net.minecraft.network;

import io.netty.handler.codec.DecoderException;
import net.minecraft.network.codec.IdDispatchCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/SkipPacketDecoderException.class */
public class SkipPacketDecoderException extends DecoderException implements IdDispatchCodec.DontDecorateException, SkipPacketException {
    public SkipPacketDecoderException(String $$0) {
        super($$0);
    }

    public SkipPacketDecoderException(Throwable $$0) {
        super($$0);
    }
}

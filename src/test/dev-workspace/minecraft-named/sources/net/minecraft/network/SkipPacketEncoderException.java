package net.minecraft.network;

import io.netty.handler.codec.EncoderException;
import net.minecraft.network.codec.IdDispatchCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/SkipPacketEncoderException.class */
public class SkipPacketEncoderException extends EncoderException implements IdDispatchCodec.DontDecorateException, SkipPacketException {
    public SkipPacketEncoderException(String $$0) {
        super($$0);
    }

    public SkipPacketEncoderException(Throwable $$0) {
        super($$0);
    }
}

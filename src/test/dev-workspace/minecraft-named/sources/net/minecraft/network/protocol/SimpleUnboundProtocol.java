package net.minecraft.network.protocol;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import net.minecraft.network.PacketListener;
import net.minecraft.network.ProtocolInfo;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/SimpleUnboundProtocol.class */
public interface SimpleUnboundProtocol<T extends PacketListener, B extends ByteBuf> extends ProtocolInfo.DetailsProvider {
    ProtocolInfo<T> bind(Function<ByteBuf, B> function);
}

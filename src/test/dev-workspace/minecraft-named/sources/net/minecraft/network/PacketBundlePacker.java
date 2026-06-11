package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.Packet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketBundlePacker.class */
public class PacketBundlePacker extends MessageToMessageDecoder<Packet<?>> {
    private final BundlerInfo bundlerInfo;
    private BundlerInfo.Bundler currentBundler;

    protected /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        decode(channelHandlerContext, (Packet<?>) obj, (List<Object>) list);
    }

    public PacketBundlePacker(BundlerInfo $$0) {
        this.bundlerInfo = $$0;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
    protected void decode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
        if (this.currentBundler != null) {
            verifyNonTerminalPacket($$1);
            Packet<?> $$3 = this.currentBundler.addPacket($$1);
            if ($$3 != null) {
                this.currentBundler = null;
                $$2.add($$3);
                return;
            }
            return;
        }
        BundlerInfo.Bundler $$4 = this.bundlerInfo.startPacketBundling($$1);
        if ($$4 != null) {
            verifyNonTerminalPacket($$1);
            this.currentBundler = $$4;
        } else {
            $$2.add($$1);
            if ($$1.isTerminal()) {
                $$0.pipeline().remove($$0.name());
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
    private static void verifyNonTerminalPacket(Packet<?> $$0) throws DecoderException {
        if ($$0.isTerminal()) {
            throw new DecoderException("Terminal message received in bundle");
        }
    }
}

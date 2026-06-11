package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import java.util.Objects;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.Packet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/PacketBundleUnpacker.class */
public class PacketBundleUnpacker extends MessageToMessageEncoder<Packet<?>> {
    private final BundlerInfo bundlerInfo;

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (Packet<?>) obj, (List<Object>) list);
    }

    public PacketBundleUnpacker(BundlerInfo $$0) {
        this.bundlerInfo = $$0;
    }

    protected void encode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
        BundlerInfo bundlerInfo = this.bundlerInfo;
        Objects.requireNonNull($$2);
        bundlerInfo.unbundlePacket($$1, (v1) -> {
            r2.add(v1);
        });
        if ($$1.isTerminal()) {
            $$0.pipeline().remove($$0.name());
        }
    }
}

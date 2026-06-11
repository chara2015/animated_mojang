package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.util.VisibleForDebug;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ProtocolInfo.class */
public interface ProtocolInfo<T extends PacketListener> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ProtocolInfo$Details.class */
    public interface Details {

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ProtocolInfo$Details$PacketVisitor.class */
        @FunctionalInterface
        public interface PacketVisitor {
            void accept(PacketType<?> packetType, int i);
        }

        ConnectionProtocol id();

        PacketFlow flow();

        @VisibleForDebug
        void listPackets(PacketVisitor packetVisitor);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/ProtocolInfo$DetailsProvider.class */
    public interface DetailsProvider {
        Details details();
    }

    ConnectionProtocol id();

    PacketFlow flow();

    StreamCodec<ByteBuf, Packet<? super T>> codec();

    BundlerInfo bundlerInfo();
}

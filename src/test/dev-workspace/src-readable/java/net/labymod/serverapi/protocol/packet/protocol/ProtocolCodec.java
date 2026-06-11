package net.labymod.serverapi.protocol.packet.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.labymod.serverapi.protocol.packet.Packet;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolCodec.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolCodec {
    private final List<Decoder<? extends Packet>> decoderLookup = new ArrayList();
    private final ProtocolRegistry registry;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolCodec$Decoder.class */
    @FunctionalInterface
    public interface Decoder<T extends Packet> {
        T decode(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr);
    }

    public ProtocolCodec(ProtocolRegistry registry) {
        this.registry = registry;
        this.decoderLookup.add((id, binaryPayload) -> {
            return decode(this.registry.getAddonProtocols(), id, binaryPayload);
        });
        this.decoderLookup.add((id2, binaryPayload2) -> {
            return decode(this.registry.getCustomProtocols(), id2, binaryPayload2);
        });
    }

    public <T extends Packet> T decode(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr, Decoder<T> decoder) {
        Packet packetDecode = decoder.decode(payloadChannelIdentifier, bArr);
        if (packetDecode == null) {
            Iterator<Decoder<? extends Packet>> it = this.decoderLookup.iterator();
            while (it.hasNext()) {
                packetDecode = it.next().decode(payloadChannelIdentifier, bArr);
                if (packetDecode != null) {
                    break;
                }
            }
        }
        return (T) packetDecode;
    }

    private <T extends Packet, P extends Protocol> T decode(Collection<P> collection, PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr) {
        for (P p : collection) {
            if (p.getIdentifier().equals(payloadChannelIdentifier)) {
                return (T) p.readPacket(bArr);
            }
        }
        return null;
    }
}

package net.labymod.serverapi.protocol.packet.protocol.neo;

import net.labymod.serverapi.protocol.packet.protocol.Protocol;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/neo/NeoProtocol.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class NeoProtocol extends Protocol {
    private static final PayloadChannelIdentifier NEO = PayloadChannelIdentifier.create("labymod", "neo");
    private static final PayloadChannelIdentifier LABYMOD_3_MAIN = PayloadChannelIdentifier.create("labymod3", "main");

    public NeoProtocol() {
        super(NEO, LABYMOD_3_MAIN);
    }
}

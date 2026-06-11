package net.labymod.serverapi.protocol.api;

import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;
import net.labymod.serverapi.protocol.packet.protocol.neo.NeoProtocol;
import net.labymod.serverapi.protocol.payload.PayloadBridge;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/api/ProtocolApi.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public interface ProtocolApi {
    PayloadBridge getPayloadBridge();

    @Deprecated(forRemoval = true, since = "4.2.24")
    ProtocolService getProtocolService();

    ProtocolPlatformLogger getPlatformLogger();

    @Deprecated(forRemoval = true, since = "4.2.24")
    default NeoProtocol getNeoProtocol() {
        return getProtocolService().getNeoProtocol();
    }
}

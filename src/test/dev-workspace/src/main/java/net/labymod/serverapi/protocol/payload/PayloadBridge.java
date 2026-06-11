package net.labymod.serverapi.protocol.payload;

import net.labymod.serverapi.protocol.api.ProtocolApi;
import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/payload/PayloadBridge.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class PayloadBridge {
    protected final ProtocolService protocolService;

    public abstract void sendPayload(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr);

    public PayloadBridge(@NotNull ProtocolApi protocolApi) {
        this.protocolService = protocolApi.getProtocolService();
    }
}

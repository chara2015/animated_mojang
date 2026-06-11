package net.labymod.core.main.serverapi.legacy;

import net.labymod.api.Laby;
import net.labymod.api.serverapi.LabyProtocolApi;
import net.labymod.core.main.serverapi.payload.LabyModPayloadChannelIdentifierSerializer;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.payload.PayloadBridge;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/legacy/LabyModPayloadBridge.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class LabyModPayloadBridge extends PayloadBridge {
    private final LabyModPayloadChannelIdentifierSerializer serializer;

    public LabyModPayloadBridge(@NotNull LabyProtocolApi protocolApi) {
        super(protocolApi);
        this.serializer = new LabyModPayloadChannelIdentifierSerializer();
    }

    public static PayloadChannelIdentifier oldToNewIdentifier(net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier identifier) {
        return PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath());
    }

    public static net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier newToOldIdentifier(PayloadChannelIdentifier identifier) {
        return net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath());
    }

    @Override // net.labymod.serverapi.protocol.payload.PayloadBridge
    public void sendPayload(net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier payloadChannelIdentifier, byte[] payload) {
        Laby.labyAPI().serverController().sendPayload(this.serializer.serialize(oldToNewIdentifier(payloadChannelIdentifier)), payload);
    }
}

package net.labymod.api.serverapi;

import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.serverapi.api.AbstractProtocolService;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/serverapi/LabyModProtocolService.class */
@Referenceable
public abstract class LabyModProtocolService extends AbstractLabyModProtocolService {
    static UUID DUMMY_PLAYER = new UUID(0, 0);

    @NotNull
    public abstract PayloadTranslationRegistry translationRegistry();

    public abstract Component mapComponent(ServerAPIComponent serverAPIComponent);

    public abstract boolean readPayload(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr);

    protected LabyModProtocolService() {
        super(AbstractProtocolService.Side.CLIENT);
    }

    public void send(@NotNull PayloadChannelIdentifier payloadChannelIdentifier, @NotNull PayloadWriter payloadWriter) {
        send(payloadChannelIdentifier, DUMMY_PLAYER, payloadWriter);
    }

    public void send(Protocol protocol, Packet packet) {
        protocol.sendPacket(DUMMY_PLAYER, packet);
    }

    public void sendLabyModPacket(Packet packet) {
        send((Protocol) this.labyModProtocol, packet);
    }
}

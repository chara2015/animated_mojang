package net.labymod.core.main.serverapi.protocol.intave;

import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.inject.Singleton;
import net.labymod.api.Namespaces;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.serverapi.TranslationProtocol;
import net.labymod.core.main.serverapi.protocol.intave.handler.IntaveClientConfigReceivedPacketHandler;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigPacket;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.core.main.serverapi.protocol.intave.translation.IntaveConfigTranslationListener;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.packet.Direction;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/intave/IntaveProtocol.class */
@Singleton
@Referenceable
public class IntaveProtocol extends Protocol {
    private final LabyModProtocolService protocolService;
    private boolean hasPermission;
    private boolean receivedConfigPacket;

    public IntaveProtocol(@NotNull LabyModProtocolService protocolService) {
        super(protocolService, PayloadChannelIdentifier.create(Namespaces.MINECRAFT, "intave"));
        this.protocolService = protocolService;
        initialize();
        protocolService.registry().registerProtocol(this);
    }

    private void initialize() {
        registerPacket(0, IntaveClientConfigPacket.class, Direction.SERVERBOUND);
        registerPacket(1, IntaveClientConfigReceivedPacket.class, Direction.CLIENTBOUND, new IntaveClientConfigReceivedPacketHandler(this));
        TranslationProtocol translationProtocol = new TranslationProtocol(this.identifier, this);
        this.protocolService.translationRegistry().register(translationProtocol);
        translationProtocol.registerListener(new IntaveConfigTranslationListener());
    }

    public void sendPacket(Packet packet) {
        sendPacket(LabyModProtocolService.DUMMY_UUID, packet);
    }

    @Nullable
    public Packet handleIncomingPayload(@NotNull UUID sender, @NotNull PayloadReader reader) {
        return null;
    }

    public void sendPacket(@NotNull UUID recipient, @NotNull Packet packet) {
        this.protocolService.afterPacketSent(this, packet, recipient);
    }

    public boolean hasPermission(boolean enabled) {
        if (this.receivedConfigPacket) {
            return this.hasPermission && enabled;
        }
        return enabled;
    }

    public boolean hasPermission(BooleanSupplier function) {
        if (this.receivedConfigPacket) {
            return this.hasPermission && function.getAsBoolean();
        }
        return function.getAsBoolean();
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public void setReceivedConfigPacket(boolean receivedConfigPacket) {
        this.receivedConfigPacket = receivedConfigPacket;
    }
}

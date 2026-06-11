package net.labymod.serverapi.protocol.packet.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import net.labymod.serverapi.protocol.packet.Packet;
import net.labymod.serverapi.protocol.packet.protocol.execption.ProtocolException;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;
import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/Protocol.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class Protocol {
    private final ProtocolService protocolService;
    private final PayloadChannelIdentifier identifier;

    @Nullable
    private final PayloadChannelIdentifier legacyIdentifier;
    private final Map<Integer, Packet> packets;
    private final Gson gson;

    public Protocol(PayloadChannelIdentifier identifier) {
        this(identifier, null);
    }

    public Protocol(PayloadChannelIdentifier identifier, PayloadChannelIdentifier legacyIdentifier) {
        this.identifier = identifier;
        this.legacyIdentifier = legacyIdentifier;
        this.packets = new HashMap();
        this.gson = new GsonBuilder().create();
        this.protocolService = ProtocolApiBridge.getProtocolApi().getProtocolService();
    }

    public void registerPacket(int id, Packet packet) {
        if (id < 0) {
            throw new ProtocolException(String.format("The %s packet cannot be registered because negative IDs are not supported", packet.getClass().getName()));
        }
        Packet anotherPacket = this.packets.get(Integer.valueOf(id));
        if (anotherPacket != null) {
            throw new ProtocolException(String.format("The %s packet has already been registered with this %s ID.", anotherPacket.getClass().getName(), Integer.valueOf(id)));
        }
        this.packets.put(Integer.valueOf(id), packet);
    }

    public void registerTranslationListener(AbstractPayloadTranslationListener translationListener) {
        this.protocolService.registerTranslationListener(translationListener);
    }

    @Nullable
    public <T extends Packet> T getPacket(int id) {
        return (T) this.packets.get(Integer.valueOf(id));
    }

    public <T extends Packet> int getPacketId(Class<T> packetClass) {
        for (Map.Entry<Integer, Packet> entry : this.packets.entrySet()) {
            if (entry.getValue().getClass().equals(packetClass)) {
                return entry.getKey().intValue();
            }
        }
        return -1;
    }

    public PayloadChannelIdentifier getIdentifier() {
        return this.identifier;
    }

    @Nullable
    public PayloadChannelIdentifier getLegacyIdentifier() {
        return this.legacyIdentifier;
    }

    public Packet readPacket(byte[] payload) {
        return readPacketFromBinary(new PayloadReader(payload));
    }

    private Packet readPacketFromBinary(@NotNull PayloadReader reader) {
        int packetId = reader.readVarInt();
        Packet packet = getPacket(packetId);
        if (packet == null) {
            throw new ProtocolException(String.format("No packet was found with this %s ID.", Integer.valueOf(packetId)));
        }
        try {
            return (Packet) this.gson.fromJson(reader.readString(), packet.getClass());
        } catch (JsonSyntaxException exception) {
            throw new ProtocolException("An error occurred while parsing the packet.", exception);
        }
    }

    public void sendPacket(@NotNull Packet packet) {
        this.protocolService.sendPacket(this.identifier, writePacketToBinary(packet));
    }

    private byte[] writePacketToBinary(@NotNull Packet packet) {
        PayloadWriter writer = new PayloadWriter();
        int packetId = getPacketId(packet.getClass());
        writer.writeVarInt(packetId);
        writer.writeString(this.gson.toJson(packet));
        return writer.toByteArray();
    }
}

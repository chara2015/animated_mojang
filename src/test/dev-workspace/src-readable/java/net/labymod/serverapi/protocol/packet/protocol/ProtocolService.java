package net.labymod.serverapi.protocol.packet.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import net.labymod.serverapi.protocol.packet.Packet;
import net.labymod.serverapi.protocol.packet.PacketHandler;
import net.labymod.serverapi.protocol.packet.protocol.execption.ProtocolException;
import net.labymod.serverapi.protocol.packet.protocol.neo.NeoProtocol;
import net.labymod.serverapi.protocol.payload.PayloadBridge;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolService.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolService {
    protected PayloadBridge payloadBridge;
    private NeoProtocol neoProtocol;
    private final ProtocolRegistry protocolRegistry = new ProtocolRegistry();
    private final ProtocolCodec protocolCodec = new ProtocolCodec(this.protocolRegistry);
    private final List<AbstractPayloadTranslationListener> translationListeners = new ArrayList();
    private final Map<Class<? extends Packet>, PacketHandler<?>> packetHandlers = new HashMap();
    private final Gson gson = new GsonBuilder().create();

    public void initialize() {
        this.neoProtocol = new NeoProtocol();
        this.payloadBridge = ProtocolApiBridge.getProtocolApi().getPayloadBridge();
    }

    public void registerAddonProtocol(String addonId) {
        registerAddonProtocol(new AddonProtocol(addonId));
    }

    public void registerAddonProtocol(@NotNull AddonProtocol protocolService) {
        this.protocolRegistry.registerAddonProtocol(protocolService);
    }

    public void registerCustomProtocol(@NotNull Protocol protocol) {
        this.protocolRegistry.registerCustomProtocol(protocol);
    }

    public boolean unregisterAddonProtocol(String addonId) {
        return this.protocolRegistry.unregisterAddonProtocol(addonId);
    }

    public boolean unregisterProtocol(PayloadChannelIdentifier identifier) {
        return this.protocolRegistry.unregisterCustomProtocol(identifier);
    }

    public void registerTranslationListener(AbstractPayloadTranslationListener translationListener) {
        this.translationListeners.add(translationListener);
    }

    public <T extends Packet> void registerPacketHandler(Class<T> packetClass, PacketHandler<?> packetHandler) {
        if (this.packetHandlers.containsKey(packetClass)) {
            ProtocolApiBridge.getProtocolApi().getPlatformLogger().warn("A handler has already been registered for this packet. ({})", packetClass.getName());
        } else {
            this.packetHandlers.put(packetClass, packetHandler);
        }
    }

    public <T extends PacketHandler<Packet>> Optional<T> getPacketHandler(Class<? extends Packet> packetClass) {
        for (Map.Entry<Class<? extends Packet>, PacketHandler<?>> entry : this.packetHandlers.entrySet()) {
            if (entry.getKey().equals(packetClass)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    @ApiStatus.Internal
    @Nullable
    public <T extends Packet> T readPayload(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr) {
        return (T) decodePayload(payloadChannelIdentifier, bArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean readCustomPayload(PayloadChannelIdentifier identifier, byte[] payload, boolean shouldTranslate) {
        try {
            Packet packet = decodePayload(identifier, payload, shouldTranslate);
            if (packet == null) {
                return false;
            }
            getPacketHandler(packet.getClass()).ifPresent(packetHandler -> {
                packetHandler.handle(packet);
            });
            return true;
        } catch (ProtocolException exception) {
            ProtocolApiBridge.getProtocolApi().getPlatformLogger().error("An error occurred while reading the payload of a custom payload packet.", exception);
            return false;
        }
    }

    @Nullable
    private <T extends Packet> T decodePayload(PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr, boolean z) {
        if (z) {
            TranslationData translationDataApplyTranslationListener = applyTranslationListener(payloadChannelIdentifier, bArr, true);
            payloadChannelIdentifier = translationDataApplyTranslationListener.getIdentifier();
            bArr = translationDataApplyTranslationListener.getPayload();
        }
        return (T) this.protocolCodec.decode(payloadChannelIdentifier, bArr, (id, binaryPayload) -> {
            if (this.neoProtocol.getIdentifier().equals(id)) {
                return this.neoProtocol.readPacket(binaryPayload);
            }
            return null;
        });
    }

    @Nullable
    private <T extends Packet, P extends Protocol> T readPayload(PayloadChannelIdentifier payloadChannelIdentifier, List<P> list, byte[] bArr) {
        for (P p : list) {
            if (p.getIdentifier().equals(payloadChannelIdentifier)) {
                return (T) p.readPacket(bArr);
            }
        }
        return null;
    }

    public <T extends Packet> byte[] writePacketToBinary(@NotNull T packet) {
        Class<?> cls = packet.getClass();
        this.neoProtocol.getPacketId(cls);
        ProtocolRegistry protocolRegistry = this.protocolRegistry;
        NeoProtocol neoProtocol = this.neoProtocol;
        Objects.requireNonNull(neoProtocol);
        int packetId = protocolRegistry.findPacketId(cls, neoProtocol::getPacketId);
        if (packetId <= -1) {
            return null;
        }
        PayloadWriter writer = new PayloadWriter();
        writer.writeVarInt(packetId);
        writer.writeString(this.gson.toJson(packet));
        return writer.toByteArray();
    }

    public void sendPacket(PayloadChannelIdentifier identifier, byte[] payload) {
        TranslationData translationData = applyTranslationListener(identifier, payload, false);
        if (translationData.hasTranslation()) {
            this.payloadBridge.sendPayload(identifier, payload);
        }
        this.payloadBridge.sendPayload(translationData.getIdentifier(), translationData.getPayload());
    }

    public NeoProtocol getNeoProtocol() {
        return this.neoProtocol;
    }

    @Nullable
    public <T extends Protocol> T findCustomProtocol(Class<T> cls) {
        return (T) this.protocolRegistry.findCustomProtocol(cls);
    }

    @Nullable
    public <T extends Protocol> T findCustomProtocol(PayloadChannelIdentifier payloadChannelIdentifier) {
        return (T) this.protocolRegistry.findCustomProtocol(payloadChannelIdentifier);
    }

    @Nullable
    public <T extends AddonProtocol> T findAddonProtocol(Class<T> cls) {
        return (T) this.protocolRegistry.findAddonProtocol(cls);
    }

    @Nullable
    public <T extends AddonProtocol> T findAddonProtocol(String str) {
        return (T) this.protocolRegistry.findAddonProtocol(str);
    }

    public List<Protocol> getAllProtocols() {
        return this.protocolRegistry.getAllProtocols();
    }

    @NotNull
    public List<AddonProtocol> getAddonProtocols() {
        return this.protocolRegistry.getAddonProtocols();
    }

    @NotNull
    public List<Protocol> getCustomProtocols() {
        return this.protocolRegistry.getCustomProtocols();
    }

    @Deprecated
    public <T extends AddonProtocol> Optional<T> getAddonProtocol(String addonId) {
        return Optional.ofNullable(findAddonProtocol(addonId));
    }

    @Deprecated
    public <T extends AddonProtocol> Optional<T> getAddonProtocol(Class<T> addonProtocolClass) {
        return Optional.ofNullable(findAddonProtocol(addonProtocolClass));
    }

    @Deprecated
    public <T extends Protocol> T getProtocol(PayloadChannelIdentifier payloadChannelIdentifier) {
        return (T) findCustomProtocol(payloadChannelIdentifier);
    }

    @Deprecated
    public <T extends Protocol> T getProtocol(Class<T> cls) {
        return (T) findCustomProtocol(cls);
    }

    @Deprecated
    public <T extends Protocol> Optional<T> getOptionalProtocol(PayloadChannelIdentifier identifier) {
        Protocol protocol = getProtocol(identifier);
        return protocol == null ? Optional.empty() : Optional.of(protocol);
    }

    @Deprecated
    public <T extends Protocol> Optional<T> getOptionalProtocol(Class<T> protocolClass) {
        Protocol protocol = getProtocol(protocolClass);
        return protocol == null ? Optional.empty() : Optional.of(protocol);
    }

    @Contract("_, _, _ -> new")
    @NotNull
    private TranslationData applyTranslationListener(PayloadChannelIdentifier identifier, byte[] payload, boolean incoming) {
        byte[] outgoingPayload;
        for (AbstractPayloadTranslationListener translationListener : this.translationListeners) {
            PayloadChannelIdentifier oldIdentifier = incoming ? translationListener.getOldIdentifier() : translationListener.getNewIdentifier();
            if (oldIdentifier.equals(identifier)) {
                if (incoming) {
                    outgoingPayload = translationListener.readIncomingPayload(payload);
                } else {
                    outgoingPayload = translationListener.readOutgoingPayload(identifier, payload);
                }
                byte[] newPayload = outgoingPayload;
                if (newPayload != null) {
                    TranslationData translationData = new TranslationData(incoming ? translationListener.getNewIdentifier() : translationListener.getOldIdentifier(), incoming ? newPayload : translationListener.modifyOutgoingPayload(newPayload));
                    translationData.setHasTranslation(true);
                    return translationData;
                }
            }
        }
        return new TranslationData(identifier, payload);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolService$TranslationData.class */
    private static class TranslationData {
        private final PayloadChannelIdentifier identifier;
        private final byte[] payload;
        private boolean hasTranslation = false;

        public TranslationData(@NotNull PayloadChannelIdentifier identifier, byte[] payload) {
            this.identifier = identifier;
            this.payload = payload;
        }

        @NotNull
        public PayloadChannelIdentifier getIdentifier() {
            return this.identifier;
        }

        public byte[] getPayload() {
            return this.payload;
        }

        public boolean hasTranslation() {
            return this.hasTranslation;
        }

        public void setHasTranslation(boolean hasTranslation) {
            this.hasTranslation = hasTranslation;
        }
    }
}

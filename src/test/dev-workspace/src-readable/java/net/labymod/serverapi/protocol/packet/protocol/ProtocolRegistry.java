package net.labymod.serverapi.protocol.packet.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import net.labymod.serverapi.protocol.packet.Packet;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolRegistry.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolRegistry {
    public static final int UNKNOWN_PACKET_ID = -1;
    private final List<PacketIdLookup> packetIdLookups = new ArrayList();
    private final List<Protocol> allProtocols = new ArrayList();
    private final List<Protocol> customProtocols = new ArrayList();
    private final List<AddonProtocol> addonProtocols = new ArrayList();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/ProtocolRegistry$PacketIdLookup.class */
    @FunctionalInterface
    public interface PacketIdLookup {
        int lookup(Class<? extends Packet> cls);
    }

    public ProtocolRegistry() {
        this.packetIdLookups.add(packetClass -> {
            return findPacketId(this.addonProtocols, packetClass);
        });
        this.packetIdLookups.add(packetClass2 -> {
            return findPacketId(this.customProtocols, packetClass2);
        });
    }

    public void registerAddonProtocol(AddonProtocol protocol) {
        if (isProtocolRegistered(this.addonProtocols, protocol)) {
            warn("AddonProtocol with identifier {} is already registered", protocol.getIdentifier());
        } else {
            registerProtocol(protocol);
        }
    }

    public boolean unregisterAddonProtocol(String addonId) {
        return this.addonProtocols.removeIf(protocol -> {
            if (protocol.getAddonId().equals(addonId)) {
                this.allProtocols.remove(protocol);
                return true;
            }
            return false;
        });
    }

    public void registerCustomProtocol(Protocol protocol) {
        if (isProtocolRegistered(this.customProtocols, protocol)) {
            warn("Protocol with identifier {} is already registered", protocol.getIdentifier());
        } else {
            registerProtocol(protocol);
        }
    }

    public boolean unregisterCustomProtocol(PayloadChannelIdentifier id) {
        return this.customProtocols.removeIf(protocol -> {
            if (protocol.getIdentifier().equals(id)) {
                this.allProtocols.remove(protocol);
                return true;
            }
            return false;
        });
    }

    public <T extends AddonProtocol> T findAddonProtocol(String addonId) {
        return (T) findProtocol(this.addonProtocols, protocol -> {
            return protocol.getAddonId().equals(addonId);
        });
    }

    public <T extends AddonProtocol> T findAddonProtocol(Class<T> addonProtocolClass) {
        return (T) findProtocol(this.addonProtocols, protocol -> {
            return protocol.getClass().equals(addonProtocolClass);
        });
    }

    public <T extends Protocol> T findCustomProtocol(PayloadChannelIdentifier payloadChannelIdentifier) {
        return (T) findProtocol(this.customProtocols, protocol -> {
            return protocol.getIdentifier().equals(payloadChannelIdentifier);
        });
    }

    public <T extends Protocol> T findCustomProtocol(Class<T> cls) {
        return (T) findProtocol(this.customProtocols, protocol -> {
            return protocol.getClass().equals(cls);
        });
    }

    public <P extends Packet> int findPacketId(Class<P> packetClass, PacketIdLookup defaultLookup) {
        int packetId = defaultLookup.lookup(packetClass);
        if (packetId == -1) {
            for (PacketIdLookup lookup : this.packetIdLookups) {
                packetId = lookup.lookup(packetClass);
                if (packetId > -1) {
                    break;
                }
            }
        }
        return packetId;
    }

    public List<Protocol> getAllProtocols() {
        return Collections.unmodifiableList(this.allProtocols);
    }

    public List<Protocol> getCustomProtocols() {
        return Collections.unmodifiableList(this.customProtocols);
    }

    public List<AddonProtocol> getAddonProtocols() {
        return Collections.unmodifiableList(this.addonProtocols);
    }

    private boolean isProtocolRegistered(Collection<? extends Protocol> protocols, Protocol protocol) {
        return isProtocolRegistered(protocols, protocol.getIdentifier());
    }

    private boolean isProtocolRegistered(Collection<? extends Protocol> protocols, PayloadChannelIdentifier id) {
        for (Protocol protocol : protocols) {
            if (protocol.getIdentifier().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void warn(String message, Object... args) {
        ProtocolApiBridge.getProtocolApi().getPlatformLogger().warn(message, args);
    }

    private <PACKET extends Packet, PROTOCOL extends Protocol> PACKET decode(Collection<PROTOCOL> collection, PayloadChannelIdentifier payloadChannelIdentifier, byte[] bArr) {
        for (PROTOCOL protocol : collection) {
            if (protocol.getIdentifier().equals(payloadChannelIdentifier)) {
                return (PACKET) protocol.readPacket(bArr);
            }
        }
        return null;
    }

    private <P extends Packet> int findAddonPacketId(Class<P> packetClass) {
        return findPacketId(this.addonProtocols, packetClass);
    }

    private <P extends Packet> int findCustomPacketId(Class<P> packetClass) {
        return findPacketId(this.customProtocols, packetClass);
    }

    @Nullable
    private <T extends Protocol> T findProtocol(Collection<T> protocols, Predicate<T> filter) {
        for (T protocol : protocols) {
            if (filter.test(protocol)) {
                return protocol;
            }
        }
        return null;
    }

    private <PACKET extends Packet, PROTOCOL extends Protocol> int findPacketId(Collection<PROTOCOL> protocols, Class<PACKET> packetClass) {
        for (PROTOCOL protocol : protocols) {
            int packetId = protocol.getPacketId(packetClass);
            if (packetId >= 0) {
                return packetId;
            }
        }
        return -1;
    }

    private void registerProtocol(Protocol protocol) {
        if (protocol instanceof AddonProtocol) {
            this.addonProtocols.add((AddonProtocol) protocol);
        } else {
            this.customProtocols.add(protocol);
        }
        this.allProtocols.add(protocol);
    }
}

package net.labymod.core.labyconnect.protocol.packets;

import java.util.ArrayList;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.labypeer.ice.CandidateType;
import net.labymod.labypeer.ice.IceCredentials;
import net.labymod.labypeer.ice.SerializableCandidate;
import net.labymod.labypeer.ice.SerializableComponent;
import net.labymod.labypeer.ice.Transport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketIceCredentials.class */
public class PacketIceCredentials extends Packet {
    private UUID targetUser;
    private IceCredentials credentials;

    public PacketIceCredentials(UUID targetUser, IceCredentials credentials) {
        this.targetUser = targetUser;
        this.credentials = credentials;
    }

    public PacketIceCredentials() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.targetUser = buf.readUUID();
        UUID uuid = buf.readUUID();
        String string = buf.readString();
        String string2 = buf.readString();
        int componentSize = buf.readByte();
        this.credentials = new IceCredentials(uuid, string, string2, new ArrayList(componentSize));
        for (int i = 0; i < componentSize; i++) {
            String string3 = buf.readString();
            byte b = buf.readByte();
            SerializableCandidate candidate = readCandidate(buf);
            int candidateSize = buf.readShort();
            SerializableComponent component = new SerializableComponent(string3, b, candidate, new ArrayList(candidateSize));
            this.credentials.getComponents().add(component);
            for (int j = 0; j < candidateSize; j++) {
                component.getCandidates().add(readCandidate(buf));
            }
        }
    }

    private SerializableCandidate readCandidate(PacketBuffer buf) {
        return new SerializableCandidate(buf.readString(), buf.readInt(), buf.readEnum(Transport.values()), buf.readEnum(CandidateType.values()), buf.readBoolean() ? buf.readString() : null, buf.readLong());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUUID(this.targetUser);
        buf.writeUUID(this.credentials.getOwningUser());
        buf.writeString(this.credentials.getUfrag());
        buf.writeString(this.credentials.getPassword());
        buf.writeByte(this.credentials.getComponents().size());
        for (SerializableComponent component : this.credentials.getComponents()) {
            buf.writeString(component.getStreamName());
            buf.writeByte(component.getComponentId());
            writeCandidate(component.getDefaultCandidate(), buf);
            buf.writeShort((short) component.getCandidates().size());
            for (SerializableCandidate candidate : component.getCandidates()) {
                writeCandidate(candidate, buf);
            }
        }
    }

    private void writeCandidate(SerializableCandidate candidate, PacketBuffer buf) {
        buf.writeString(candidate.getAddress());
        buf.writeInt(candidate.getPort());
        buf.writeByte(candidate.getTransport().ordinal());
        buf.writeByte(candidate.getType().ordinal());
        buf.writeBoolean(candidate.getFoundation() != null);
        if (candidate.getFoundation() != null) {
            buf.writeString(candidate.getFoundation());
        }
        buf.writeLong(candidate.getPriority());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public UUID getTargetUser() {
        return this.targetUser;
    }

    public IceCredentials getCredentials() {
        return this.credentials;
    }
}

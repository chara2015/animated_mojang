package net.labymod.core.labyconnect.protocol.packets;

import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.Property;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketUserTracker.class */
public class PacketUserTracker extends Packet {
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
    private EnumTrackingChannel channel;
    private EnumTrackingAction action;
    private PlayerEntityMeta[] users;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketUserTracker$EnumTrackingAction.class */
    public enum EnumTrackingAction {
        ADD,
        REMOVE,
        UPDATE,
        CLEAR,
        SYNC
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketUserTracker$EnumTrackingChannel.class */
    public enum EnumTrackingChannel {
        ENTITIES,
        LIST
    }

    public PacketUserTracker() {
    }

    public PacketUserTracker(EnumTrackingChannel channel, EnumTrackingAction action) {
        this(channel, action, new PlayerEntityMeta[0]);
    }

    public PacketUserTracker(EnumTrackingChannel channel, EnumTrackingAction action, PlayerEntityMeta[] users) {
        this.channel = channel;
        this.action = action;
        this.users = users;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        buf.readByte();
        this.channel = (EnumTrackingChannel) buf.readEnum(EnumTrackingChannel.values());
        this.action = (EnumTrackingAction) buf.readEnum(EnumTrackingAction.values());
        if (this.action != EnumTrackingAction.CLEAR) {
            this.users = new PlayerEntityMeta[buf.readInt()];
            for (int i = 0; i < this.users.length; i++) {
                this.users[i] = new PlayerEntityMeta(buf.readLong(), buf.readLong());
                if (this.channel == EnumTrackingChannel.LIST && this.action == EnumTrackingAction.ADD) {
                    boolean hasCape = buf.readBoolean();
                    if (hasCape) {
                        this.users[i].setCapeId(buf.readShort());
                    }
                }
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.readByte();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeByte(6);
        buf.writeByte(this.channel.ordinal());
        buf.writeByte(this.action.ordinal());
        if (this.action != EnumTrackingAction.CLEAR) {
            buf.writeInt(this.users.length);
            for (PlayerEntityMeta user : this.users) {
                buf.writeLong(user.getMostSignificantBits());
                buf.writeLong(user.getLeastSignificantBits());
                if (this.channel == EnumTrackingChannel.LIST && this.action == EnumTrackingAction.ADD) {
                    boolean hasCape = user.hasCape();
                    buf.writeBoolean(hasCape);
                    if (hasCape) {
                        buf.writeShort(user.getCapeId());
                    }
                }
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.writeByte(0);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public EnumTrackingChannel getChannel() {
        return this.channel;
    }

    public EnumTrackingAction getAction() {
        return this.action;
    }

    public PlayerEntityMeta[] getUsers() {
        return this.users;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketUserTracker$PlayerEntityMeta.class */
    public static class PlayerEntityMeta {
        private final UUID uuid;
        private boolean hasCape;
        private short capeId;

        public PlayerEntityMeta(UUID uuid) {
            this.capeId = (short) 0;
            this.uuid = uuid;
        }

        public PlayerEntityMeta(long mostSignificantBits, long leastSignificantBits) {
            this.capeId = (short) 0;
            this.uuid = new UUID(mostSignificantBits, leastSignificantBits);
        }

        public PlayerEntityMeta(GameProfile profile) {
            this.capeId = (short) 0;
            this.uuid = profile.getUniqueId();
            try {
                Collection<Property> textures = profile.getProperties().get("textures");
                if (textures != null) {
                    Iterator<Property> it = textures.iterator();
                    if (it.hasNext()) {
                        Property texture = it.next();
                        String json = new String(PacketUserTracker.BASE64_DECODER.decode(texture.getValue()));
                        Short capeId = getCapeId(json);
                        if (capeId != null) {
                            this.hasCape = true;
                            this.capeId = capeId.shortValue();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCapeId(short capeId) {
            this.capeId = capeId;
        }

        public int hashCode() {
            return this.uuid.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof PlayerEntityMeta) {
                return this.uuid.equals(((PlayerEntityMeta) obj).uuid);
            }
            return false;
        }

        public long getMostSignificantBits() {
            return this.uuid.getMostSignificantBits();
        }

        public long getLeastSignificantBits() {
            return this.uuid.getLeastSignificantBits();
        }

        public boolean hasCape() {
            return this.hasCape;
        }

        public short getCapeId() {
            return this.capeId;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        private Short getCapeId(String json) {
            String json2;
            int urlPos;
            String json3;
            int hashEnd;
            try {
                int capePos = json.indexOf("\"CAPE\" : {");
                if (capePos == -1 || (urlPos = (json2 = json.substring(capePos)).indexOf("textures.minecraft.net/texture/")) == -1 || (hashEnd = (json3 = json2.substring(urlPos + "textures.minecraft.net/texture/".length())).indexOf("\"")) == -1) {
                    return null;
                }
                String json4 = json3.substring(0, hashEnd);
                if (json4.length() < 4) {
                    return null;
                }
                return Short.valueOf((short) Integer.parseInt(json4.substring(0, 4), 16));
            } catch (Exception e) {
                return null;
            }
        }
    }
}

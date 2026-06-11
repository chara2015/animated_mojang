package net.labymod.core.labyconnect.protocol;

import java.util.HashMap;
import java.util.Map;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlay;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketChatVisibilityChange;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPing;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginData;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginStart;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginVersion;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayChangeOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayDenyFriendRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatusUpdate;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.core.labyconnect.protocol.packets.PacketPong;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/Protocol.class */
public class Protocol {
    private final Map<Integer, Class<? extends Packet>> packets = new HashMap();

    public Protocol() {
        register(0, PacketHelloPing.class);
        register(1, PacketHelloPong.class);
        register(2, PacketLoginStart.class);
        register(3, PacketLoginData.class);
        register(4, PacketLoginFriend.class);
        register(5, PacketLoginRequest.class);
        register(6, PacketLoginOptions.class);
        register(7, PacketLoginComplete.class);
        register(8, PacketLoginTime.class);
        register(9, PacketLoginVersion.class);
        register(10, PacketEncryptionRequest.class);
        register(11, PacketEncryptionResponse.class);
        register(14, PacketPlayPlayerOnline.class);
        register(16, PacketPlayRequestAddFriend.class);
        register(17, PacketPlayRequestAddFriendResponse.class);
        register(18, PacketPlayRequestRemove.class);
        register(19, PacketPlayDenyFriendRequest.class);
        register(20, PacketPlayFriendRemove.class);
        register(21, PacketPlayChangeOptions.class);
        register(22, PacketPlayServerStatus.class);
        register(23, PacketPlayFriendStatus.class);
        register(24, PacketPlayFriendPlayingOn.class);
        register(25, PacketPlayTyping.class);
        register(26, PacketMojangStatus.class);
        register(27, PacketActionPlay.class);
        register(28, PacketActionPlayResponse.class);
        register(29, PacketActionRequest.class);
        register(30, PacketActionRequestResponse.class);
        register(31, PacketUpdateCosmetics.class);
        register(32, PacketAddonMessage.class);
        register(33, PacketUserBadge.class);
        register(34, PacketAddonDevelopment.class);
        register(35, PacketPlayInviteLanWorld.class);
        register(36, PacketPlayAcceptLanWorldInvite.class);
        register(37, PacketPlayRejectLanWorldInvite.class);
        register(38, PacketIceCredentials.class);
        register(60, PacketDisconnect.class);
        register(61, PacketKick.class);
        register(62, PacketPing.class);
        register(63, PacketPong.class);
        register(64, PacketServerMessage.class);
        register(65, PacketMessage.class);
        register(66, PacketNotAllowed.class);
        register(67, PacketChatVisibilityChange.class);
        register(68, PacketPlayServerStatusUpdate.class);
        register(69, PacketUserTracker.class);
        register(70, PacketActionBroadcast.class);
        register(71, PacketPlayBroadcastPayload.class);
    }

    private void register(int id, Class<?> clazz) {
        try {
            this.packets.put(Integer.valueOf(id), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Packet getPacket(int id) throws Exception {
        if (!this.packets.containsKey(Integer.valueOf(id))) {
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        }
        return this.packets.get(Integer.valueOf(id)).getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    public int getPacketId(Packet packet) {
        for (Map.Entry<Integer, Class<? extends Packet>> entry : this.packets.entrySet()) {
            Class<? extends Packet> clazz = entry.getValue();
            if (clazz.isInstance(packet)) {
                return entry.getKey().intValue();
            }
        }
        throw new RuntimeException("Packet " + String.valueOf(packet) + " is not registered.");
    }

    public Map<Integer, Class<? extends Packet>> getPackets() {
        return this.packets;
    }
}

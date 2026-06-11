package net.labymod.core.labyconnect.protocol.packets;

import java.util.ArrayList;
import java.util.List;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginFriend.class */
public class PacketLoginFriend extends Packet {
    private List<DefaultFriend> friends;

    public PacketLoginFriend(List<DefaultFriend> friends) {
        this.friends = friends;
    }

    public PacketLoginFriend() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        List<DefaultFriend> players = new ArrayList<>();
        int a = buf.readInt();
        for (int i = 0; i < a; i++) {
            players.add(buf.readChatUser());
        }
        this.friends = new ArrayList();
        this.friends.addAll(players);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeInt(getFriends().size());
        for (int i = 0; i < getFriends().size(); i++) {
            DefaultFriend p = getFriends().get(i);
            buf.writeUser(p);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public List<DefaultFriend> getFriends() {
        return this.friends;
    }
}

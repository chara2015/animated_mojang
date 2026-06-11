package net.labymod.core.labyconnect.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/PacketHandler.class */
public abstract class PacketHandler extends SimpleChannelInboundHandler<Object> {
    public abstract void handle(PacketHelloPong packetHelloPong);

    public abstract void handle(PacketPlayPlayerOnline packetPlayPlayerOnline);

    public abstract void handle(PacketLoginComplete packetLoginComplete);

    public abstract void handle(PacketKick packetKick);

    public abstract void handle(PacketDisconnect packetDisconnect);

    public abstract void handle(PacketLoginFriend packetLoginFriend);

    public abstract void handle(PacketLoginRequest packetLoginRequest);

    public abstract void handle(PacketNotAllowed packetNotAllowed);

    public abstract void handle(PacketPing packetPing);

    public abstract void handle(PacketServerMessage packetServerMessage);

    public abstract void handle(PacketMessage packetMessage);

    public abstract void handle(PacketPlayTyping packetPlayTyping);

    public abstract void handle(PacketPlayRequestAddFriendResponse packetPlayRequestAddFriendResponse);

    public abstract void handle(PacketPlayRequestRemove packetPlayRequestRemove);

    public abstract void handle(PacketPlayFriendRemove packetPlayFriendRemove);

    public abstract void handle(PacketPlayFriendStatus packetPlayFriendStatus);

    public abstract void handle(PacketPlayFriendPlayingOn packetPlayFriendPlayingOn);

    public abstract void handle(PacketPlayInviteLanWorld packetPlayInviteLanWorld);

    public abstract void handle(PacketPlayAcceptLanWorldInvite packetPlayAcceptLanWorldInvite);

    public abstract void handle(PacketPlayRejectLanWorldInvite packetPlayRejectLanWorldInvite);

    public abstract void handle(PacketIceCredentials packetIceCredentials);

    public abstract void handle(PacketLoginTime packetLoginTime);

    public abstract void handle(PacketEncryptionRequest packetEncryptionRequest);

    public abstract void handle(PacketMojangStatus packetMojangStatus);

    public abstract void handle(PacketUpdateCosmetics packetUpdateCosmetics);

    public abstract void handle(PacketAddonMessage packetAddonMessage);

    public abstract void handle(PacketUserBadge packetUserBadge);

    public abstract void handle(PacketActionPlayResponse packetActionPlayResponse);

    public abstract void handle(PacketActionRequestResponse packetActionRequestResponse);

    public abstract void handle(PacketAddonDevelopment packetAddonDevelopment);

    public abstract void handle(PacketActionBroadcast packetActionBroadcast);

    public abstract void handle(PacketPlayBroadcastPayload packetPlayBroadcastPayload);

    protected void channelRead0(ChannelHandlerContext ctx, Object packet) {
        handlePacket((Packet) packet);
    }

    protected void handlePacket(Packet packet) {
        packet.handle(this);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}

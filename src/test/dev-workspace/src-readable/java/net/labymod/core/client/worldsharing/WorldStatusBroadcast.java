package net.labymod.core.client.worldsharing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/WorldStatusBroadcast.class */
public final class WorldStatusBroadcast {
    private final NetEventHandler netEventHandler;

    public WorldStatusBroadcast(NetEventHandler netEventHandler) {
        this.netEventHandler = netEventHandler;
    }

    public void send(UUID id, Type type) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (id != null && session != null && session.isAuthenticated()) {
            session.sendAddonDevelopment(Worldsharing.NAMESPACE, new UUID[]{id}, new byte[]{(byte) type.ordinal()});
        }
    }

    public void send(Type type) {
        LabyConnectSession session;
        UUID[] uuids;
        if (type == Type.INVITE || (session = Laby.labyAPI().labyConnect().getSession()) == null || !session.isAuthenticated() || (uuids = getFilteredFriends(session)) == null || uuids.length < 1) {
            return;
        }
        session.sendAddonDevelopment(Worldsharing.NAMESPACE, uuids, new byte[]{(byte) type.ordinal()});
    }

    public void invite(UUID uuid) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (uuid != null && session != null && session.isAuthenticated()) {
            session.sendAddonDevelopment(Worldsharing.NAMESPACE, new UUID[]{uuid}, new byte[]{(byte) Type.INVITE.ordinal()});
        }
    }

    @Subscribe
    public void onPlayerOnline(LabyConnectFriendStatusEvent event) {
        boolean isPublished = this.netEventHandler.isPublished();
        if (!isPublished || this.netEventHandler.worldPrivacy().isLan()) {
            return;
        }
        boolean inviteOnly = this.netEventHandler.worldPrivacy().isWhitelist();
        if (shouldReceive(event.friend(), inviteOnly, Arrays.asList(Worldsharing.integratedServer().getWhitelist()))) {
            send(event.friend().getUniqueId(), event.wasOnline() ? Type.REMOVE : Type.ADD);
        }
    }

    @Nullable
    public List<String> getWhitelist() {
        String[] whitelist = Worldsharing.integratedServer().getWhitelist();
        List<String> whitelistList = null;
        if (whitelist != null) {
            whitelistList = Arrays.asList(whitelist);
        }
        return whitelistList;
    }

    private UUID[] getFilteredFriends(LabyConnectSession session) {
        List<Friend> labyFriends = session.getFriends();
        if (labyFriends == null) {
            return null;
        }
        List<UUID> friendsUUIDs = new ArrayList<>();
        List<String> whitelist = getWhitelist();
        if (this.netEventHandler.worldPrivacy().isLan()) {
            return null;
        }
        boolean isInviteOnly = this.netEventHandler.isPublished() && this.netEventHandler.worldPrivacy().isWhitelist();
        for (Friend friend : labyFriends) {
            if (friend.isOnline() && shouldReceive(friend, isInviteOnly, whitelist)) {
                friendsUUIDs.add(friend.getUniqueId());
            }
        }
        return (UUID[]) friendsUUIDs.toArray(x$0 -> {
            return new UUID[x$0];
        });
    }

    private boolean shouldReceive(Friend friend, boolean inviteOnly, List<String> whitelist) {
        if (inviteOnly && whitelist != null) {
            return whitelist.contains(friend.getName());
        }
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/WorldStatusBroadcast$Type.class */
    public enum Type {
        ADD,
        REMOVE,
        INVITE;

        public static Type fromByte(byte b) {
            if (b < 0 || b >= values().length) {
                return null;
            }
            return values()[b];
        }
    }
}

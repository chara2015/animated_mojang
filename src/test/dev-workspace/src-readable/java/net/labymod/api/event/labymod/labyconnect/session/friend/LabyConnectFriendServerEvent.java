package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/friend/LabyConnectFriendServerEvent.class */
public class LabyConnectFriendServerEvent extends LabyConnectFriendEvent {
    private final ServerInfo server;
    private final ServerInfo previousServer;

    public LabyConnectFriendServerEvent(LabyConnect api, Friend friend, ServerInfo server) {
        this(api, friend, server, null);
    }

    public LabyConnectFriendServerEvent(LabyConnect api, Friend friend, ServerInfo server, ServerInfo previousServer) {
        super(api, friend);
        this.server = server;
        this.previousServer = previousServer;
    }

    public ServerInfo serverInfo() {
        return this.server;
    }

    @Nullable
    public ServerInfo getPreviousServer() {
        return this.previousServer;
    }
}

package net.minecraft.server.jsonrpc.internalapi;

import java.util.Collection;
import net.minecraft.server.jsonrpc.methods.ClientInfo;
import net.minecraft.server.players.IpBanListEntry;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.UserBanListEntry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/internalapi/MinecraftBanListService.class */
public interface MinecraftBanListService {
    void addUserBan(UserBanListEntry userBanListEntry, ClientInfo clientInfo);

    void removeUserBan(NameAndId nameAndId, ClientInfo clientInfo);

    Collection<UserBanListEntry> getUserBanEntries();

    Collection<IpBanListEntry> getIpBanEntries();

    void addIpBan(IpBanListEntry ipBanListEntry, ClientInfo clientInfo);

    void clearIpBans(ClientInfo clientInfo);

    void removeIpBan(String str, ClientInfo clientInfo);

    void clearUserBans(ClientInfo clientInfo);
}

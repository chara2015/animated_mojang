package net.labymod.api.labyconnect;

import com.google.gson.JsonElement;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/LabyConnectSession.class */
public interface LabyConnectSession {
    void removeFriend(UUID uuid);

    void sendCurrentServer(ServerData serverData, String str, boolean z);

    void sendLeaveCurrentServer();

    void sendSettings();

    @Nullable
    Friend getFriend(UUID uuid);

    @Nullable
    Chat getChat(UUID uuid);

    @Nullable
    IncomingFriendRequest getIncomingRequest(UUID uuid);

    @Nullable
    OutgoingFriendRequest getOutgoingRequest(UUID uuid);

    List<Friend> getFriends();

    Collection<Friend> getOnlineFriends();

    List<Chat> getChats();

    TokenStorage tokenStorage();

    List<IncomingFriendRequest> getIncomingRequests();

    void sendFriendRequest(String str);

    void acceptFriendRequest(UUID uuid);

    void declineFriendRequest(UUID uuid);

    List<OutgoingFriendRequest> getOutgoingRequests();

    void playEmote(short s);

    void spray(short s, int i, double d, double d2, double d3, Direction direction, float f);

    User self();

    void inviteToLanWorld(Friend friend);

    void acceptLanWorldInvite(Friend friend);

    void rejectLanWorldInvite(Friend friend);

    void retractLanWorldInvite(Friend friend);

    void sendAddonMessage(String str, byte[] bArr);

    void sendAddonDevelopment(String str, UUID[] uuidArr, byte[] bArr);

    void sendChatMessage(UUID uuid, TextChatMessage textChatMessage);

    UUID sendChatFile(UUID uuid, String str, byte[] bArr);

    boolean isAuthenticated();

    boolean isPremium();

    boolean isConnectionEstablished();

    @Deprecated
    void refreshOpenChat(UUID uuid);

    void sendBroadcastPayload(@NotNull String str, @NotNull JsonElement jsonElement);

    void sendSurroundingBroadcastPayload(@NotNull String str, @NotNull JsonElement jsonElement);

    void sendTextureUpdated(MojangTextureChangedResponse mojangTextureChangedResponse);

    byte[] getFile(UUID uuid);

    default int getOnlineFriendCount() {
        return getOnlineFriends().size();
    }

    default int getUnreadCount() {
        int count = 0;
        for (Chat chat : getChats()) {
            count += chat.getUnreadCount();
        }
        return count;
    }
}

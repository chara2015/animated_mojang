package net.labymod.api.labyconnect.protocol.model.friend;

import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/friend/Friend.class */
public interface Friend extends User {
    boolean isOnline();

    @Nullable
    ServerInfo getServer();

    @NotNull
    Chat chat();

    void remove();

    boolean isPinned();

    void pin();

    void unpin();

    long getLastOnline();

    String getNote();

    void openNoteEditor();

    long getLastServerChange();

    boolean isHostingWorld();

    void setHostingWorld(boolean z);

    @Deprecated
    default Chat getChat() {
        return chat();
    }
}

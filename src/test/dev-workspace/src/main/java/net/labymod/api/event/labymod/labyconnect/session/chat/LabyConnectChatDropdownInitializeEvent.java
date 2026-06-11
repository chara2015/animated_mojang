package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/chat/LabyConnectChatDropdownInitializeEvent.class */
public class LabyConnectChatDropdownInitializeEvent implements Event {
    private final Friend friend;
    private final ContextMenu menu;

    public LabyConnectChatDropdownInitializeEvent(@NotNull Friend friend, @NotNull ContextMenu menu) {
        this.friend = friend;
        this.menu = menu;
    }

    @NotNull
    public Friend friend() {
        return this.friend;
    }

    @NotNull
    public ContextMenu menu() {
        return this.menu;
    }
}

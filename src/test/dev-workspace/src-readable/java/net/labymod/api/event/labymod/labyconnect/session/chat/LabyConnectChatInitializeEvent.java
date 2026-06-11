package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/chat/LabyConnectChatInitializeEvent.class */
public class LabyConnectChatInitializeEvent implements Event {
    private final Friend friend;
    private final HorizontalListWidget buttonContainer;

    public LabyConnectChatInitializeEvent(@NotNull Friend friend, @NotNull HorizontalListWidget buttonContainer) {
        this.friend = friend;
        this.buttonContainer = buttonContainer;
    }

    @NotNull
    public Friend friend() {
        return this.friend;
    }

    @NotNull
    public HorizontalListWidget buttonContainer() {
        return this.buttonContainer;
    }
}

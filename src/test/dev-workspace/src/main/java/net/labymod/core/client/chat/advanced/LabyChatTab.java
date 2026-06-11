package net.labymod.core.client.chat.advanced;

import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/advanced/LabyChatTab.class */
public class LabyChatTab extends ChatTab {
    private final ListSession<Widget> listSession;
    private final Friend friend;

    protected LabyChatTab(@NotNull ChatWindow chatWindow, @NotNull RootChatTabConfig rootConfig, Friend friend) {
        super(chatWindow, rootConfig);
        this.friend = friend;
        this.listSession = new ListSession<>();
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    public void copy(@NotNull ChatTab chatTab) {
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    @NotNull
    public String getCustomName() {
        return this.friend.getName();
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    @NotNull
    public Widget createContentWidget(WindowAccessor window) {
        return new LabyConnectChatWidget(this.friend.chat(), this.listSession);
    }
}

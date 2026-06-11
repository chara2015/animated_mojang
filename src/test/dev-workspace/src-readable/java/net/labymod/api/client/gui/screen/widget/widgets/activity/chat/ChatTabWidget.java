package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/chat/ChatTabWidget.class */
@AutoWidget
public abstract class ChatTabWidget extends AbstractWidget<Widget> {
    private final RootChatTabConfig tab;
    protected boolean chatOpen;
    private int unreadMessages = 0;
    private Runnable messageHandler;

    public abstract void handleInput(String str);

    protected ChatTabWidget(RootChatTabConfig tab) {
        this.tab = tab;
    }

    public RootChatTabConfig getChatTab() {
        return this.tab;
    }

    public int getUnreadMessages() {
        return this.unreadMessages;
    }

    public Component getUnreadMessagesComponent() {
        return this.unreadMessages > 9 ? Component.text("!!!") : Component.text(Integer.valueOf(this.unreadMessages));
    }

    public void setMessageHandler(Runnable messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            this.unreadMessages = 0;
            if (this.messageHandler != null) {
                this.messageHandler.run();
            }
        }
    }

    public void onChatClose() {
        this.chatOpen = false;
        setActive(false);
    }

    public void onChatOpen() {
        this.chatOpen = true;
        setActive(true);
    }
}

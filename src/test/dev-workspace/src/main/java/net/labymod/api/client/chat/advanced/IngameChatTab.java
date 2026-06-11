package net.labymod.api.client.chat.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.util.AddressUtil;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/advanced/IngameChatTab.class */
public final class IngameChatTab extends ChatTab {
    public static final String CUSTOM_BACKGROUND = "custom_background";
    private final List<AdvancedChatMessage> messages;
    private int counter;

    public IngameChatTab(@NotNull ChatWindow chatWindow, @NotNull RootChatTabConfig rootConfig) {
        super(chatWindow, rootConfig);
        this.messages = new ArrayList();
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    @NotNull
    public String getCustomName() {
        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null) {
            return I18n.translate("labymod.ui.navigation.singleplayer", new Object[0]);
        }
        String host = serverData.address().toString();
        if (AddressUtil.isLocalHost(host)) {
            host = "localhost";
        }
        Optional<ServerGroup> serverGroup = Laby.labyAPI().labyNetController().getServerByIp(host);
        if (serverGroup.isPresent()) {
            return serverGroup.get().getNiceName();
        }
        return serverData.getName();
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    public void copy(@NotNull ChatTab chatTab) {
        if (!(chatTab instanceof IngameChatTab)) {
            return;
        }
        IngameChatTab ingameChatTab = (IngameChatTab) chatTab;
        this.messages.addAll(ingameChatTab.messages);
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    @NotNull
    public Widget createContentWidget(WindowAccessor window) {
        return new ChatMessagesWidget(this, window);
    }

    @Override // net.labymod.api.configuration.labymod.chat.ChatTab
    public void invalidateCache() {
        super.invalidateCache();
        for (AdvancedChatMessage message : this.messages) {
            message.invalidateCache();
        }
    }

    @NotNull
    public List<AdvancedChatMessage> getMessages() {
        return this.messages;
    }

    public void handleInput(@NotNull AdvancedChatMessage message) {
        Objects.requireNonNull(message, "Message cannot be null!");
        AdvancedChatTabMessageEvent event = new AdvancedChatTabMessageEvent(this, message);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        int limit = config().chatLimit().get().intValue();
        for (int i = this.messages.size(); i >= limit; i--) {
            this.messages.remove(i - 1);
        }
        this.messages.add(0, message);
        this.counter++;
        this.unread++;
    }

    public void handleMessageDelete(@NotNull ChatMessage message) {
        this.messages.removeIf(advancedMessage -> {
            return advancedMessage.chatMessage().messageId().equals(message.messageId());
        });
    }

    public void handleMessageUpdate(@NotNull ChatMessage message) {
        for (AdvancedChatMessage advancedMessage : this.messages) {
            if (advancedMessage.chatMessage().messageId().equals(message.messageId())) {
                advancedMessage.invalidateCache();
                return;
            }
        }
    }

    public int getCounter() {
        return this.counter;
    }
}

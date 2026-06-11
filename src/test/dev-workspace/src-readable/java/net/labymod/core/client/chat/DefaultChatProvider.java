package net.labymod.core.client.chat;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.client.chat.filter.FilterChatService;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.core.client.chat.autotext.AutoTextHandler;
import net.labymod.core.client.chat.autotext.DefaultAutoTextService;
import net.labymod.core.client.chat.prefix.DefaultChatPrefixRegistry;
import net.labymod.core.configuration.labymod.DefaultAutoTextConfig;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatProvider.class */
@Singleton
@Implements(ChatProvider.class)
public final class DefaultChatProvider implements ChatProvider {
    private final ChatController chatController;
    private final ChatInputRegistry chatInputRegistry;
    private final FilterChatService filterChatService;
    private final ConfigProvider<AutoTextConfigAccessor> autoTextConfig;
    private final ChatPrefixRegistry prefixRegistry = new DefaultChatPrefixRegistry();
    private final ConfigProvider<ChatConfigAccessor> chatConfig = DefaultChatConfig.ChatConfigProvider.INSTANCE;

    @Inject
    public DefaultChatProvider(ChatController chatController, ChatInputRegistry chatInputRegistry, FilterChatService filterChatService, EventBus eventBus) {
        this.chatController = chatController;
        this.chatInputRegistry = chatInputRegistry;
        this.filterChatService = filterChatService;
        this.chatConfig.loadJson();
        this.autoTextConfig = DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE;
        this.autoTextConfig.loadJson();
        eventBus.registerListener(new AutoTextHandler());
    }

    public void initialize() {
        ((DefaultAutoTextService) autoTextService()).initialize();
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public ChatController chatController() {
        return this.chatController;
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public ChatInputRegistry chatInputService() {
        return this.chatInputRegistry;
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public FilterChatService filterChatService() {
        return this.filterChatService;
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public AutoTextService autoTextService() {
        return Laby.references().autoTextService();
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public AutoTextConfigAccessor autoTextConfigAccessor() {
        return (AutoTextConfigAccessor) this.autoTextConfig.get();
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public ChatPrefixRegistry prefixRegistry() {
        return this.prefixRegistry;
    }

    public ConfigProvider<AutoTextConfigAccessor> autoTextConfigProvider() {
        return this.autoTextConfig;
    }

    @Override // net.labymod.api.client.chat.ChatProvider
    public ChatConfigAccessor chatConfigAccessor() {
        return (ChatConfigAccessor) this.chatConfig.get();
    }

    public ConfigProvider<ChatConfigAccessor> chatConfigProvider() {
        return this.chatConfig;
    }
}

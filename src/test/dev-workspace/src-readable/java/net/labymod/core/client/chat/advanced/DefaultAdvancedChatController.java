package net.labymod.core.client.chat.advanced;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.advanced.AdvancedChatController;
import net.labymod.api.client.chat.advanced.IngameChatTab;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatMessageAddEvent;
import net.labymod.api.event.client.chat.ChatMessageUpdateEvent;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReceiveEvent;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReloadEvent;
import net.labymod.api.event.client.lifecycle.ShutdownEvent;
import net.labymod.api.event.labymod.config.SettingResetEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/advanced/DefaultAdvancedChatController.class */
@Singleton
@Implements(AdvancedChatController.class)
public class DefaultAdvancedChatController implements AdvancedChatController {
    private static final String CHAT_MESSAGE_KEY = "Minecraft-ChatMessage";
    private final Set<ChatWindow> windows = new HashSet();

    @Inject
    public DefaultAdvancedChatController(EventBus eventBus) {
        loadConfiguration();
        eventBus.registerListener(new ChatDuplicateMessageHandler());
    }

    @Override // net.labymod.api.client.chat.advanced.AdvancedChatController
    public Set<ChatWindow> getWindows() {
        return this.windows;
    }

    @Override // net.labymod.api.client.chat.advanced.AdvancedChatController
    public void saveConfig() {
        DefaultChatConfig.ChatConfigProvider.INSTANCE.save();
    }

    @Override // net.labymod.api.client.chat.advanced.AdvancedChatController
    public boolean hasSecondaryWindow() {
        return this.windows.size() > 1;
    }

    @Override // net.labymod.api.client.chat.advanced.AdvancedChatController
    @NotNull
    public ChatWindow getOrCreateSecondaryWindow(@Nullable Supplier<RootChatTabConfig> defaultTabSupplier) {
        ChatWindow secondaryWindow = null;
        for (ChatWindow window : this.windows) {
            if (!window.isMainWindow()) {
                secondaryWindow = window;
            }
        }
        if (secondaryWindow != null) {
            return secondaryWindow;
        }
        RootChatTabConfig defaultTab = defaultTabSupplier == null ? null : defaultTabSupplier.get();
        if (defaultTab == null) {
            defaultTab = new RootChatTabConfig(0, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig("Second chat"));
        }
        ChatWindowConfig config = new ChatWindowConfig(defaultTab);
        config.setPosition(0.21f, 2.15f, HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM);
        DefaultChatConfig.ChatConfigProvider.INSTANCE.get().getWindows().add(config);
        saveConfig();
        reload();
        for (ChatWindow window2 : this.windows) {
            for (ChatTab tab : window2.getTabs()) {
                if (tab.rootConfig().getUniqueID().equals(defaultTab.getUniqueID())) {
                    return window2;
                }
            }
        }
        throw new NullPointerException("Could not find the newly created secondary window");
    }

    public void addWindow(ChatWindow chatWindow) {
        this.windows.add(chatWindow);
        ChatConfigAccessor chatConfigAccessor = Laby.labyAPI().chatProvider().chatConfigAccessor();
        chatConfigAccessor.getWindows().add(chatWindow.config());
        saveConfig();
    }

    public void deleteWindow(ChatWindow chatWindow) {
        this.windows.remove(chatWindow);
        ChatConfigAccessor chatConfigAccessor = Laby.labyAPI().chatProvider().chatConfigAccessor();
        chatConfigAccessor.getWindows().remove(chatWindow.config());
        saveConfig();
    }

    @Subscribe
    public void handleChatMessageReceive(ChatMessageAddEvent event) {
        if (ThreadSafe.isRenderThread()) {
            fireAdvancedChatReceiveEvent(event.chatMessage());
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                fireAdvancedChatReceiveEvent(event.chatMessage());
            });
        }
    }

    @Subscribe(127)
    public void handleChatUpdate(ChatMessageUpdateEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        ingameChatTab(tab -> {
            if (event.newComponent() == null) {
                tab.handleMessageDelete(event.message());
            } else {
                tab.handleMessageUpdate(event.message());
            }
        });
    }

    @Subscribe(127)
    public void handleChatClear(ChatClearEvent event) {
        if (event.isCancelled()) {
            return;
        }
        boolean force = Laby.labyAPI().minecraft().isMouseLocked() && Key.F3.isPressed() && Key.D.isPressed();
        ingameChatTab(tab -> {
            if (force || !tab.config().antiChatClear().get().booleanValue()) {
                tab.getMessages().clear();
            }
        });
    }

    @Subscribe
    public void handleGameShutdown(ShutdownEvent event) {
        saveConfig();
    }

    @Subscribe
    public void handleChatConfigReset(SettingResetEvent event) {
        if (event.setting().getPath().equals("settings.ingame.advancedChat.enabled")) {
            Laby.labyAPI().chatProvider().chatConfigAccessor().getWindows().clear();
            reload();
        }
    }

    @Override // net.labymod.api.client.chat.advanced.AdvancedChatController
    public void reload() {
        this.windows.clear();
        loadConfiguration();
        Laby.references().chatAccessor().reload();
        Laby.fireEvent(new AdvancedChatReloadEvent());
    }

    private void loadConfiguration() {
        List<ChatWindowConfig> windowConfigs = Laby.labyAPI().chatProvider().chatConfigAccessor().getWindows();
        boolean foundServerTab = false;
        removeEmptyWindows(windowConfigs);
        for (ChatWindowConfig windowConfig : windowConfigs) {
            boolean foundWindow = false;
            Iterator<ChatWindow> it = this.windows.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ChatWindow chatWindowWidget = it.next();
                if (chatWindowWidget.config() == windowConfig) {
                    if (!foundServerTab) {
                        Iterator<ChatTab> it2 = chatWindowWidget.getTabs().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            ChatTab tab = it2.next();
                            if (tab.rootConfig().type().get() == RootChatTabConfig.Type.SERVER) {
                                foundServerTab = true;
                                break;
                            }
                        }
                    }
                    foundWindow = true;
                }
            }
            if (!foundWindow) {
                windowConfig.checkForFocusedTab();
                DefaultChatWindow chatWindowWidget2 = new DefaultChatWindow(windowConfig);
                this.windows.add(chatWindowWidget2);
                windowConfig.getTabs().sort(Comparator.comparingInt(config -> {
                    return config.index().get().intValue();
                }));
                for (RootChatTabConfig tabConfig : windowConfig.getTabs()) {
                    if (tabConfig.type().get() == RootChatTabConfig.Type.SERVER) {
                        if (foundServerTab) {
                            tabConfig.type().set(RootChatTabConfig.Type.CUSTOM);
                        } else {
                            foundServerTab = true;
                        }
                    }
                    boolean foundTab = false;
                    Iterator<ChatTab> it3 = chatWindowWidget2.getTabs().iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        ChatTab tab2 = it3.next();
                        if (tab2.rootConfig() == tabConfig) {
                            foundTab = true;
                            break;
                        }
                    }
                    if (!foundTab) {
                        chatWindowWidget2.initializeTab(tabConfig, null, false);
                    }
                }
            }
        }
        if (windowConfigs.isEmpty()) {
            windowConfigs.add(new ChatWindowConfig());
            loadConfiguration();
        }
    }

    private void removeEmptyWindows(List<ChatWindowConfig> windowConfigs) {
        this.windows.removeIf(window -> {
            if (window.getTabs().isEmpty()) {
                windowConfigs.remove(window.config());
                return true;
            }
            return false;
        });
        windowConfigs.removeIf(config -> {
            return config.getTabs().isEmpty();
        });
    }

    private void ingameChatTab(Consumer<IngameChatTab> tab) {
        for (ChatWindow window : this.windows) {
            for (ChatTab chatTab : window.getTabs()) {
                if (chatTab instanceof IngameChatTab) {
                    tab.accept((IngameChatTab) chatTab);
                }
            }
        }
    }

    private void fireAdvancedChatReceiveEvent(@NotNull ChatMessage vanillaChatMessage) {
        AdvancedChatReceiveEvent receiveEvent = new AdvancedChatReceiveEvent(vanillaChatMessage);
        Laby.fireEvent(receiveEvent);
        if (receiveEvent.isCancelled()) {
            return;
        }
        ChatMessage chatMessage = receiveEvent.chatMessage();
        ingameChatTab(tab -> {
            if (tab.config().antiChatClear().get().booleanValue() && chatMessage.getPlainText().trim().isEmpty() && !chatMessage.containsIcon()) {
                return;
            }
            AdvancedChatMessage chat = AdvancedChatMessage.chat(chatMessage);
            chat.metadata().set(CHAT_MESSAGE_KEY, chatMessage);
            tab.handleInput(chat);
        });
    }
}

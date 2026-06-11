package net.labymod.core.configuration.labymod.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.loader.annotation.ConfigName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/chat/DefaultChatConfig.class */
@ConfigName("chat")
public class DefaultChatConfig extends Config implements ChatConfigAccessor {
    private List<ChatWindowConfig> windows = new ArrayList(Collections.singletonList(new ChatWindowConfig()));

    @Override // net.labymod.api.configuration.labymod.chat.ChatConfigAccessor
    public List<ChatWindowConfig> getWindows() {
        return this.windows;
    }

    public void setWindows(List<ChatWindowConfig> windows) {
        this.windows = windows;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/chat/DefaultChatConfig$ChatConfigProvider.class */
    public static class ChatConfigProvider extends ConfigProvider<ChatConfigAccessor> {
        public static final ChatConfigProvider INSTANCE = new ChatConfigProvider();

        private ChatConfigProvider() {
        }

        @Override // net.labymod.api.configuration.loader.ConfigProvider
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultChatConfig.class;
        }
    }
}

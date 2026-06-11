package net.labymod.api.configuration.labymod.chat;

import java.util.List;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/ChatConfigAccessor.class */
public interface ChatConfigAccessor extends ConfigAccessor {
    List<ChatWindowConfig> getWindows();
}

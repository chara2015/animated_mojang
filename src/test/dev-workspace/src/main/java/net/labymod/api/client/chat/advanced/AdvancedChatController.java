package net.labymod.api.client.chat.advanced;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/advanced/AdvancedChatController.class */
@Referenceable
public interface AdvancedChatController {
    Set<ChatWindow> getWindows();

    void saveConfig();

    void reload();

    boolean hasSecondaryWindow();

    @NotNull
    ChatWindow getOrCreateSecondaryWindow(@Nullable Supplier<RootChatTabConfig> supplier);

    @NotNull
    default ChatWindow getOrCreateSecondaryWindow() {
        return getOrCreateSecondaryWindow(null);
    }

    default <T extends ChatTab> boolean forEachChatTab(@NotNull Class<T> clazz, @NotNull Predicate<T> predicate) {
        for (ChatWindow window : getWindows()) {
            if (window.forEachChatTab(clazz, predicate)) {
                return true;
            }
        }
        return false;
    }

    default boolean forEachIngameChatTab(@NotNull Predicate<IngameChatTab> predicate) {
        for (ChatWindow window : getWindows()) {
            if (window.forEachIngameChatTab(predicate)) {
                return true;
            }
        }
        return false;
    }
}

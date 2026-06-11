package net.labymod.api.client.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatExecutor.class */
@Referenceable
public interface ChatExecutor {
    void performAction(ClickEvent clickEvent);

    void insertText(String str, boolean z);

    void chat(String str);

    void chat(String str, boolean z);

    void displayClientMessage(Component component, boolean z);

    void displayActionBar(Component component, boolean z);

    ActionBar displayActionBarContinuous(Component component);

    void openUrl(String str, boolean z);

    void openUrl(String str);

    void openUrlOrJoinServer(String str);

    void openFile(String str);

    void suggestCommand(String str);

    void copyToClipboard(String str);

    @Nullable
    String getChatInputMessage();

    @Nullable
    Title getTitle();

    void showTitle(@NotNull Title title);

    void clearTitle();

    default void insertText(String insertion) {
        insertText(insertion, false);
    }

    default void displayClientMessage(String message) {
        displayClientMessage((Component) Component.text(message), false);
    }

    default void displayClientMessage(String message, boolean actionBar) {
        displayClientMessage(Component.text(message), actionBar);
    }

    default void displayClientMessage(Component component) {
        displayClientMessage(component, false);
    }

    default void clearActionBar() {
        displayActionBar((Component) Component.empty(), false);
    }

    default void displayActionBar(String message) {
        displayActionBar((Component) Component.text(message), false);
    }

    default void displayActionBar(String message, boolean animate) {
        displayActionBar(Component.text(message), animate);
    }

    default void displayActionBar(Component message) {
        displayActionBar(message, false);
    }
}

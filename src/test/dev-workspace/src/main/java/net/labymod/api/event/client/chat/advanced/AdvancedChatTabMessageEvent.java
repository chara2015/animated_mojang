package net.labymod.api.event.client.chat.advanced;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/advanced/AdvancedChatTabMessageEvent.class */
public class AdvancedChatTabMessageEvent extends DefaultCancellable implements Event {
    private final ChatTab tab;
    private final AdvancedChatMessage message;

    public AdvancedChatTabMessageEvent(@NotNull ChatTab tab, @NotNull AdvancedChatMessage originalMessage) {
        this.tab = tab;
        this.message = originalMessage;
    }

    @NotNull
    public ChatTab tab() {
        return this.tab;
    }

    @NotNull
    public AdvancedChatMessage message() {
        return this.message;
    }

    @NotNull
    public Component component() {
        return this.message.component();
    }

    public void setMessage(@NotNull Component message) {
        Objects.requireNonNull(message, "Message cannot be null, cancel the event instead!");
        this.message.updateComponent(message);
    }
}

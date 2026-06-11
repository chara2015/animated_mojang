package net.labymod.v26_1_1.client.chat;

import java.util.UUID;
import net.labymod.api.client.options.ChatVisibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/chat/ChatMessageMeta.class */
public class ChatMessageMeta {
    private static final ChatMessageMeta SYSTEM = new ChatMessageMeta(ChatVisibility.COMMANDS_ONLY, null);
    private final ChatVisibility visibility;
    private final UUID sender;

    private ChatMessageMeta(ChatVisibility visibility, UUID sender) {
        this.visibility = visibility;
        this.sender = sender;
    }

    public static ChatMessageMeta system() {
        return SYSTEM;
    }

    public static ChatMessageMeta player(UUID sender) {
        return new ChatMessageMeta(ChatVisibility.SHOWN, sender);
    }

    public ChatVisibility visibility() {
        return this.visibility;
    }

    public UUID getSender() {
        return this.sender;
    }
}

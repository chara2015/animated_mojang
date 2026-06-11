package net.labymod.v26_1_1.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends MutableComponent {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, Component wrapped) {
        super(PlainTextContents.EMPTY, List.of(wrapped), Style.EMPTY);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}

package net.labymod.v1_21_11.client.chat;

import java.util.List;
import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<GuiMessage> getMessages();

    List<GuiMessage.Line> getFormattedMessages();

    void injectFormattedMessages(int i, Component component, GuiMessage guiMessage);

    void setMessageMeta(Component component, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(Component component);
}

package net.labymod.v26_2_snapshot_8.client.chat;

import java.util.List;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<GuiMessage> getMessages();

    List<GuiMessage.Line> getFormattedMessages();

    void injectFormattedMessages(int i, Component component, GuiMessage guiMessage);

    void setMessageMeta(Component component, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(Component component);
}

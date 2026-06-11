package net.labymod.v1_21_11.client.chat;

import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.chat.InternalChatModifier;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/chat/VersionedChatModifier.class */
@Singleton
@Implements(InternalChatModifier.class)
public class VersionedChatModifier implements InternalChatModifier {
    private static final Logging LOGGER = Logging.create(VersionedChatModifier.class);
    private final ComponentMapper componentMapper;

    @Inject
    public VersionedChatModifier(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    public void fireChatClear(boolean clearHistory) {
        Minecraft.getInstance().gui.getChat().clearMessages(clearHistory);
    }

    public void deleteMessage(ChatMessage message) {
        VersionedChatComponent accessor = Minecraft.getInstance().gui.getChat();
        accessor.getMessages().removeIf(messageFilter(message));
        accessor.getFormattedMessages().removeIf(formattedFilter(message));
    }

    public void editMessage(ChatMessage message, Component component) {
        VersionedChatComponent accessor = Minecraft.getInstance().gui.getChat();
        int index = CollectionHelper.indexOf(accessor.getMessages(), messageFilter(message));
        if (index == -1) {
            LOGGER.warn("Tried to edit unknown message", new Object[0]);
            return;
        }
        GuiMessage prevMessage = accessor.getMessages().get(index);
        VersionedChatMessageComponent versionedChatMessageComponent = new VersionedChatMessageComponent(message, (net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(component));
        accessor.getMessages().set(index, new GuiMessage(prevMessage.addedTime(), versionedChatMessageComponent, prevMessage.signature(), prevMessage.tag()));
        Predicate<GuiMessage.Line> predicate = formattedFilter(message);
        int first = -1;
        int i = 0;
        while (true) {
            if (i >= accessor.getFormattedMessages().size()) {
                break;
            }
            if (!predicate.test(accessor.getFormattedMessages().get(i))) {
                i++;
            } else {
                first = i;
                break;
            }
        }
        if (first != -1) {
            accessor.getFormattedMessages().removeIf(formattedFilter(message));
            accessor.injectFormattedMessages(first, versionedChatMessageComponent, prevMessage);
        }
    }

    private Predicate<GuiMessage> messageFilter(ChatMessage message) {
        return m -> {
            if (m.content() instanceof VersionedChatMessageComponent) {
                return m.content().message().messageId().equals(message.messageId());
            }
            return false;
        };
    }

    private Predicate<GuiMessage.Line> formattedFilter(ChatMessage message) {
        return m -> {
            if (m.content() instanceof VersionedChatMessageCharSequence) {
                return ((VersionedChatMessageCharSequence) m.content()).message().messageId().equals(message.messageId());
            }
            return false;
        };
    }
}

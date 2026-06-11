package net.labymod.v1_8_9.client.chat;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/chat/VersionedChatModifier.class */
@Singleton
@Implements(InternalChatModifier.class)
public class VersionedChatModifier implements InternalChatModifier {
    private static final Logging LOGGER = Logging.create((Class<?>) VersionedChatModifier.class);
    private final ComponentMapper componentMapper;

    @Inject
    public VersionedChatModifier(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.core.client.chat.InternalChatModifier
    public void fireChatClear(boolean clearHistory) {
        ave.A().q.d().a();
    }

    @Override // net.labymod.core.client.chat.InternalChatModifier
    public void deleteMessage(ChatMessage message) {
        VersionedGuiNewChat accessor = ave.A().q.d();
        Predicate<ava> messageFilter = messageFilter(message);
        accessor.getMessages().removeIf(messageFilter);
        accessor.getFormattedMessages().removeIf(messageFilter);
    }

    @Override // net.labymod.core.client.chat.InternalChatModifier
    public void editMessage(ChatMessage message, Component component) {
        VersionedGuiNewChat accessor = ave.A().q.d();
        Predicate<ava> messageFilter = messageFilter(message);
        int index = CollectionHelper.indexOf(accessor.getMessages(), messageFilter);
        if (index == -1) {
            LOGGER.warn("Tried to edit unknown message", new Object[0]);
            return;
        }
        ava prevMessage = accessor.getMessages().get(index);
        VersionedChatMessageComponent versionedChatMessageComponent = new VersionedChatMessageComponent(message, (eu) this.componentMapper.toMinecraftComponent(component));
        accessor.getMessages().set(index, new ava(prevMessage.b(), versionedChatMessageComponent, prevMessage.c()));
        int first = -1;
        int i = 0;
        while (true) {
            if (i >= accessor.getFormattedMessages().size()) {
                break;
            }
            if (!messageFilter.test(accessor.getFormattedMessages().get(i))) {
                i++;
            } else {
                first = i;
                break;
            }
        }
        if (first != -1) {
            accessor.getFormattedMessages().removeIf(messageFilter);
            accessor.injectFormattedMessages(first, versionedChatMessageComponent, prevMessage);
        }
    }

    private Predicate<ava> messageFilter(ChatMessage message) {
        return m -> {
            if (m.a() instanceof VersionedChatMessageComponent) {
                return m.a().message().messageId().equals(message.messageId());
            }
            return false;
        };
    }
}

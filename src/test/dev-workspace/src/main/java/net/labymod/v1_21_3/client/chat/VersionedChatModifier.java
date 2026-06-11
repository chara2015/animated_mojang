package net.labymod.v1_21_3.client.chat;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/chat/VersionedChatModifier.class */
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
        fmg.Q().m.d().a(clearHistory);
    }

    @Override // net.labymod.core.client.chat.InternalChatModifier
    public void deleteMessage(ChatMessage message) {
        VersionedChatComponent accessor = fmg.Q().m.d();
        accessor.getMessages().removeIf(messageFilter(message));
        accessor.getFormattedMessages().removeIf(formattedFilter(message));
    }

    @Override // net.labymod.core.client.chat.InternalChatModifier
    public void editMessage(ChatMessage message, Component component) {
        VersionedChatComponent accessor = fmg.Q().m.d();
        int index = CollectionHelper.indexOf(accessor.getMessages(), messageFilter(message));
        if (index == -1) {
            LOGGER.warn("Tried to edit unknown message", new Object[0]);
            return;
        }
        flz prevMessage = accessor.getMessages().get(index);
        VersionedChatMessageComponent versionedChatMessageComponent = new VersionedChatMessageComponent(message, (xv) this.componentMapper.toMinecraftComponent(component));
        accessor.getMessages().set(index, new flz(prevMessage.b(), versionedChatMessageComponent, prevMessage.d(), prevMessage.e()));
        Predicate<a> predicate = formattedFilter(message);
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

    private Predicate<flz> messageFilter(ChatMessage message) {
        return m -> {
            if (m.c() instanceof VersionedChatMessageComponent) {
                return m.c().message().messageId().equals(message.messageId());
            }
            return false;
        };
    }

    private Predicate<a> formattedFilter(ChatMessage message) {
        return m -> {
            if (m.b() instanceof VersionedChatMessageCharSequence) {
                return ((VersionedChatMessageCharSequence) m.b()).message().messageId().equals(message.messageId());
            }
            return false;
        };
    }
}

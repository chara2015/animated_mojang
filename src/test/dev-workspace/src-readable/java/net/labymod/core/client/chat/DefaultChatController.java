package net.labymod.core.client.chat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatMessageAddEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.models.Implements;
import net.labymod.core.client.chat.feature.ChatMessageSenderHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatController.class */
@Singleton
@Implements(ChatController.class)
public class DefaultChatController implements ChatController {
    private final InternalChatModifier chatModifier;
    private State state;
    private final LabyConfig config = Laby.labyAPI().config();
    private final List<ChatMessage> messages = new ArrayList();

    @Inject
    public DefaultChatController(InternalChatModifier chatModifier, EventBus eventBus) {
        this.chatModifier = chatModifier;
        eventBus.registerListener(this);
        eventBus.registerListener(new ChatMessageSenderHandler());
    }

    @Override // net.labymod.api.client.chat.ChatController
    public int getMaxHistorySize() {
        return SubmissionOrders.DEBUG;
    }

    @Override // net.labymod.api.client.chat.ChatController
    public List<ChatMessage> getMessages() {
        return this.messages;
    }

    @Override // net.labymod.api.client.chat.ChatController
    public ChatMessage addMessage(Component component) {
        return addMessage(ChatMessage.builder().component(component).visibility(ChatVisibility.COMMANDS_ONLY).build());
    }

    @Override // net.labymod.api.client.chat.ChatController
    public ChatMessage addMessage(ChatMessage.Builder builder) {
        return addMessage(builder.build());
    }

    @Override // net.labymod.api.client.chat.ChatController
    public ChatMessage addMessage(ChatMessage chatMessage) {
        return addMessage(chatMessage, true);
    }

    private ChatMessage addMessage(ChatMessage chatMessage, boolean justReceived) {
        DefaultChatMessage message = (DefaultChatMessage) chatMessage;
        if (justReceived) {
            ChatReceiveEvent event = (ChatReceiveEvent) Laby.fireEvent(new ChatReceiveEvent(chatMessage));
            if (event.isCancelled()) {
                return null;
            }
            message.setMessageSent(true);
            if (event.isModified()) {
                message = new DefaultChatMessage(message.messageId(), message.timestamp(), message.originalComponent(), event.message(), message.visibility(), message.trustLevel(), message.getCachedSender());
                message.setMessageSent(true);
            }
        }
        this.messages.add(0, message);
        while (this.messages.size() > getMaxHistorySize()) {
            int index = this.messages.size() - 1;
            this.messages.remove(index);
        }
        Laby.fireEvent(new ChatMessageAddEvent(chatMessage));
        return message;
    }

    @Override // net.labymod.api.client.chat.ChatController
    public void clear() {
        this.chatModifier.fireChatClear(true);
    }

    @Subscribe(127)
    public void handleChatClear(ChatClearEvent event) {
        if (!isGlobalAntiChatClear() && !event.isCancelled()) {
            this.messages.clear();
        }
    }

    @Override // net.labymod.api.client.chat.ChatController
    public ChatMessage messageAt(int index) {
        if (this.messages.size() > index) {
            return this.messages.get(index);
        }
        return null;
    }

    @Override // net.labymod.api.client.chat.ChatController
    public void storeState() {
        this.state = new State(List.copyOf(this.messages));
    }

    @Override // net.labymod.api.client.chat.ChatController
    public void restoreState() {
        if (isGlobalAntiChatClear()) {
            this.state = null;
            return;
        }
        this.messages.clear();
        if (this.state != null) {
            for (ChatMessage message : this.state.messages.reversed()) {
                addMessage(message, false);
            }
            this.state = null;
        }
    }

    private boolean isGlobalAntiChatClear() {
        AdvancedChatConfig advancedChatConfig = this.config.ingame().advancedChat();
        return advancedChatConfig.enabled().get().booleanValue() && advancedChatConfig.globalAntiChatClear().get().booleanValue();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatController$State.class */
    public static final class State extends Record {
        private final List<ChatMessage> messages;

        public State(List<ChatMessage> messages) {
            this.messages = messages;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, State.class), State.class, "messages", "FIELD:Lnet/labymod/core/client/chat/DefaultChatController$State;->messages:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, State.class), State.class, "messages", "FIELD:Lnet/labymod/core/client/chat/DefaultChatController$State;->messages:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, State.class, Object.class), State.class, "messages", "FIELD:Lnet/labymod/core/client/chat/DefaultChatController$State;->messages:Ljava/util/List;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public List<ChatMessage> messages() {
            return this.messages;
        }
    }
}

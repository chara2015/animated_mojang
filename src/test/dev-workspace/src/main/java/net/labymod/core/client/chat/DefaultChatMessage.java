package net.labymod.core.client.chat;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatController;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ChatMessageGuessSenderEvent;
import net.labymod.api.event.client.chat.ChatMessageUpdateEvent;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatMessage.class */
public class DefaultChatMessage implements ChatMessage {
    private static final Logging LOGGER = Logging.create((Class<?>) ChatMessage.class);
    private static final InternalChatModifier CHAT_MODIFIER = LabyMod.references().internalChatModifier();
    private static final ChatController CONTROLLER = Laby.references().chatController();
    private static final ComponentRenderer COMPONENT_RENDERER = Laby.references().componentRenderer();
    private final UUID messageId;
    private final long timestamp;
    private final ChatVisibility visibility;
    private final ChatTrustLevel chatTrustLevel;
    private final Component originalComponent;
    private String originalPlainText;
    private String originalFormattedText;
    private long lastModifiedTimestamp;
    private Component component;
    private String plainText;
    private Boolean containsIcon;
    private String formattedText;
    private boolean triedSerialization;
    private String serializedText;
    private final UUID senderUniqueId;
    private final GameProfile senderProfile;
    private boolean messageSent;
    private Metadata metadata;

    public DefaultChatMessage(UUID messageId, long timestamp, Component originalComponent, Component component, ChatVisibility visibility, ChatTrustLevel chatTrustLevel) {
        this(messageId, timestamp, originalComponent, component, visibility, chatTrustLevel, null);
    }

    public DefaultChatMessage(UUID messageId, long timestamp, Component originalComponent, Component component, ChatVisibility visibility, ChatTrustLevel chatTrustLevel, UUID senderUniqueId) {
        this.lastModifiedTimestamp = -1L;
        this.metadata = Metadata.create();
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.originalComponent = originalComponent;
        this.component = component;
        this.visibility = visibility;
        this.chatTrustLevel = chatTrustLevel;
        ChatMessageGuessSenderEvent event = (ChatMessageGuessSenderEvent) Laby.fireEvent(new ChatMessageGuessSenderEvent(originalComponent, getPlainText(), senderUniqueId));
        this.senderUniqueId = event.getSenderUniqueId();
        this.senderProfile = event.getSenderProfile();
    }

    public DefaultChatMessage(UUID messageId, long timestamp, Component originalComponent, Component component, ChatVisibility visibility, ChatTrustLevel chatTrustLevel, UUID senderUniqueId, GameProfile senderProfile) {
        this.lastModifiedTimestamp = -1L;
        this.metadata = Metadata.create();
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.originalComponent = originalComponent;
        this.component = component;
        this.visibility = visibility;
        this.chatTrustLevel = chatTrustLevel;
        this.senderUniqueId = senderUniqueId;
        this.senderProfile = senderProfile;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public ChatVisibility visibility() {
        return this.visibility;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public ChatTrustLevel trustLevel() {
        return this.chatTrustLevel;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public Component originalComponent() {
        return this.originalComponent;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public String getOriginalPlainText() {
        if (this.originalPlainText == null) {
            this.originalPlainText = loadPlainText(this.originalComponent, false);
        }
        return this.originalPlainText;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public String getOriginalFormattedText() {
        if (this.originalFormattedText == null) {
            this.originalFormattedText = loadFormattedText(this.originalComponent);
        }
        return this.originalFormattedText;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public Component component() {
        return this.component;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public String getPlainText() {
        if (this.plainText == null) {
            this.plainText = loadPlainText(this.component, true);
        }
        return this.plainText;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public boolean containsIcon() {
        if (this.containsIcon == null) {
            this.plainText = loadPlainText(this.component, true);
        }
        return this.containsIcon.booleanValue();
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public String getFormattedText() {
        if (this.formattedText == null) {
            this.formattedText = loadFormattedText(this.component);
        }
        return this.formattedText;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @Nullable
    public String getSerializedText() {
        if (this.serializedText == null && !this.triedSerialization) {
            this.triedSerialization = true;
            try {
                this.serializedText = GsonComponentSerializer.gson().serialize(this.component);
            } catch (Exception e) {
                LOGGER.warn("An error occurred while serializing the chat message. {}: {}", e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return this.serializedText;
    }

    public UUID getCachedSender() {
        return this.senderUniqueId;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @Nullable
    public GameProfile getSenderProfile() {
        return this.senderProfile;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @Nullable
    public UUID getSenderUniqueId() {
        return this.senderUniqueId;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public void edit(@NotNull Component component) {
        if (!this.messageSent) {
            this.component = component;
            reset();
            return;
        }
        ThreadSafe.ensureRenderThread();
        Component prevComponent = this.component;
        if (((ChatMessageUpdateEvent) Laby.fireEvent(new ChatMessageUpdateEvent(Phase.PRE, this, prevComponent, component))).isCancelled()) {
            return;
        }
        this.component = component;
        reset();
        this.containsIcon = null;
        this.lastModifiedTimestamp = TimeUtil.getMillis();
        CHAT_MODIFIER.editMessage(this, component);
        Laby.fireEvent(new ChatMessageUpdateEvent(Phase.POST, this, prevComponent, component));
    }

    private void reset() {
        this.formattedText = null;
        this.plainText = null;
        this.serializedText = null;
        this.triedSerialization = false;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public void delete() {
        if (!this.messageSent) {
            throw new IllegalStateException("Cannot delete a message that has not been sent yet. In the ChatReceiveEvent, use event.setCancelled(true) instead");
        }
        if (wasDeleted()) {
            throw new IllegalStateException("This message has already been deleted");
        }
        ThreadSafe.ensureRenderThread();
        Component prevComponent = this.component;
        if (((ChatMessageUpdateEvent) Laby.fireEvent(new ChatMessageUpdateEvent(Phase.PRE, this, prevComponent, null))).isCancelled()) {
            return;
        }
        CHAT_MODIFIER.deleteMessage(this);
        CONTROLLER.getMessages().remove(this);
        Laby.fireEvent(new ChatMessageUpdateEvent(Phase.POST, this, prevComponent, null));
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public long timestamp() {
        return this.timestamp;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public long lastModifiedTimestamp() {
        return this.lastModifiedTimestamp;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    @NotNull
    public UUID messageId() {
        return this.messageId;
    }

    @Override // net.labymod.api.client.chat.ChatMessage
    public boolean wasDeleted() {
        return CollectionHelper.noneMatch(CONTROLLER.getMessages(), m -> {
            return m.messageId().equals(this.messageId);
        });
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setMessageSent(boolean messageSent) {
        this.messageSent = messageSent;
    }

    public DefaultChatMessage copy() {
        DefaultChatMessage defaultChatMessage = new DefaultChatMessage(this.messageId, this.timestamp, this.originalComponent, this.component, this.visibility, this.chatTrustLevel, this.senderUniqueId, this.senderProfile);
        defaultChatMessage.originalFormattedText = this.originalFormattedText;
        defaultChatMessage.originalPlainText = this.originalPlainText;
        defaultChatMessage.formattedText = this.formattedText;
        defaultChatMessage.plainText = this.plainText;
        return defaultChatMessage;
    }

    private String loadPlainText(Component component, boolean loadIcon) {
        ComponentFlattener flattener = Laby.references().componentRenderer().getColorStrippingFlattener();
        StringBuilder builder = new StringBuilder();
        Consumer<Component> consumer = c -> {
            if (loadIcon && (c instanceof IconComponent)) {
                this.containsIcon = true;
            }
        };
        Objects.requireNonNull(builder);
        flattener.flatten(component, consumer, builder::append);
        if (loadIcon && this.containsIcon == null) {
            this.containsIcon = false;
        }
        return builder.toString();
    }

    private String loadFormattedText(Component component) {
        return COMPONENT_RENDERER.legacySectionSerializer().serialize(component);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatMessage$Builder.class */
    @Implements(ChatMessage.Builder.class)
    public static class Builder extends ChatMessage.Builder {
        @Override // net.labymod.api.client.chat.ChatMessage.Builder
        public ChatMessage build() {
            Objects.requireNonNull(this.component, "Component cannot be null");
            Objects.requireNonNull(this.visibility, "ChatVisibility cannot be null");
            if (this.timestamp == -1) {
                this.timestamp = TimeUtil.getMillis();
            }
            if (this.trustLevel == null) {
                this.trustLevel = this.visibility == ChatVisibility.COMMANDS_ONLY ? ChatTrustLevel.SYSTEM : ChatTrustLevel.SECURE;
            }
            return new DefaultChatMessage(UUID.randomUUID(), this.timestamp, this.component, this.component, this.visibility, this.trustLevel, this.sender);
        }
    }
}

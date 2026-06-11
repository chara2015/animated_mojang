package net.labymod.api.client.chat;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatMessage.class */
public interface ChatMessage extends MetadataExtension {
    @NotNull
    ChatVisibility visibility();

    @NotNull
    ChatTrustLevel trustLevel();

    @NotNull
    Component originalComponent();

    @NotNull
    String getOriginalPlainText();

    @NotNull
    String getOriginalFormattedText();

    @NotNull
    Component component();

    @NotNull
    String getPlainText();

    boolean containsIcon();

    @NotNull
    String getFormattedText();

    @Nullable
    String getSerializedText();

    @Nullable
    GameProfile getSenderProfile();

    @Nullable
    UUID getSenderUniqueId();

    void edit(@NotNull Component component);

    void delete();

    long timestamp();

    long lastModifiedTimestamp();

    @NotNull
    UUID messageId();

    boolean wasDeleted();

    static Builder builder() {
        return Laby.references().chatMessageBuilder();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatMessage$Builder.class */
    @Referenceable
    public static abstract class Builder {
        protected Component component;
        protected ChatVisibility visibility;
        protected ChatTrustLevel trustLevel;
        protected UUID sender;
        protected long timestamp = -1;

        public abstract ChatMessage build();

        protected Builder() {
        }

        public Builder component(Component component) {
            this.component = component;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder visibility(ChatVisibility visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder trustLevel(ChatTrustLevel trustLevel) {
            this.trustLevel = trustLevel;
            return this;
        }

        public Builder sender(UUID sender) {
            this.sender = sender;
            return this;
        }
    }
}

package net.labymod.api.configuration.labymod.chat;

import java.util.Objects;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/ChatTab.class */
public abstract class ChatTab implements MetadataExtension {
    private final RootChatTabConfig rootConfig;
    private final ChatWindow chatWindow;
    private Metadata metadata;
    protected int unread;

    public abstract void copy(@NotNull ChatTab chatTab);

    @NotNull
    public abstract Widget createContentWidget(WindowAccessor windowAccessor);

    protected ChatTab(@NotNull ChatWindow chatWindow, @NotNull RootChatTabConfig rootConfig) {
        Objects.requireNonNull(chatWindow, "Chat window cannot be null!");
        Objects.requireNonNull(rootConfig, "Root config cannot be null!");
        this.chatWindow = chatWindow;
        this.rootConfig = rootConfig;
    }

    @NotNull
    public final RootChatTabConfig rootConfig() {
        return this.rootConfig;
    }

    @NotNull
    public final ChatWindow window() {
        return this.chatWindow;
    }

    @NotNull
    public final GeneralChatTabConfig config() {
        return rootConfig().config();
    }

    @NotNull
    @ApiStatus.NonExtendable
    public String getName() {
        String name = config().name().get();
        if (name == null || name.trim().isEmpty()) {
            return getCustomName();
        }
        return name.trim();
    }

    @NotNull
    public String getCustomName() {
        return I18n.translate("labymod.activity.chat.context.newTab", new Object[0]);
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        throw new UnsupportedOperationException("Cannot overwrite Metadata of ChatTabs");
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        if (this.metadata == null) {
            this.metadata = Metadata.create();
        }
        return this.metadata;
    }

    public int getUnread() {
        return this.unread;
    }

    public void resetUnread() {
        this.unread = 0;
    }

    public void invalidateCache() {
    }
}

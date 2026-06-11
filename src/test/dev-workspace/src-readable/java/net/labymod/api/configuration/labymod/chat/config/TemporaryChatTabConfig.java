package net.labymod.api.configuration.labymod.chat.config;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/config/TemporaryChatTabConfig.class */
public class TemporaryChatTabConfig extends RootChatTabConfig implements MetadataExtension {
    private transient Metadata metadata;
    private Icon icon;

    public TemporaryChatTabConfig(int index, RootChatTabConfig.Type type, GeneralChatTabConfig config) {
        super(index, type, config);
        this.metadata = Metadata.create();
    }

    public Icon icon() {
        return this.icon;
    }

    public void icon(Icon icon) {
        this.icon = icon;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }
}

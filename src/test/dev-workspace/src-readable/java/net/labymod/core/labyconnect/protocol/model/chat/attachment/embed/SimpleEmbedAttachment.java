package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed;

import java.net.URI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/SimpleEmbedAttachment.class */
public final class SimpleEmbedAttachment extends AbstractURIAttachment {
    private final Icon icon;
    private final Component title;
    private final Component description;

    private SimpleEmbedAttachment(Icon icon, Component title, Component description, String url) {
        super(URI.create(url));
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Icon getIcon() {
        return this.icon;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getTitle() {
        return this.title;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getDescription() {
        return this.description;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public boolean shouldHideURI() {
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/SimpleEmbedAttachment$Builder.class */
    public static final class Builder {
        private Icon icon;
        private Component title;
        private Component description;
        private String url;

        public Builder icon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public SimpleEmbedAttachment build() {
            return new SimpleEmbedAttachment(this.icon, this.title, this.description, this.url);
        }
    }
}

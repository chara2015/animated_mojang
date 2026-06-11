package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.link;

import java.net.URI;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/link/LinkAttachment.class */
public class LinkAttachment extends AbstractURIAttachment {
    private Icon icon;
    private Component title;
    private Component description;

    public LinkAttachment(URI uri) {
        super(uri);
        this.icon = Textures.SpriteCommon.QUESTION_MARK;
        this.title = Component.text(uri.toString());
        this.description = Component.text("...");
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
        return false;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public void setDescription(Component description) {
        this.description = description;
    }
}

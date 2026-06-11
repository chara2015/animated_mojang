package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file;

import java.net.URI;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/file/FileAttachment.class */
public class FileAttachment extends AbstractURIAttachment {
    protected final String type;
    protected final UUID identifier;

    public FileAttachment(URI uri, String type, UUID identifier) {
        super(uri);
        this.type = type;
        this.identifier = identifier;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Icon getIcon() {
        return Textures.SpriteCommon.QUESTION_MARK;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getTitle() {
        byte[] fileData = getFileData();
        if (fileData == null) {
            return Component.text("Receiving...");
        }
        return Component.text(fileData.length + " bytes");
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public boolean isClickable() {
        return false;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getDescription() {
        return Component.text(this.type);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public void open() {
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public boolean shouldHideURI() {
        return true;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    @NotNull
    public Component toComponent() {
        return getTitle();
    }

    public byte[] getFileData() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return null;
        }
        return session.getFile(this.identifier);
    }
}

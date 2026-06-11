package net.labymod.core.uri.loader;

import java.net.URI;
import java.util.UUID;
import net.labymod.api.uri.loader.AttachmentLoader;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.FileAttachment;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.file.ImageAttachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/uri/loader/FileAttachmentLoader.class */
public class FileAttachmentLoader implements AttachmentLoader<FileAttachment> {
    @Override // net.labymod.api.uri.loader.AttachmentLoader
    public FileAttachment create(URI uri) {
        String authority;
        try {
            String query = uri.getQuery();
            if (query == null || (authority = uri.getAuthority()) == null) {
                return null;
            }
            String type = query.replace("type=", "");
            UUID identifier = UUID.fromString(authority);
            if (type.equals("png") || type.equals("jpg")) {
                return new ImageAttachment(uri, type, identifier);
            }
            return new FileAttachment(uri, type, identifier);
        } catch (Throwable th) {
            return null;
        }
    }
}

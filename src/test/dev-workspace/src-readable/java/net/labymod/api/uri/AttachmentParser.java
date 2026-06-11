package net.labymod.api.uri;

import java.net.URI;
import java.util.List;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.uri.loader.AttachmentLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/uri/AttachmentParser.class */
@Referenceable
public interface AttachmentParser {
    List<URIAttachment> parse(String str);

    AttachmentLoader<?> getLoader(URI uri);

    void registerLoader(String str, AttachmentLoader<?> attachmentLoader);
}

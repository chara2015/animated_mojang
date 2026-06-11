package net.labymod.api.uri.loader;

import java.net.URI;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/uri/loader/AttachmentLoader.class */
public interface AttachmentLoader<T extends URIAttachment> {
    @Nullable
    T create(URI uri);
}

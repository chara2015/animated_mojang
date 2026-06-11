package net.labymod.api.labyconnect.protocol.model.chat.attachment;

import java.net.URI;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/chat/attachment/URIAttachment.class */
public interface URIAttachment extends Attachment {
    URI getURI();

    void open();

    boolean shouldHideURI();

    default String getUrl() {
        return getURI().toString();
    }
}

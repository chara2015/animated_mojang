package net.labymod.core.labyconnect.protocol.model.chat.attachment;

import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/AbstractAttachment.class */
public abstract class AbstractAttachment implements Attachment {
    private int version = 0;

    @Override // net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    public void update() {
        this.version++;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    public int getVersion() {
        return this.version;
    }
}

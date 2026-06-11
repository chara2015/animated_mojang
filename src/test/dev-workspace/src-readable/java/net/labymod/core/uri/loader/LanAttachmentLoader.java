package net.labymod.core.uri.loader;

import java.net.URI;
import java.util.UUID;
import net.labymod.api.uri.loader.AttachmentLoader;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan.LanAttachment;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/uri/loader/LanAttachmentLoader.class */
public class LanAttachmentLoader implements AttachmentLoader<LanAttachment> {
    @Override // net.labymod.api.uri.loader.AttachmentLoader
    public LanAttachment create(URI uri) {
        try {
            String authority = uri.getAuthority();
            if (authority == null) {
                return null;
            }
            UUID sender = UUID.fromString(authority);
            SharedLanWorldInvite invite = LabyMod.references().sharedLanWorldService().getInviteOfSender(sender);
            if (invite == null) {
                return LanAttachment.createExpired(uri);
            }
            return LanAttachment.createOf(uri, invite);
        } catch (Throwable th) {
            return null;
        }
    }
}

package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan;

import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.util.TextFormat;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/lan/LanAttachment.class */
public class LanAttachment extends AbstractURIAttachment {

    @Nullable
    private final SharedLanWorldInvite invite;

    private LanAttachment(URI uri, @NotNull SharedLanWorldInvite invite) {
        super(uri);
        this.invite = invite;
    }

    private LanAttachment(URI uri) {
        super(uri);
        this.invite = null;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Icon getIcon() {
        if (this.invite == null || this.invite.options().getIcon() == null) {
            return Textures.SpriteCommon.QUESTION_MARK;
        }
        return Icon.url(this.invite.options().getIcon());
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getTitle() {
        if (this.invite == null) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.title", new Component[0]);
        }
        return Component.text(this.invite.options().getWorldName());
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    @Nullable
    public Component getButtonComponent() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || this.invite.status() != SharedLanWorldInvite.InviteStatus.PENDING || session.self().getUniqueId().equals(this.invite.getSender())) {
            return null;
        }
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.action.join", new Component[0]);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public boolean isClickable() {
        return false;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getDescription() {
        Component connectError;
        if (this.invite != null && this.invite.status() == SharedLanWorldInvite.InviteStatus.ACCEPTED && (connectError = this.invite.getConnectError()) != null) {
            return connectError;
        }
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || this.invite.status() != SharedLanWorldInvite.InviteStatus.PENDING) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description." + TextFormat.SNAKE_CASE.toLowerCamelCase((this.invite != null ? this.invite.status() : SharedLanWorldInvite.InviteStatus.EXPIRED).name()), new Component[0]);
        }
        if (session.self().getUniqueId().equals(this.invite.getSender())) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.sent", Component.text(this.invite.options().getWorldName()));
        }
        Friend sender = session.getFriend(this.invite.getSender());
        String senderName = sender == null ? "Unknown" : sender.getName();
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.pending", Component.text(senderName));
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public void open() {
        Friend sender;
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (this.invite == null || session == null || session.self().getUniqueId().equals(this.invite.getSender()) || (sender = session.getFriend(this.invite.getSender())) == null) {
            return;
        }
        session.acceptLanWorldInvite(sender);
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

    public static LanAttachment createExpired(URI uri) {
        return new LanAttachment(uri);
    }

    public static LanAttachment createOf(URI uri, SharedLanWorldInvite invite) {
        LanAttachment attachment = new LanAttachment(uri, invite);
        invite.setAttachment(attachment);
        return attachment;
    }
}

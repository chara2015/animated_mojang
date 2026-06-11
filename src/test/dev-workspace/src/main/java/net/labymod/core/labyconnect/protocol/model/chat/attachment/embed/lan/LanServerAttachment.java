package net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan;

import java.net.URI;
import java.util.Base64;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.worldsharing.model.InviteProperties;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/attachment/embed/lan/LanServerAttachment.class */
public class LanServerAttachment extends AbstractURIAttachment {
    private static final String I18N_PREFIX = "labymod.activity.labyconnect.chat.lanworld.";

    @Nullable
    private final Friend friend;
    private final JoinWorldEvent.Type type;
    private boolean accepted;
    private final long expiry;
    private boolean expired;
    private final InviteProperties properties;
    private Icon icon;

    protected LanServerAttachment(URI uri, InviteProperties properties, @Nullable Friend friend, @NotNull JoinWorldEvent.Type type) {
        super(uri);
        this.friend = friend;
        this.expiry = System.currentTimeMillis() + 60000;
        this.properties = properties;
        this.type = type;
        String iconIdentifier = this.properties.get("icon");
        if (iconIdentifier == null) {
            if (this.icon == null) {
                this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
                return;
            }
            return;
        }
        try {
            UUID iconId = UUID.fromString(iconIdentifier);
            LabyConnectSession session = Laby.references().labyConnect().getSession();
            if (session == null) {
                if (this.icon == null) {
                    this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
                    return;
                }
                return;
            }
            byte[] iconData = session.getFile(iconId);
            if (iconData == null) {
                if (this.icon == null) {
                    this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
                }
            } else {
                this.icon = Icon.url("data:image/png;base64," + Base64.getEncoder().encodeToString(iconData));
                if (this.icon == null) {
                    this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
                }
            }
        } catch (IllegalArgumentException e) {
            if (this.icon == null) {
                this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
            }
        } catch (Throwable th) {
            if (this.icon == null) {
                this.icon = Icon.texture(Textures.DEFAULT_SERVER_ICON);
            }
            throw th;
        }
    }

    private boolean isExpired() {
        if (this.accepted) {
            return false;
        }
        if (this.expired) {
            return true;
        }
        this.expired = System.currentTimeMillis() > this.expiry;
        update();
        return this.expired;
    }

    private boolean isSender() {
        return this.friend == null;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public boolean shouldHideURI() {
        return true;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment
    public void open() {
        if (isSender() || this.accepted || isExpired()) {
            return;
        }
        this.accepted = true;
        LabyMod.references().worldsharing().netEventHandler().joinServer(this.friend, this.type);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    @NotNull
    public Component toComponent() {
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.title", new Component[0]);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment, net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment
    public float getHeight() {
        return 30.0f;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Icon getIcon() {
        return this.icon;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getTitle() {
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.title", new Component[0]);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public Component getDescription() throws MatchException {
        if (isExpired()) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.expired", new Component[0]);
        }
        if (isSender()) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.sent", new Component[0]);
        }
        if (this.accepted) {
            return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.accepted", new Component[0]);
        }
        switch (this.type) {
            case WORLD:
                String name = this.properties.getWorldName();
                Component gameMode = Component.translatable("labymod.misc.gameMode." + this.properties.getGameMode(), new Component[0]);
                Component result = Component.empty();
                if (!name.isBlank()) {
                    result.append(Component.text(name + ", "));
                }
                result.append(gameMode).append(Component.text(", ")).append(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite." + (this.properties.isCheatsEnabled() ? "cheats" : "no_cheats"), new Component[0])).append(Component.text(", ")).append(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite.version", Component.text(this.properties.getVersion())));
                return result;
            case LOCAL_SERVER:
                return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.description.pending", Component.text(this.friend.getName()));
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    @Nullable
    public Component getButtonComponent() {
        if (isSender() || this.accepted || isExpired()) {
            return null;
        }
        return Component.translatable("labymod.activity.labyconnect.chat.lanworld.inviteReceived.action.join", new Component[0]);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.AbstractURIAttachment
    public boolean isClickable() {
        return false;
    }

    public static LanServerAttachment createOf(URI uri) {
        try {
            String authority = uri.getAuthority();
            if (authority == null) {
                return null;
            }
            UUID sender = UUID.fromString(authority);
            Friend friend = null;
            if (!sender.equals(Laby.labyAPI().getUniqueId())) {
                LabyConnectSession session = Laby.references().labyConnect().getSession();
                if (session == null) {
                    return null;
                }
                friend = session.getFriend(sender);
                if (friend == null) {
                    return null;
                }
            }
            InviteProperties properties = InviteProperties.fromQuery(uri.getQuery());
            JoinWorldEvent.Type type = properties.getType();
            if (type == null) {
                throw new IllegalStateException("Unknown lan type: " + properties.get(ParameterType.TYPE));
            }
            return new LanServerAttachment(uri, properties, friend, type);
        } catch (Exception e) {
            return null;
        }
    }
}

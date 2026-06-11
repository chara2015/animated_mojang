package net.labymod.core.labyconnect.lanworld;

import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.lan.LanAttachment;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/SharedLanWorldInvite.class */
public class SharedLanWorldInvite {
    private final UUID sender;
    private final UUID receiver;
    private final PacketPlayInviteLanWorld.LanWorldOptions options;
    private final long timestamp;
    private InviteStatus status = InviteStatus.PENDING;
    private Component connectError = null;

    @Nullable
    private LanAttachment attachment;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/SharedLanWorldInvite$InviteStatus.class */
    public enum InviteStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        RETRACTED,
        EXPIRED,
        DISCONNECTED,
        INCOMPATIBLE_MINECRAFT_VERSION,
        ERROR,
        INVALID
    }

    public SharedLanWorldInvite(UUID sender, UUID receiver, PacketPlayInviteLanWorld.LanWorldOptions options, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.options = options;
        this.timestamp = timestamp;
    }

    public UUID getSender() {
        return this.sender;
    }

    public UUID getReceiver() {
        return this.receiver;
    }

    public PacketPlayInviteLanWorld.LanWorldOptions options() {
        return this.options;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Icon icon() {
        if (this.options.getIcon() != null) {
            return Icon.url(this.options.getIcon());
        }
        return Icon.head(this.sender);
    }

    public InviteStatus status() {
        return this.status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
        if (this.attachment != null) {
            this.attachment.update();
        }
    }

    public void setConnectError(Component connectError) {
        this.connectError = connectError;
        if (this.attachment != null) {
            this.attachment.update();
        }
    }

    public Component getConnectError() {
        return this.connectError;
    }

    public void setAttachment(@Nullable LanAttachment attachment) {
        this.attachment = attachment;
    }

    @Nullable
    public LanAttachment getAttachment() {
        return this.attachment;
    }

    public String toString() {
        return "lan://" + this.sender.toString();
    }
}

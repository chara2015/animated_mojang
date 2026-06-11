package net.labymod.core.labyconnect.protocol.model.friend;

import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendNoteUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendPinUpdateEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultChat;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import net.labymod.core.labyconnect.util.LoremIpsum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/friend/DefaultFriend.class */
public class DefaultFriend extends DefaultUser implements Friend {
    private String statusMessage;
    private DefaultServerInfo server;
    private long lastServerChange;
    private int unreadMessages;
    private final long lastInteraction;
    private final long lastTyping;
    private final String timeZone;
    private final long firstJoined;
    private final int contactAmount;
    private long lastOnline;
    private final boolean party;
    private boolean isHosting;
    private final DefaultChat chat;

    public DefaultFriend(UUID uniqueId, String name, UserStatus status, String statusMessage, DefaultServerInfo server, int unreadMessages, long lastInteraction, long lastTyping, String timeZone, long lastOnline, long firstJoined, int contactAmount, boolean party) {
        super(uniqueId, name);
        this.unreadMessages = 0;
        updateStatus(status);
        this.statusMessage = statusMessage;
        this.server = server;
        this.unreadMessages = unreadMessages;
        this.lastInteraction = lastInteraction;
        this.lastTyping = lastTyping;
        this.timeZone = timeZone;
        this.lastOnline = lastOnline;
        this.firstJoined = firstJoined;
        this.contactAmount = contactAmount;
        this.party = party;
        this.chat = new DefaultChat(this.uniqueId);
        this.chat.addParticipant(this);
        if (Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() && uniqueId.equals(UUID.fromString("3a440181-e057-46ae-ad79-79873f03ddbe"))) {
            LoremIpsum.addMessages(this.chat);
        }
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    @Nullable
    public ServerInfo getServer() {
        if (this.server == null || this.server.getAddress().isEmpty()) {
            return null;
        }
        return this.server;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    @NotNull
    public Chat chat() {
        return this.chat;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public void remove() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            session.removeFriend(this.uniqueId);
        }
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public boolean isPinned() {
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) Laby.labyAPI().labyConnect().configProvider().get();
        return (config == null || config.pinnedFriends() == null || !config.pinnedFriends().get().contains(this.uniqueId)) ? false : true;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public void pin() {
        setPin(true);
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public void unpin() {
        setPin(false);
    }

    private void setPin(boolean pin) {
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) labyConnect.configProvider().get();
        Set<UUID> uuids = config.pinnedFriends().get();
        if (pin) {
            uuids.add(this.uniqueId);
        } else {
            uuids.remove(this.uniqueId);
        }
        config.pinnedFriends().set(uuids);
        Laby.labyAPI().labyConnect().configProvider().save();
        Laby.fireEvent(new LabyConnectFriendPinUpdateEvent(labyConnect, this));
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public String getNote() {
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) Laby.labyAPI().labyConnect().configProvider().get();
        return config.friendNotes().get().getOrDefault(this.uniqueId, null);
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public void openNoteEditor() {
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) Laby.labyAPI().labyConnect().configProvider().get();
        TextFieldWidget textFieldWidget = new TextFieldWidget();
        textFieldWidget.setText(config.friendNotes().get().getOrDefault(this.uniqueId, ""));
        textFieldWidget.maximalLength(64);
        PopupWidget.builder().title(Component.translatable("labymod.activity.labyconnect.chat.action.note", new Component[0])).widgetSupplier(() -> {
            textFieldWidget.setFocused(true);
            textFieldWidget.setCursorAtEnd();
            return textFieldWidget;
        }).confirmComponent(Component.translatable("labymod.ui.button.save", new Component[0])).confirmCallback(() -> {
            if (textFieldWidget.getText().isEmpty()) {
                config.friendNotes().get().remove(this.uniqueId);
            } else {
                config.friendNotes().get().put(this.uniqueId, textFieldWidget.getText());
            }
            Laby.labyAPI().labyConnect().configProvider().save();
            Laby.fireEvent(new LabyConnectFriendNoteUpdateEvent(Laby.labyAPI().labyConnect(), this));
        }).build().displayInOverlay();
    }

    public void increaseUnreadMessages() {
        this.unreadMessages++;
    }

    public void setUnreadMessages(int amount) {
        boolean z = this.unreadMessages != amount;
        this.unreadMessages = amount;
    }

    public int getUnreadMessages() {
        return this.unreadMessages;
    }

    public long getLastInteraction() {
        return this.lastInteraction;
    }

    public long getLastTyping() {
        return this.lastTyping;
    }

    public boolean isParty() {
        return this.party;
    }

    public boolean isFriendRequest() {
        return this instanceof DefaultIncomingFriendRequest;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public boolean isOnline() {
        return userStatus() != UserStatus.OFFLINE;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public long getLastOnline() {
        return this.lastOnline;
    }

    public long getFirstJoined() {
        return this.firstJoined;
    }

    public int getContactAmount() {
        return this.contactAmount;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override // net.labymod.core.labyconnect.protocol.model.DefaultUser, net.labymod.api.labyconnect.protocol.model.User
    public void updateStatus(@NotNull UserStatus userStatus) {
        super.updateStatus(userStatus);
        this.lastOnline = TimeUtil.getCurrentTimeMillis();
    }

    public boolean equals(DefaultFriend friend) {
        return (this.party && friend.party) || !(friend.party || this.party || !friend.getUniqueId().equals(getUniqueId()));
    }

    public void setServer(DefaultServerInfo server) {
        if ((this.server == null && server != null) || (this.server != null && !this.server.isSameServer(server))) {
            this.lastServerChange = TimeUtil.getCurrentTimeMillis();
        }
        this.server = server;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public long getLastServerChange() {
        return this.lastServerChange;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public boolean isHostingWorld() {
        return this.isHosting;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.Friend
    public void setHostingWorld(boolean isHosting) {
        this.isHosting = isHosting;
    }
}

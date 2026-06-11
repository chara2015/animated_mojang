package net.labymod.core.labyconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.main.notification.DefaultNotificationController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/LabyConnectNotifications.class */
public class LabyConnectNotifications {
    private final LabyConnect labyConnect;
    private final DefaultNotificationController notifications = (DefaultNotificationController) Laby.labyAPI().notificationController();
    private final DefaultLabyNetController labyNetController = (DefaultLabyNetController) Laby.references().labyNetController();
    private final Map<UUID, List<String>> previousGameModes = new HashMap();
    private final Set<UUID> announcedFriendRequests = new HashSet();

    public LabyConnectNotifications(LabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }

    @Subscribe
    public void onLabyConnectDisconnect(LabyConnectDisconnectEvent event) {
        this.previousGameModes.clear();
        if (event.getInitiator() == LabyConnectDisconnectEvent.Initiator.USER) {
            return;
        }
        pushSystemMessage(Component.translatable("labymod.activity.labyconnect.notifications.disconnect", new Component[0]).args(Component.text(event.getReason())));
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulk(LabyConnectIncomingFriendRequestAddBulkEvent event) {
        if (!((LabyConnectConfigAccessor) this.labyConnect.configProvider().get()).incomingRequestNotifications().get().booleanValue()) {
            return;
        }
        pushSystemMessage(Component.translatable("labymod.activity.labyconnect.notifications.requests", new Component[0]).args(Component.text(Integer.valueOf(event.getRequests().size()))));
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(LabyConnectIncomingFriendRequestAddEvent event) {
        if (!((LabyConnectConfigAccessor) this.labyConnect.configProvider().get()).incomingRequestNotifications().get().booleanValue() || !this.announcedFriendRequests.add(event.request().getUniqueId())) {
            return;
        }
        pushRequestMessage(event.request(), Component.translatable("labymod.activity.labyconnect.notifications.request", new Component[0]));
    }

    @Subscribe
    public void onLabyConnectChatMessage(LabyConnectChatMessageEvent event) {
        ChatMessage chatMessageMessage = event.message();
        if (!(chatMessageMessage instanceof TextChatMessage)) {
            return;
        }
        TextChatMessage message = (TextChatMessage) chatMessageMessage;
        if (message.sender().isSelf() || !((LabyConnectConfigAccessor) this.labyConnect.configProvider().get()).incomingChatMessageNotifications().get().booleanValue()) {
            return;
        }
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_CHAT_MESSAGE, 1.0f, 1.5f);
        Component text = message.toComponent();
        if (text == null) {
            text = Component.empty();
        }
        pushChatMessage(event.chat(), text);
    }

    @Subscribe
    public void onLabyConnectFriendStatus(LabyConnectFriendStatusEvent event) {
        String str;
        TextColor textColor;
        boolean online = event.isOnline();
        if (event.wasOnline() == online) {
            return;
        }
        this.previousGameModes.remove(event.friend().getUniqueId());
        if (!((LabyConnectConfigAccessor) this.labyConnect.configProvider().get()).onlineStatusNotification().get().booleanValue()) {
            return;
        }
        Friend friend = event.friend();
        if (online) {
            str = "labymod.activity.labyconnect.notifications.online";
        } else {
            str = "labymod.activity.labyconnect.notifications.offline";
        }
        TranslatableComponent translatableComponentTranslatable = Component.translatable(str, new Component[0]);
        if (online) {
            textColor = NamedTextColor.GREEN;
        } else {
            textColor = NamedTextColor.RED;
        }
        pushUserMessage(friend, translatableComponentTranslatable.color(textColor));
    }

    @Subscribe
    public void onLabyConnectFriendServer(LabyConnectFriendServerEvent event) {
        Component text;
        ServerInfo serverInfo = event.serverInfo();
        if (serverInfo == null) {
            this.previousGameModes.remove(event.friend().getUniqueId());
            return;
        }
        boolean sameServer = serverInfo.isSameServer(event.getPreviousServer());
        LabyConnectConfigAccessor labyConnectConfig = (LabyConnectConfigAccessor) this.labyConnect.configProvider().get();
        if (!labyConnectConfig.serverSwitchNotifications().get().booleanValue()) {
            return;
        }
        String gameModeName = serverInfo.getGameModeName();
        if (sameServer) {
            if (!labyConnectConfig.serverSwitchGameModeNotifications().get().booleanValue()) {
                return;
            }
            List<String> lastGameModes = this.previousGameModes.computeIfAbsent(event.friend().getUniqueId(), uniqueId -> {
                return new ArrayList(2);
            });
            String actualGameModeName = gameModeName == null ? "" : gameModeName;
            if (lastGameModes.contains(actualGameModeName)) {
                return;
            }
            if (lastGameModes.size() > 1) {
                lastGameModes.remove(0);
            }
            lastGameModes.add(actualGameModeName);
        } else {
            this.previousGameModes.remove(event.friend().getUniqueId());
        }
        if (gameModeName == null) {
            text = Component.translatable("labymod.activity.labyconnect.notifications.server", NamedTextColor.GRAY, Component.text(serverInfo.getDisplayInfo(), NamedTextColor.WHITE));
        } else if (TimeUtil.getCurrentTimeMillis() - event.friend().getLastServerChange() < 1000) {
            text = Component.translatable("labymod.activity.labyconnect.notifications.gameModeAndServer", NamedTextColor.GRAY, Component.text(gameModeName, NamedTextColor.WHITE), Component.text(serverInfo.getDisplayInfo(), NamedTextColor.WHITE));
        } else {
            text = Component.translatable("labymod.activity.labyconnect.notifications.gameMode", NamedTextColor.GRAY, Component.text(gameModeName, NamedTextColor.WHITE));
        }
        Notification.Builder builder = Notification.builder().title(Component.text(event.friend().getName())).icon(Icon.head(event.friend().getUniqueId())).text(text).type(Notification.Type.SYSTEM);
        Optional<ServerGroup> serverByIp = this.labyNetController.getServerByIp(serverInfo.getAddress());
        if (serverByIp.isPresent()) {
            ServerGroup serverGroup = serverByIp.get();
            Optional<ServerGroup.Attachment> icon = serverGroup.getAttachment("icon");
            icon.ifPresent(attachment -> {
                builder.secondaryIcon(attachment.getIcon());
            });
        }
        Debounce.of("labyconnect-server-switch-" + String.valueOf(event.friend().getUniqueId()), 1000L, () -> {
            this.notifications.push(builder.build());
        });
    }

    private void pushRequestMessage(IncomingFriendRequest chat, Component component) {
        this.notifications.push(Notification.builder().title(Component.text(chat.getName())).icon(Icon.head(chat.getUniqueId())).text(component).type(Notification.Type.SOCIAL).build());
    }

    private void pushChatMessage(Chat chat, Component component) {
        this.notifications.push(Notification.builder().title(chat.title()).icon(chat.icon()).secondaryIcon(Textures.SpriteCommon.CHAT_BUBBLE).text(component).type(Notification.Type.SOCIAL).onClick(notification -> {
            chat.openChat();
        }).build());
    }

    private void pushUserMessage(User user, Component component) {
        this.notifications.push(Notification.builder().title(Component.text(user.getName())).icon(Icon.head(user.getUniqueId())).secondaryIcon(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP).text(component).type(Notification.Type.SOCIAL).build());
    }

    private void pushSystemMessage(Component component) {
        this.notifications.push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.notifications.title", new Component[0])).text(component).icon(Textures.SpriteCommon.CHAT_BUBBLE).type(Notification.Type.SYSTEM).build());
    }
}

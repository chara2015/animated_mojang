package net.labymod.api.labyconnect.configuration;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.labyconnect.protocol.model.UserStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/configuration/LabyConnectConfigAccessor.class */
public interface LabyConnectConfigAccessor extends ConfigAccessor {
    ConfigProperty<UserStatus> onlineStatus();

    ConfigProperty<Boolean> showConnectedServer();

    ConfigProperty<Boolean> onlineStatusNotification();

    ConfigProperty<Boolean> serverSwitchNotifications();

    ConfigProperty<Boolean> serverSwitchGameModeNotifications();

    ConfigProperty<Boolean> incomingRequestNotifications();

    ConfigProperty<Boolean> incomingChatMessageNotifications();

    ConfigProperty<Set<UUID>> pinnedFriends();

    ConfigProperty<Map<UUID, String>> friendNotes();
}

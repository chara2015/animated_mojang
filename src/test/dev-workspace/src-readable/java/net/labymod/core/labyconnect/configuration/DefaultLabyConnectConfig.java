package net.labymod.core.labyconnect.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.UserStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/configuration/DefaultLabyConnectConfig.class */
@ConfigName("labyconnect")
public class DefaultLabyConnectConfig extends Config implements LabyConnectConfigAccessor {

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<UserStatus> onlineStatus = ConfigProperty.createEnum(UserStatus.ONLINE);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showConnectedServer = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @SettingSection("notifications")
    private final ConfigProperty<Boolean> onlineStatusNotifications = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverSwitchNotifications = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverSwitchGameModeNotifications = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> incomingRequestNotifications = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> incomingChatMessageNotifications = new ConfigProperty<>(true);
    private final ConfigProperty<Set<UUID>> pinnedFriends = new ConfigProperty<>(new HashSet());
    private final ConfigProperty<Map<UUID, String>> friendNotes = new ConfigProperty<>(new HashMap());

    public DefaultLabyConnectConfig() {
        this.onlineStatus.addChangeListener((type, oldValue, newValue) -> {
            LabyConnect labyConnect = Laby.labyAPI().labyConnect();
            LabyConnectSession session = labyConnect == null ? null : labyConnect.getSession();
            if (session != null) {
                session.sendSettings();
            }
        });
        this.showConnectedServer.addChangeListener((type2, oldValue2, newValue2) -> {
            LabyConnect labyConnect = Laby.labyAPI().labyConnect();
            LabyConnectSession session = labyConnect == null ? null : labyConnect.getSession();
            if (session != null) {
                session.sendSettings();
            }
        });
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<UserStatus> onlineStatus() {
        return this.onlineStatus;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> showConnectedServer() {
        return this.showConnectedServer;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> onlineStatusNotification() {
        return this.onlineStatusNotifications;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> serverSwitchNotifications() {
        return this.serverSwitchNotifications;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> serverSwitchGameModeNotifications() {
        return this.serverSwitchGameModeNotifications;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> incomingRequestNotifications() {
        return this.incomingRequestNotifications;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Boolean> incomingChatMessageNotifications() {
        return this.incomingChatMessageNotifications;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Set<UUID>> pinnedFriends() {
        return this.pinnedFriends;
    }

    @Override // net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor
    public ConfigProperty<Map<UUID, String>> friendNotes() {
        return this.friendNotes;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/configuration/DefaultLabyConnectConfig$LabyConnectConfigProvider.class */
    public static class LabyConnectConfigProvider extends ConfigProvider<LabyConnectConfigAccessor> {
        public static final LabyConnectConfigProvider INSTANCE = new LabyConnectConfigProvider();

        private LabyConnectConfigProvider() {
        }

        @Override // net.labymod.api.configuration.loader.ConfigProvider
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultLabyConnectConfig.class;
        }
    }
}

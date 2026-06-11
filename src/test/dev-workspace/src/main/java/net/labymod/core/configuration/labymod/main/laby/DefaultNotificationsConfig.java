package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.NotificationsConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/DefaultNotificationsConfig.class */
public class DefaultNotificationsConfig extends Config implements NotificationsConfig {

    @SpriteSlot(x = 1, y = 2, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 2, y = 2, page = 1)
    @SliderWidget.SliderSetting(min = 3.0f, max = 10.0f)
    @SettingRequires("enabled")
    private final ConfigProperty<Integer> maxNotifications = new ConfigProperty<>(5);

    @SpriteSlot(y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> hideChatTrustToast = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, y = 5, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> screenshot = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> screenshotSound = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.NotificationsConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.NotificationsConfig
    public ConfigProperty<Integer> maxNotifications() {
        return this.maxNotifications;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.NotificationsConfig
    public ConfigProperty<Boolean> hideChatTrustToast() {
        return this.hideChatTrustToast;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.NotificationsConfig
    public ConfigProperty<Boolean> screenshot() {
        return this.screenshot;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.NotificationsConfig
    public ConfigProperty<Boolean> screenshotSound() {
        return this.screenshotSound;
    }
}

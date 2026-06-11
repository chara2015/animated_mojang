package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/NotificationsConfig.class */
public interface NotificationsConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Integer> maxNotifications();

    ConfigProperty<Boolean> hideChatTrustToast();

    ConfigProperty<Boolean> screenshot();

    ConfigProperty<Boolean> screenshotSound();
}

package net.labymod.core.main.listener;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/SettingListener.class */
@Singleton
public class SettingListener {
    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
        SettingElement setting = event.setting();
        if (setting == null || !setting.getPath().equals("settings.appearance.darkLoadingScreen") || !(event.getValue() instanceof Boolean)) {
            return;
        }
        boolean value = ((Boolean) event.getValue()).booleanValue();
        Laby.labyAPI().minecraft().options().setEyeProtectionActive(value);
    }
}

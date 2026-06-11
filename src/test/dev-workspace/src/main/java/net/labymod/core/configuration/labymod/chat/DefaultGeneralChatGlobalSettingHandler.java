package net.labymod.core.configuration.labymod.chat;

import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatGlobalSettingHandler;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatSettingActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/chat/DefaultGeneralChatGlobalSettingHandler.class */
@Singleton
@Implements(GeneralChatGlobalSettingHandler.class)
public class DefaultGeneralChatGlobalSettingHandler implements GeneralChatGlobalSettingHandler {
    private final LabyAPI labyAPI;

    @Inject
    public DefaultGeneralChatGlobalSettingHandler(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public void created(Setting setting) {
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public void initialized(Setting setting) {
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public boolean opensActivity(Setting setting) {
        return true;
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public Activity activity(Setting setting) {
        RootSettingRegistry settingRegistry = RootSettingRegistry.labymod(setting.getId()).translationId("settings.ingame.advancedChat");
        List<Setting> chatSettings = ((Config) this.labyAPI.config().ingame().advancedChat()).toSettings();
        for (Setting chatSetting : chatSettings) {
            if (chatSetting.isElement() && chatSetting.getId().toLowerCase(Locale.ROOT).contains("global")) {
                settingRegistry.register(chatSetting);
            }
        }
        return new ChatSettingActivity(settingRegistry);
    }
}

package net.labymod.api.configuration.labymod.chat.category;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatData;
import net.labymod.api.client.chat.filter.ChatFilter;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingRequiresExclude;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/category/GeneralChatTabConfig.class */
@CustomTranslation("labymod.chattab.general")
@SettingRequires(value = "global", invert = true)
public class GeneralChatTabConfig extends Config {
    private static final AdvancedChatConfig CHAT_SETTING = Laby.labyAPI().config().ingame().advancedChat();

    @SettingRequiresExclude
    @TextFieldWidget.TextFieldSetting
    @CustomTranslation("labymod.chattab.general.identifier")
    private final ConfigProperty<String> name = new ConfigProperty<>("");

    @SettingRequiresExclude
    @CustomTranslation("labymod.chattab.")
    private final ConfigProperty<List<ChatFilter>> filters = new ConfigProperty<>(new ArrayList());

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> global = new ConfigProperty<>(true);

    @SliderWidget.SliderSetting(min = 100.0f, max = 1000.0f, steps = 10.0f)
    private final ConfigProperty<Integer> chatLimit = new ConfigProperty<>(CHAT_SETTING.globalChatLimit().get());

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> combineChatMessages = new ConfigProperty<>(CHAT_SETTING.globalCombineChatMessages().get());

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> antiChatClear = new ConfigProperty<>(CHAT_SETTING.globalAntiChatClear().get());

    @VersionCompatibility(ChatData.CHAT_TRUST_VERSIONS)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> chatTrust = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> shadow = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> background = new ConfigProperty<>(true);

    public GeneralChatTabConfig(String name) {
        this.name.set(name);
    }

    public GeneralChatTabConfig() {
    }

    public ConfigProperty<String> name() {
        return this.name;
    }

    public ConfigProperty<Boolean> global() {
        return this.global;
    }

    public ConfigProperty<Integer> chatLimit() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalChatLimit() : this.chatLimit;
    }

    public ConfigProperty<Boolean> combineChatMessages() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalCombineChatMessages() : this.combineChatMessages;
    }

    public ConfigProperty<Boolean> antiChatClear() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalAntiChatClear() : this.antiChatClear;
    }

    public ConfigProperty<Boolean> chatTrust() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalChatTrust() : this.chatTrust;
    }

    public ConfigProperty<Boolean> shadow() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalShadow() : this.shadow;
    }

    public ConfigProperty<Boolean> background() {
        return this.global.get().booleanValue() ? CHAT_SETTING.globalBackground() : this.background;
    }

    public ConfigProperty<List<ChatFilter>> filters() {
        return this.filters;
    }
}

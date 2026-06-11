package net.labymod.core.configuration.labymod.main.laby.ingame;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultChatInputConfig.class */
public class DefaultChatInputConfig extends Config implements ChatInputConfig {

    @SwitchWidget.SwitchSetting
    @PermissionRequired("chat_autotext")
    private final ConfigProperty<Boolean> autoText = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> nameHistory = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> chatSymbols = new ConfigProperty<>(true);

    @Exclude
    private final ConfigProperty<List<String>> favoriteSymbols = new ConfigProperty<>(new ArrayList());

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig
    public ConfigProperty<Boolean> autoText() {
        return this.autoText;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig
    public ConfigProperty<Boolean> nameHistory() {
        return this.nameHistory;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig
    public ConfigProperty<Boolean> chatSymbols() {
        return this.chatSymbols;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig
    public ConfigProperty<List<String>> favoriteSymbols() {
        return this.favoriteSymbols;
    }
}

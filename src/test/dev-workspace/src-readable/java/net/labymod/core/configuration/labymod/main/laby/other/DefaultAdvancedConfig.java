package net.labymod.core.configuration.labymod.main.laby.other;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/other/DefaultAdvancedConfig.class */
public class DefaultAdvancedConfig extends Config implements AdvancedConfig {
    private final ConfigProperty<Boolean> devTools = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    @SettingSection("devTools")
    @SettingRequires("devTools")
    private final ConfigProperty<Boolean> enableDebugger = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    @SettingRequires("devTools")
    private final ConfigProperty<Boolean> enableRefreshHotkey = new ConfigProperty<>(false);

    @Exclude
    private final ConfigProperty<Map<String, Boolean>> debugWindows = new ConfigProperty<>(new HashMap());

    @Override // net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig
    public boolean debugger() {
        return this.devTools.get().booleanValue() && this.enableDebugger.get().booleanValue();
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig
    public boolean refreshHotkey() {
        return this.devTools.get().booleanValue() && this.enableRefreshHotkey.get().booleanValue();
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig
    public Map<String, Boolean> debugWindows() {
        return this.debugWindows.get();
    }

    public ConfigProperty<Boolean> devTools() {
        return this.devTools;
    }
}

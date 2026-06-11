package net.labymod.core.configuration.labymod.main.laby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CollectionResetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.core.client.WindowController;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultAdvancedConfig;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultDiscordConfig;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultWindowConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/DefaultOtherConfig.class */
public class DefaultOtherConfig extends Config implements OtherConfig {

    @SwitchWidget.SwitchSetting
    private ConfigProperty<Boolean> fastLanguageReload = new ConfigProperty<>(true);

    @SpriteSlot(x = 5, y = 7)
    private DefaultDiscordConfig discord = new DefaultDiscordConfig();

    @SpriteSlot(x = 4, y = 5, page = 1)
    private final DefaultWindowConfig window = new DefaultWindowConfig();

    @SpriteSlot(x = 4, y = 7)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> anonymousStatistics = new ConfigProperty<>(true);
    private final ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings = new ConfigProperty<>(new HashMap());

    @SpriteSlot(x = 3, y = 2, page = 1)
    private DefaultAdvancedConfig advanced = new DefaultAdvancedConfig();
    private final ConfigProperty<Set<String>> conversionAskedNamespaces = new ConfigProperty<>(new HashSet());

    @CollectionResetWidget.CollectionResetSetting
    @SettingSection("serverapi")
    private final ConfigProperty<Map<String, Boolean>> serverSwitchChoices = new ConfigProperty<>(new HashMap());

    @CollectionResetWidget.CollectionResetSetting
    private final ConfigProperty<List<String>> ignoredAddonRecommendations = new ConfigProperty<>(new ArrayList());
    private final ConfigProperty<Integer> lastWidth = new ConfigProperty<>(1280);
    private final ConfigProperty<Integer> lastHeight = new ConfigProperty<>(Integer.valueOf(WindowController.DEFAULT_HEIGHT));
    private final ConfigProperty<String> preferredCurrency = new ConfigProperty<>("null");

    @Exclude
    private final ConfigProperty<SemanticVersion> lastStartedVersion = new ConfigProperty<>(new SemanticVersion(0, 0, 0));

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Boolean> fastLanguageReload() {
        return this.fastLanguageReload;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public DiscordConfig discord() {
        return this.discord;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Boolean> anonymousStatistics() {
        return this.anonymousStatistics;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings() {
        return this.outOfMemoryWarnings;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public AdvancedConfig advanced() {
        return this.advanced;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Set<String>> conversionAskedNamespaces() {
        return this.conversionAskedNamespaces;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Integer> lastWidth() {
        return this.lastWidth;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Integer> lastHeight() {
        return this.lastHeight;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<Map<String, Boolean>> serverSwitchChoices() {
        return this.serverSwitchChoices;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<List<String>> ignoredAddonRecommendations() {
        return this.ignoredAddonRecommendations;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public ConfigProperty<String> preferredCurrency() {
        return this.preferredCurrency;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.OtherConfig
    public WindowConfig window() {
        return this.window;
    }

    public ConfigProperty<SemanticVersion> lastStartedVersion() {
        return this.lastStartedVersion;
    }

    @Override // net.labymod.api.configuration.loader.Config
    public int getConfigVersion() {
        return 2;
    }
}

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultDynamicBackgroundConfig;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultNavigationConfig;
import net.labymod.core.configuration.labymod.main.laby.appearance.DefaultTitleScreenConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMenuBlurConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/DefaultAppearanceConfig.class */
public class DefaultAppearanceConfig extends Config implements AppearanceConfig {

    @SpriteSlot
    @AdvancedSelectionWidget.AdvancedSelectionSetting
    private final ConfigProperty<String> theme = new ConfigProperty<>(FancyTheme.ID);

    @SpriteSlot(x = 6, y = 2, page = 1)
    private DefaultDynamicBackgroundConfig dynamicBackground = new DefaultDynamicBackgroundConfig();

    @VersionCompatibility("1.13<*")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1)
    @SettingSection("loadingScreen")
    private final ConfigProperty<Boolean> darkLoadingScreen = new ConfigProperty<>(false);

    @VersionCompatibility("1.13<*")
    @DropdownWidget.DropdownSetting
    @SpriteSlot(x = 2)
    @SettingRequires(value = "dynamicBackground", invert = true)
    private final ConfigProperty<FadeOutAnimationType> fadeOutAnimation = new ConfigProperty<>(FadeOutAnimationType.FADING);

    @SpriteSlot(x = 6)
    @SettingSection("menus")
    private DefaultNavigationConfig navigation = new DefaultNavigationConfig();

    @SpriteSlot(x = 3)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> hideMenuBackground = new ConfigProperty<>(false);

    @SpriteSlot(x = 3, y = 5, page = 1)
    @IntroducedIn("4.1.0")
    private final DefaultMenuBlurConfig menuBlur = new DefaultMenuBlurConfig();

    @SpriteSlot(x = 3, y = 3, page = 1)
    private DefaultTitleScreenConfig customTitleScreen = new DefaultTitleScreenConfig();

    @SpriteSlot(x = 4, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fixedTooltips = new ConfigProperty<>(false);

    @SpriteSlot(x = 3, y = 7)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> replaceSkinCustomization = ConfigProperty.create(true);

    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 4)
    private final ConfigProperty<Boolean> cleanWindowTitle = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<String> theme() {
        return this.theme;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public DynamicBackgroundConfig dynamicBackground() {
        return this.dynamicBackground;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<Boolean> darkLoadingScreen() {
        return this.darkLoadingScreen;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<FadeOutAnimationType> fadeOutAnimation() {
        return this.fadeOutAnimation;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<Boolean> hideMenuBackground() {
        return this.hideMenuBackground;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<Boolean> cleanWindowTitle() {
        return this.cleanWindowTitle;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<Boolean> fixedTooltips() {
        return this.fixedTooltips;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<HighQuality> blurQuality() {
        return null;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public NavigationConfig navigation() {
        return this.navigation;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public TitleScreenConfig titleScreen() {
        return this.customTitleScreen;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public MenuBlurConfig menuBlur() {
        return this.menuBlur;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.AppearanceConfig
    public ConfigProperty<Boolean> replaceSkinCustomization() {
        return this.replaceSkinCustomization;
    }

    @Override // net.labymod.api.configuration.loader.Config
    public int getConfigVersion() {
        return 2;
    }
}

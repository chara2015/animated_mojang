package net.labymod.core.client.gui.screen.theme.vanilla;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/VanillaThemeConfig.class */
public class VanillaThemeConfig extends ThemeConfig implements VanillaThemeConfigAccessor {

    @VersionCompatibility("24w09a<*")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> freshUI = (ConfigProperty) new ConfigProperty(true).addChangeListener((type, oldValue, newValue) -> {
        DefaultThemeService themeService = (DefaultThemeService) Laby.labyAPI().themeService();
        themeService.reloadActivities();
    });

    @Override // net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor
    public ConfigProperty<Boolean> freshUI() {
        return this.freshUI;
    }
}

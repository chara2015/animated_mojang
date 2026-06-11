package net.labymod.core.configuration.labymod.main.laby.other;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.OSCompatibility;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.models.OperatingSystem;
import net.labymod.core.configuration.labymod.main.laby.other.microsoft.DefaultMicrosoftWindowConfig;
import net.labymod.core.configuration.labymod.main.laby.other.window.CleanWindowTitleChangeListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/other/DefaultWindowConfig.class */
public class DefaultWindowConfig extends Config implements WindowConfig {

    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 4)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> cleanWindowTitle = (ConfigProperty) ConfigProperty.create(true).addChangeListener(new CleanWindowTitleChangeListener());

    @SpriteSlot(x = 2, y = 7)
    @SwitchWidget.SwitchSetting
    @OSCompatibility({OperatingSystem.WINDOWS, OperatingSystem.LINUX})
    private final ConfigProperty<Boolean> borderlessWindow = ConfigProperty.create(false);

    @VersionCompatibility("1.13<*")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> restoreWindowResolution = ConfigProperty.create(true);

    @OSCompatibility({OperatingSystem.WINDOWS})
    private final DefaultMicrosoftWindowConfig microsoftWindow = new DefaultMicrosoftWindowConfig();

    @Override // net.labymod.api.configuration.labymod.main.laby.other.WindowConfig
    public ConfigProperty<Boolean> cleanWindowTitle() {
        return this.cleanWindowTitle;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.WindowConfig
    public ConfigProperty<Boolean> borderlessWindow() {
        return this.borderlessWindow;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.WindowConfig
    public ConfigProperty<Boolean> restoreWindowResolution() {
        return this.restoreWindowResolution;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.WindowConfig
    public MicrosoftWindowConfig microsoftWindow() {
        return this.microsoftWindow;
    }
}

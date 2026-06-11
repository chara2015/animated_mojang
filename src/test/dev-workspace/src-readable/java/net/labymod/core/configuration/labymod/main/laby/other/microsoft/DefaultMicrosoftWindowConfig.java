package net.labymod.core.configuration.labymod.main.laby.other.microsoft;

import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.window.WindowHandleStorage;
import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.Color;
import net.labymod.core.client.os.windows.window.WindowManagement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/other/microsoft/DefaultMicrosoftWindowConfig.class */
public class DefaultMicrosoftWindowConfig extends Config implements MicrosoftWindowConfig {

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> immersiveDarkMode = (ConfigProperty) ConfigProperty.create(false).addChangeListener(new UpdateListener());

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<MicrosoftWindowConfig.WindowMaterial> windowMaterial = (ConfigProperty) ConfigProperty.createEnum(MicrosoftWindowConfig.WindowMaterial.AUTO).visibilitySupplier(WindowManagement::isWindowMaterialSupported).addChangeListener(new UpdateListener());

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<MicrosoftWindowConfig.CornerCurvatures> cornerCurvatures = (ConfigProperty) ConfigProperty.createEnum(MicrosoftWindowConfig.CornerCurvatures.DEFAULT).addChangeListener(new UpdateListener());

    @SwitchWidget.SwitchSetting
    @SettingSection("windowBorder")
    private final ConfigProperty<Boolean> defaultWindowBorderColor = (ConfigProperty) ConfigProperty.create(true).addChangeListener(new UpdateListener());

    @SwitchWidget.SwitchSetting
    @SettingRequires(value = "defaultWindowBorderColor", invert = true)
    private final ConfigProperty<Boolean> hideWindowBorder = (ConfigProperty) ConfigProperty.create(false).addChangeListener(new UpdateListener());

    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultWindowBorderColor", invert = true)
    private final ConfigProperty<Color> windowBorderColor = (ConfigProperty) ConfigProperty.create(Color.WHITE).addChangeListener(new UpdateListener());

    @SwitchWidget.SwitchSetting
    @SettingSection("windowTitleBar")
    private final ConfigProperty<Boolean> defaultWindowTitleBarColor = (ConfigProperty) ConfigProperty.create(true).addChangeListener(new UpdateListener());

    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultWindowTitleBarColor", invert = true)
    private final ConfigProperty<Color> windowTitleBarColor = (ConfigProperty) ConfigProperty.create(Color.WHITE).addChangeListener(new UpdateListener());

    @SwitchWidget.SwitchSetting
    @SettingSection("titleText")
    private final ConfigProperty<Boolean> defaultTitleTextColor = (ConfigProperty) ConfigProperty.create(true).addChangeListener(new UpdateListener());

    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultTitleTextColor", invert = true)
    private final ConfigProperty<Color> titleTextColor = (ConfigProperty) ConfigProperty.create(Color.BLACK).addChangeListener(new UpdateListener());

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Boolean> immersiveDarkMode() {
        return this.immersiveDarkMode;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<MicrosoftWindowConfig.WindowMaterial> windowMaterial() {
        return this.windowMaterial;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<MicrosoftWindowConfig.CornerCurvatures> cornerCurvatures() {
        return this.cornerCurvatures;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Boolean> defaultWindowBorderColor() {
        return this.defaultWindowBorderColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Boolean> hideWindowBorder() {
        return this.hideWindowBorder;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Color> windowBorderColor() {
        return this.windowBorderColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Boolean> defaultWindowTitleBarColor() {
        return this.defaultWindowTitleBarColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Color> windowTitleBarColor() {
        return this.windowTitleBarColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Boolean> defaultTitleTextColor() {
        return this.defaultTitleTextColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig
    public ConfigProperty<Color> titleTextColor() {
        return this.titleTextColor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/other/microsoft/DefaultMicrosoftWindowConfig$UpdateListener.class */
    private static class UpdateListener implements Runnable {
        private UpdateListener() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Minecraft minecraft = Laby.labyAPI().minecraft();
            if (minecraft != null) {
                Window window = minecraft.minecraftWindow();
                WindowManagement.update(window.getPointer());
                LongListIterator it = WindowHandleStorage.WINDOW_HANDLES.iterator();
                while (it.hasNext()) {
                    long windowPointer = ((Long) it.next()).longValue();
                    if (windowPointer != window.getPointer()) {
                        WindowManagement.update(windowPointer);
                    }
                }
            }
        }
    }
}

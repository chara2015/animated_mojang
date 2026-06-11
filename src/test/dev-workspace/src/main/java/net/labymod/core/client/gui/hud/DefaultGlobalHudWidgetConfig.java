package net.labymod.core.client.gui.hud;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.ConfigPath;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.Color;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/DefaultGlobalHudWidgetConfig.class */
@ConfigPath("hud")
@ConfigName("$PROFILE")
public class DefaultGlobalHudWidgetConfig extends Config implements GlobalHudWidgetConfig {

    @SliderWidget.SliderSetting(min = 0.5f, max = 1.5f, steps = 0.1f)
    @CustomTranslation("labymod.hudWidget.global.scale")
    @Exclude
    private final ConfigProperty<Float> scale = new ConfigProperty<>(Float.valueOf(1.0f));

    @ColorPickerWidget.ColorPickerSetting
    @SettingSection("color")
    @CustomTranslation("labymod.hudWidget.text.labelColor")
    private final ConfigProperty<Color> labelColor = new ConfigProperty<>(Color.of("#FFAA00"));

    @ColorPickerWidget.ColorPickerSetting
    @CustomTranslation("labymod.hudWidget.text.bracketColor")
    private final ConfigProperty<Color> bracketColor = new ConfigProperty<>(Color.of("#AAAAAA"));

    @ColorPickerWidget.ColorPickerSetting
    @CustomTranslation("labymod.hudWidget.text.valueColor")
    private final ConfigProperty<Color> valueColor = new ConfigProperty<>(Color.WHITE);

    @SliderWidget.SliderSetting(min = TimeUtil.TICKS_TO_MILLISECONDS, max = 150.0f, steps = 10.0f)
    @CustomTranslation("labymod.hudWidget.text.lineHeight")
    private final ConfigProperty<Integer> lineHeight = new ConfigProperty<>(100);

    @DropdownWidget.DropdownSetting
    @SettingSection("miscellaneous")
    @CustomTranslation("labymod.hudWidget.text.formatting")
    private final ConfigProperty<Formatting> formatting = ConfigProperty.createEnum(Formatting.SQUARE_BRACKETS);

    @CustomTranslation("labymod.hudWidget.background")
    private BackgroundConfig background = new BackgroundConfig();

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> smoothMovement = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> itemGravity = new ConfigProperty<>(true);

    @Exclude
    private Map<String, JsonObject> configs = new HashMap();

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Float> scale() {
        return this.scale;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Color> valueColor() {
        return this.valueColor;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Color> bracketColor() {
        return this.bracketColor;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Color> labelColor() {
        return this.labelColor;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Integer> lineHeight() {
        return this.lineHeight;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Formatting> formatting() {
        return this.formatting;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public BackgroundConfig background() {
        return this.background;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Boolean> smoothMovement() {
        return this.smoothMovement;
    }

    @Override // net.labymod.api.client.gui.hud.GlobalHudWidgetConfig
    public ConfigProperty<Boolean> itemGravity() {
        return this.itemGravity;
    }

    public Map<String, JsonObject> getConfigs() {
        return this.configs;
    }

    public void setConfigs(Map<String, JsonObject> configs) {
        this.configs = configs;
    }
}

package net.labymod.core.client.gui.screen.theme.fancy;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderUtil;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.util.Color;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.FancyVariableThemeEventListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/FancyThemeConfig.class */
public class FancyThemeConfig extends ThemeConfig {
    public static final ThemeUpdateEvent.Reason FONT_UPDATE_REASON = ThemeUpdateEvent.Reason.of("FONT_UPDATE", true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fancyFont = (ConfigProperty) new ConfigProperty(true).addChangeListener((type, oldValue, newValue) -> {
        updateFonts();
    });

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> ingameFancyFont = (ConfigProperty) new ConfigProperty(true).addChangeListener((type, oldValue, newValue) -> {
        updateFonts();
        Laby.references().textRendererProvider().forceMinecraftRenderer(false);
    });

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fancySounds = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> activityTransitions = new ConfigProperty<>(true);

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<HighQuality> blurQuality = ConfigProperty.createEnum(HighQuality.HIGH);

    @ColorPickerWidget.ColorPickerSetting(alpha = true)
    @IntroducedIn("4.1.0")
    private final ConfigProperty<Color> buttonColor = (ConfigProperty) ConfigProperty.create(Color.of(15001321, 30)).addChangeListener((type, oldValue, newValue) -> {
        updateThemeVariables();
    });

    @ColorPickerWidget.ColorPickerSetting(alpha = true)
    @IntroducedIn("4.1.0")
    private final ConfigProperty<Color> accentColor = (ConfigProperty) ConfigProperty.create(Color.of(813232, 255)).addChangeListener((type, oldValue, newValue) -> {
        updateThemeVariables();
    });
    private transient boolean hasActiveShaderPack;

    public ConfigProperty<Boolean> fancyFont() {
        return this.fancyFont;
    }

    public ConfigProperty<Boolean> ingameFancyFont() {
        return this.ingameFancyFont;
    }

    public boolean isIngameFancyFont() {
        boolean hasActiveShaderPack = ShaderUtil.isShaderSelected();
        if (this.hasActiveShaderPack != hasActiveShaderPack) {
            this.hasActiveShaderPack = hasActiveShaderPack;
            updateFonts();
        }
        return !hasActiveShaderPack && (!fancyFont().get().booleanValue() || ingameFancyFont().get().booleanValue());
    }

    public ConfigProperty<Boolean> fancySounds() {
        return this.fancySounds;
    }

    public ConfigProperty<Boolean> activityTransitions() {
        return this.activityTransitions;
    }

    public ConfigProperty<HighQuality> blurQuality() {
        return this.blurQuality;
    }

    public ConfigProperty<Color> buttonColor() {
        return this.buttonColor;
    }

    public ConfigProperty<Color> accentColor() {
        return this.accentColor;
    }

    private void updateThemeVariables() {
        FancyVariableThemeEventListener listener;
        FancyTheme theme = (FancyTheme) Laby.labyAPI().themeService().getThemeByName(FancyTheme.ID);
        if (theme != null && (listener = theme.getVariableListener()) != null) {
            listener.updateThemeVariables();
        }
    }

    private void updateFonts() {
        DefaultThemeService themeService = (DefaultThemeService) Laby.labyAPI().themeService();
        if (themeService.isInitialized()) {
            Laby.labyAPI().minecraft().executeNextTick(() -> {
                Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
                Laby.references().textRendererProvider().forceMinecraftRenderer(false);
                Laby.fireEvent(new ThemeUpdateEvent(themeService.currentTheme(), FONT_UPDATE_REASON));
                themeService.reloadActivities();
            });
        }
    }
}

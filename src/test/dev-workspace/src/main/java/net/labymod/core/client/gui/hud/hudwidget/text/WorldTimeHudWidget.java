package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Locale;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/WorldTimeHudWidget.class */
@SpriteSlot(x = 1, y = 3)
@IntroducedIn("4.1.0")
public class WorldTimeHudWidget extends TextHudWidget<WorldTimeHudWidgetConfig> {
    private TextLine timeLine;

    public WorldTimeHudWidget() {
        super("world_time", WorldTimeHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(WorldTimeHudWidgetConfig config) {
        super.load(config);
        this.timeLine = super.createLine("World Time", "00:00");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        String str;
        boolean militaryFormat = ((WorldTimeHudWidgetConfig) this.config).militaryFormat().get().booleanValue();
        boolean showDays = ((WorldTimeHudWidgetConfig) this.config).showDays().get().booleanValue();
        long timePassed = this.labyAPI.minecraft().clientWorld().getDayTime();
        long days = (timePassed / 24000) + 1;
        long millisSinceMidnight = timePassed % 24000;
        int hour = (int) (((millisSinceMidnight / 1000) + 6) % 24);
        int minute = (int) (((millisSinceMidnight % 1000) / 1000.0d) * 60.0d);
        if (((WorldTimeHudWidgetConfig) this.config).showOnNewDay().get().booleanValue()) {
            showDays = millisSinceMidnight >= 0 && millisSinceMidnight <= 500;
        }
        if (showDays) {
            str = " (%s)".formatted(I18n.translate("labymod.hudWidget.world_time.showDays.day", Long.valueOf(days)));
        } else {
            str = "";
        }
        String daySuffix = str;
        if (militaryFormat) {
            this.timeLine.updateAndFlush(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute)) + daySuffix);
        } else {
            String suffix = hour >= 12 ? "pm" : "am";
            this.timeLine.updateAndFlush(String.format(Locale.ROOT, "%d:%02d %s", Integer.valueOf(hour % 12 == 0 ? 12 : hour % 12), Integer.valueOf(minute), suffix) + daySuffix);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/WorldTimeHudWidget$WorldTimeHudWidgetConfig.class */
    public static class WorldTimeHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> militaryFormat = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showDays = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        @SettingRequires("showDays")
        private final ConfigProperty<Boolean> showOnNewDay = new ConfigProperty<>(false);

        public ConfigProperty<Boolean> militaryFormat() {
            return this.militaryFormat;
        }

        public ConfigProperty<Boolean> showDays() {
            return this.showDays;
        }

        public ConfigProperty<Boolean> showOnNewDay() {
            return this.showOnNewDay;
        }
    }
}

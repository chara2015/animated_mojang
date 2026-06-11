package net.labymod.core.client.gui.hud.hudwidget.text;

import java.time.format.DateTimeFormatter;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.time.DateUtil;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/DateHudWidget.class */
@SpriteSlot(x = 7)
public class DateHudWidget extends TextHudWidget<DateHudWidgetConfig> {
    private String selectedDateFormat;
    private DateTimeFormatter dateFormat;
    private long lastUpdate;
    private TextLine clockLine;
    private String lastDateString;

    public DateHudWidget() {
        super("date", DateHudWidgetConfig.class);
        this.lastUpdate = -1L;
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(DateHudWidgetConfig config) {
        super.load(config);
        this.clockLine = super.createLine("Date", "?");
        updateConfig();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onUpdate() {
        updateConfig();
        super.onUpdate();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        String now;
        if (this.lastUpdate + 10000 >= TimeUtil.getCurrentTimeMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getCurrentTimeMillis();
        if (this.dateFormat == null) {
            now = "Invalid date format: '" + this.selectedDateFormat + "'";
        } else {
            now = DateUtil.formatNow(this.dateFormat, DateUtil.DEFAULT_TIMEZONE, DateUtil.TemporalAccessorType.DATE);
        }
        String formattedTime = now;
        if (this.lastDateString != null && this.lastDateString.equals(formattedTime)) {
            return;
        }
        this.lastDateString = formattedTime;
        this.clockLine.updateAndFlush(formattedTime);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateConfig() {
        DateFormat timeFormat = ((DateHudWidgetConfig) getConfig()).dateFormat().get();
        if (timeFormat.equals(DateFormat.CUSTOM)) {
            this.selectedDateFormat = ((DateHudWidgetConfig) getConfig()).customFormat().getOrDefault(DateFormat.US.getFormat());
        } else {
            this.selectedDateFormat = timeFormat.getFormat();
        }
        try {
            this.dateFormat = DateUtil.ofPattern(this.selectedDateFormat, DateUtil.TemporalAccessorType.DATE);
        } catch (IllegalArgumentException e) {
            this.dateFormat = null;
        }
        this.lastUpdate = -1L;
        this.lastDateString = null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/DateHudWidget$DateFormat.class */
    public enum DateFormat {
        FULL_US("MM/dd/YYYY"),
        US("MM/dd/YY"),
        SHORT_US("MM/dd"),
        FULL_EU("dd.MM.yyyy"),
        EU("dd.MM.yy"),
        SHORT_EU("dd.MM"),
        CUSTOM(null);

        private final String format;

        DateFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/DateHudWidget$DateHudWidgetConfig.class */
    public static class DateHudWidgetConfig extends TextHudWidgetConfig {

        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DateFormat> dateFormat = ConfigProperty.createEnum(DateFormat.FULL_US);

        @TextFieldWidget.TextFieldSetting
        @SettingRequires(value = "dateFormat", required = "CUSTOM")
        private final ConfigProperty<String> customFormat = new ConfigProperty<>("");

        public ConfigProperty<DateFormat> dateFormat() {
            return this.dateFormat;
        }

        public ConfigProperty<String> customFormat() {
            return this.customFormat;
        }
    }
}

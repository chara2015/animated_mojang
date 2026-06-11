package net.labymod.core.client.gui.hud.hudwidget.text;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.DateUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ClockHudWidget.class */
@SpriteSlot(x = 2)
public class ClockHudWidget extends TextHudWidget<ClockHudWidgetConfig> {
    private static final Logging LOGGER = Logging.create((Class<?>) ClockHudWidget.class);
    private static final ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault();
    private ZoneId selectedZoneId;
    private TextLine clockLine;
    private String selectedClockFormat;
    private DateTimeFormatter clockFormat;
    private String lastTimeString;

    public ClockHudWidget() {
        super("clock", ClockHudWidgetConfig.class);
        this.selectedZoneId = DEFAULT_TIMEZONE;
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(ClockHudWidgetConfig config) {
        super.load(config);
        this.clockLine = super.createLine("Clock", "?");
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
        if (this.clockFormat == null) {
            now = "Invalid time format: '" + this.selectedClockFormat + "'";
        } else {
            now = DateUtil.formatNow(this.clockFormat, this.selectedZoneId, DateUtil.TemporalAccessorType.TIME);
        }
        String formattedTime = now;
        if (this.lastTimeString != null && this.lastTimeString.equals(formattedTime)) {
            return;
        }
        this.lastTimeString = formattedTime;
        this.clockLine.updateAndFlush(formattedTime);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateConfig() {
        TimeFormat timeFormat = ((ClockHudWidgetConfig) getConfig()).timeFormat().get();
        if (timeFormat.equals(TimeFormat.CUSTOM)) {
            this.selectedClockFormat = ((ClockHudWidgetConfig) getConfig()).customFormat().getOrDefault(TimeFormat.CLOCK_24.getFormat());
        } else {
            this.selectedClockFormat = timeFormat.getFormat();
        }
        try {
            this.clockFormat = DateUtil.ofPattern(this.selectedClockFormat, DateUtil.TemporalAccessorType.TIME);
            String customTimeZone = ((ClockHudWidgetConfig) getConfig()).customTimeZone().get();
            if (customTimeZone != null && !customTimeZone.isEmpty()) {
                try {
                    ZoneId zoneId = ZoneId.of(customTimeZone);
                    TimeZone timeZone = TimeZone.getTimeZone(customTimeZone);
                    if (timeZone != null) {
                        this.selectedZoneId = zoneId;
                    }
                } catch (Exception e) {
                    LOGGER.error("Invalid time zone: {}", customTimeZone);
                    this.selectedZoneId = DEFAULT_TIMEZONE;
                }
            } else {
                this.selectedZoneId = DEFAULT_TIMEZONE;
            }
        } catch (IllegalArgumentException error) {
            LOGGER.error(error.getMessage(), new Object[0]);
            this.clockFormat = null;
        }
        this.lastTimeString = null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ClockHudWidget$TimeFormat.class */
    public enum TimeFormat {
        CLOCK_12_SECONDS("h:mm:ss a"),
        CLOCK_12("h:mm a"),
        CLOCK_24_SECONDS("HH:mm:ss"),
        CLOCK_24("HH:mm"),
        CUSTOM(null);

        private final String format;

        TimeFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ClockHudWidget$ClockHudWidgetConfig.class */
    public static class ClockHudWidgetConfig extends TextHudWidgetConfig {

        @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.clock.timeFormat.entries")
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<TimeFormat> timeFormat = ConfigProperty.createEnum(TimeFormat.CLOCK_12);

        @TextFieldWidget.TextFieldSetting
        @SettingRequires(value = "timeFormat", required = "CUSTOM")
        private final ConfigProperty<String> customFormat = new ConfigProperty<>("HH:mm:ss");

        @TextFieldWidget.TextFieldSetting
        private final ConfigProperty<String> customTimeZone = new ConfigProperty<>("");

        public ConfigProperty<TimeFormat> timeFormat() {
            return this.timeFormat;
        }

        public ConfigProperty<String> customFormat() {
            return this.customFormat;
        }

        public ConfigProperty<String> customTimeZone() {
            return this.customTimeZone;
        }
    }
}

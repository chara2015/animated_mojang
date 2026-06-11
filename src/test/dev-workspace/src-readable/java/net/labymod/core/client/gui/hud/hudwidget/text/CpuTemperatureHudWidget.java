package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.I18n;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CpuTemperatureHudWidget.class */
@SpriteSlot(x = 5, y = 4)
@IntroducedIn("4.1.0")
public class CpuTemperatureHudWidget extends TextHudWidget<CpuTemperatureHudWidgetConfig> {
    private final SystemInfo systemInfo;
    private TextLine temperatureLine;
    private long nextUpdate;
    private double lastTemperature;

    public CpuTemperatureHudWidget() {
        super("cpu_temperature", CpuTemperatureHudWidgetConfig.class);
        this.lastTemperature = -2.147483648E9d;
        this.systemInfo = Laby.references().systemInfo();
        bindCategory(HudWidgetCategory.SYSTEM);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(CpuTemperatureHudWidgetConfig config) {
        super.load(config);
        this.temperatureLine = super.createLine("CPU Temperature", "?");
        this.lastTemperature = -2.147483648E9d;
        this.nextUpdate = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        if (this.nextUpdate > TimeUtil.getMillis()) {
            return;
        }
        double temperature = this.systemInfo.getCPUTemperature();
        boolean invalid = temperature == -1.0d || temperature == 0.0d || Double.isNaN(temperature);
        this.nextUpdate = TimeUtil.getMillis() + ((long) (SubmissionOrders.DEBUG * (invalid ? 10 : 1)));
        if (this.lastTemperature == temperature) {
            return;
        }
        this.lastTemperature = temperature;
        if (invalid) {
            boolean isWindows = OperatingSystem.getPlatform() == OperatingSystem.WINDOWS;
            this.temperatureLine.updateAndFlush(isWindows ? I18n.getTranslation("labymod.hudWidget.cpu_temperature.requires", "OpenHardwareMonitor") : "?");
            return;
        }
        boolean displayInFahrenheit = ((CpuTemperatureHudWidgetConfig) this.config).fahrenheit().get().booleanValue();
        float fahrenheit = (float) ((temperature * 1.8d) + 32.0d);
        TextLine textLine = this.temperatureLine;
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[2];
        objArr[0] = Double.valueOf(displayInFahrenheit ? fahrenheit : temperature);
        objArr[1] = displayInFahrenheit ? "F" : "C";
        textLine.updateAndFlush(String.format(locale, "%.1f°%s", objArr));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CpuTemperatureHudWidget$CpuTemperatureHudWidgetConfig.class */
    public static class CpuTemperatureHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> fahrenheit = new ConfigProperty<>(false);

        public ConfigProperty<Boolean> fahrenheit() {
            return this.fahrenheit;
        }
    }
}

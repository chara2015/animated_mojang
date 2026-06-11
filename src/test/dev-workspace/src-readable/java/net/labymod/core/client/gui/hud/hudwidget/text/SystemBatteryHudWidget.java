package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/SystemBatteryHudWidget.class */
@SpriteSlot(x = 6, y = 2)
public class SystemBatteryHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private final SystemInfo systemInfo;
    private TextLine batteryLevelLine;
    private long lastUpdate;
    private double lastBatteryLevel;

    public SystemBatteryHudWidget() {
        super("system_battery_level");
        this.lastUpdate = -1L;
        this.lastBatteryLevel = 0.0d;
        this.systemInfo = Laby.references().systemInfo();
        bindCategory(HudWidgetCategory.SYSTEM);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.batteryLevelLine = super.createLine("Battery", "?");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        if (this.lastUpdate + 10000 >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        double batteryLevel = this.systemInfo.getBatteryLevel();
        if (this.lastBatteryLevel == batteryLevel) {
            return;
        }
        this.lastBatteryLevel = batteryLevel;
        this.batteryLevelLine.updateAndFlush(batteryLevel == -1.0d ? "?" : MathHelper.floor(batteryLevel) + "%");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.lastBatteryLevel != 0.0d;
    }
}

package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/SystemMemoryHudWidget.class */
@SpriteSlot(x = 1, y = 4)
public class SystemMemoryHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private final SystemInfo systemInfo;
    private TextLine memoryUsageLine;
    private long lastUpdate;
    private int lastMemoryUsage;

    public SystemMemoryHudWidget() {
        super("system_memory_usage");
        this.lastUpdate = -1L;
        this.systemInfo = Laby.references().systemInfo();
        bindCategory(HudWidgetCategory.SYSTEM);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.memoryUsageLine = super.createLine("System Memory", "?");
        this.lastUpdate = -1L;
        this.lastMemoryUsage = Integer.MIN_VALUE;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        if (this.lastUpdate + 1000 >= TimeUtil.getMillis()) {
            return;
        }
        this.lastUpdate = TimeUtil.getMillis();
        try {
            float totalMemory = this.systemInfo.getTotalMemorySize();
            float freeMemory = this.systemInfo.getFreeMemorySize();
            int memoryUsage = MathHelper.ceil((1.0f - (freeMemory / totalMemory)) * 100.0f);
            if (this.lastMemoryUsage == memoryUsage) {
                return;
            }
            this.lastMemoryUsage = memoryUsage;
            this.memoryUsageLine.updateAndFlush(String.format(Locale.ROOT, "%s%%", Integer.valueOf(memoryUsage)));
        } catch (AbstractMethodError e) {
        }
    }
}

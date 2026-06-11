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
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/PlaytimeHudWidget.class */
@SpriteSlot(x = 4, y = 4)
@IntroducedIn("4.1.0")
public class PlaytimeHudWidget extends TextHudWidget<PlaytimeHudWidgetConfig> {
    private final long timeGameStart;
    private long timeServerStart;
    private long timeServerEnd;
    private TextLine timeLine;

    public PlaytimeHudWidget() {
        super("playtime", PlaytimeHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        this.timeServerStart = -1L;
        this.timeGameStart = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(PlaytimeHudWidgetConfig config) {
        super.load(config);
        this.timeLine = super.createLine("Playtime", "00:00");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        long millis;
        String str;
        boolean gameSession = ((PlaytimeHudWidgetConfig) this.config).gameSession().get().booleanValue();
        long gameTime = TimeUtil.getMillis() - this.timeGameStart;
        if (this.timeServerStart == -1) {
            millis = 0;
        } else if (this.timeServerEnd == -1) {
            millis = TimeUtil.getMillis() - this.timeServerStart;
        } else {
            millis = this.timeServerEnd - this.timeServerStart;
        }
        long serverTime = millis;
        long timePassed = gameSession ? gameTime : serverTime;
        long minutesPassed = (timePassed / 1000) / 60;
        long days = minutesPassed / 1440;
        int hour = ((int) (minutesPassed / 60)) % 24;
        int minute = ((int) minutesPassed) % 60;
        TextLine textLine = this.timeLine;
        if (days > 0) {
            str = String.format(Locale.ROOT, "%dd %02d:%02d", Long.valueOf(days), Integer.valueOf(hour), Integer.valueOf(minute));
        } else {
            str = String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute));
        }
        textLine.updateAndFlush(str);
    }

    @Subscribe
    public void onServerJoin(ServerJoinEvent event) {
        this.timeServerStart = TimeUtil.getMillis();
        this.timeServerEnd = -1L;
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.timeServerEnd = TimeUtil.getMillis();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/PlaytimeHudWidget$PlaytimeHudWidgetConfig.class */
    public static class PlaytimeHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> gameSession = new ConfigProperty<>(false);

        public ConfigProperty<Boolean> gameSession() {
            return this.gameSession;
        }
    }
}

package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.Locale;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.util.Color;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/AfkTimerHudWidget.class */
@SpriteSlot(x = 7, y = 3)
public class AfkTimerHudWidget extends TextHudWidget<AfkTimeHudWidgetConfig> {
    private TextLine textLine;
    private int lastMouseX;
    private int lastMouseY;
    private long timeLastMovement;
    private long timeLastIdle;
    private long lastDurationInSeconds;
    private boolean lastWarning;

    public AfkTimerHudWidget() {
        super("afk_timer", AfkTimeHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(AfkTimeHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("AFK", "00:00");
        this.timeLastIdle = TimeUtil.getMillis();
        this.timeLastMovement = TimeUtil.getMillis();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        long millis;
        int i;
        Minecraft minecraft = this.labyAPI.minecraft();
        int currentMouseX = minecraft.absoluteMouse().getX();
        int currentMouseY = minecraft.absoluteMouse().getY();
        if (currentMouseX == this.lastMouseX && currentMouseY == this.lastMouseY) {
            if (isIdle()) {
                this.timeLastIdle = TimeUtil.getMillis();
            }
        } else {
            this.timeLastMovement = TimeUtil.getMillis();
        }
        this.lastMouseX = currentMouseX;
        this.lastMouseY = currentMouseY;
        if (!isVisibleInGame()) {
            return;
        }
        boolean warning = isWarning();
        if (warning) {
            millis = this.lastDurationInSeconds;
        } else {
            millis = (TimeUtil.getMillis() - this.timeLastMovement) / 1000;
        }
        long currentSeconds = millis;
        if (this.lastDurationInSeconds == currentSeconds && this.lastWarning == warning) {
            return;
        }
        this.lastWarning = warning;
        this.lastDurationInSeconds = currentSeconds;
        long minutes = currentSeconds / 60;
        long seconds = currentSeconds % 60;
        if (isWarning()) {
            i = ((AfkTimeHudWidgetConfig) getConfig()).warningColor().get().get();
        } else {
            i = ((AfkTimeHudWidgetConfig) getConfig()).valueColor().get().get();
        }
        int valueColor = i;
        String time = String.format(Locale.ROOT, "%02d:%02d", Long.valueOf(minutes), Long.valueOf(seconds));
        Component component = Component.text(time).color(TextColor.color(valueColor));
        this.textLine.updateAndFlush(component);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return isIdle() || isWarning();
    }

    private boolean isIdle() {
        return this.timeLastMovement < TimeUtil.getMillis() - ((long) (((AfkTimeHudWidgetConfig) this.config).idleTime().get().intValue() * SubmissionOrders.DEBUG));
    }

    private boolean isWarning() {
        return !isIdle() && this.timeLastIdle > TimeUtil.getMillis() - 3000;
    }

    @Subscribe
    public void keyPress(KeyEvent event) {
        this.timeLastMovement = TimeUtil.getMillis();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/AfkTimerHudWidget$AfkTimeHudWidgetConfig.class */
    public static class AfkTimeHudWidgetConfig extends TextHudWidgetConfig {

        @SliderWidget.SliderSetting(steps = 1.0f, min = 3.0f, max = 1800.0f)
        private final ConfigProperty<Integer> idleTime = new ConfigProperty<>(20);

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> warningColor = new ConfigProperty<>(Color.of(-43691));

        public ConfigProperty<Integer> idleTime() {
            return this.idleTime;
        }

        public ConfigProperty<Color> warningColor() {
            return this.warningColor;
        }
    }
}

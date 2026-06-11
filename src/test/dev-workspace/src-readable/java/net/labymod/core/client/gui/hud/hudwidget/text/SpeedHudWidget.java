package net.labymod.core.client.gui.hud.hudwidget.text;

import java.text.DecimalFormat;
import java.util.Locale;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.math.position.Position;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/SpeedHudWidget.class */
@SpriteSlot(x = 0, y = 2)
public class SpeedHudWidget extends TextHudWidget<SpeedHudWidgetConfig> {
    private DecimalFormat precisionPattern;
    private TextLine textLine;
    private double tickX;
    private double tickY;
    private double tickZ;

    public SpeedHudWidget() {
        super("speed", SpeedHudWidgetConfig.class);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(SpeedHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Speed", "0 blocks/s");
        bindCategory(HudWidgetCategory.INGAME);
        int digits = ((SpeedHudWidgetConfig) this.config).precision().get().intValue();
        this.precisionPattern = new DecimalFormat(digits > 0 ? "0." + "0".repeat(digits) : "0");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget
    public void updateTextContent() {
        double speed = getSpeed();
        this.textLine.updateAndFlush(String.format(Locale.ROOT, "%s blocks/s", this.precisionPattern.format(speed)));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        Minecraft minecraft = this.labyAPI.minecraft();
        Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity == null) {
            return;
        }
        Position position = cameraEntity.position();
        double diffX = position.getX() - this.tickX;
        double diffY = position.getY() - this.tickY;
        double diffZ = position.getZ() - this.tickZ;
        if (diffX > 10.0d) {
            this.tickX = position.getX();
        }
        if (diffZ > 10.0d) {
            this.tickZ = position.getZ();
        }
        if (diffY > 10.0d) {
            this.tickY = position.getY();
        }
        if (diffX < -10.0d) {
            this.tickX = position.getX();
        }
        if (diffZ < -10.0d) {
            this.tickZ = position.getZ();
        }
        if (diffY < -10.0d) {
            this.tickY = position.getY();
        }
        this.tickX += diffX * 0.25d;
        this.tickZ += diffZ * 0.25d;
        this.tickY += diffY * 0.25d;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return getSpeed() != 0.0d || ((SpeedHudWidgetConfig) this.config).displayAtZeroSpeed().get().booleanValue();
    }

    private double getSpeed() {
        Minecraft minecraft = this.labyAPI.minecraft();
        Entity cameraEntity = minecraft.getCameraEntity();
        double deltaX = cameraEntity == null ? 0.0d : (cameraEntity.position().getX() - this.tickX) / 0.15d;
        double deltaY = cameraEntity == null ? 0.0d : (cameraEntity.position().getY() - this.tickY) / 0.15d;
        double deltaZ = cameraEntity == null ? 0.0d : (cameraEntity.position().getZ() - this.tickZ) / 0.15d;
        if (((SpeedHudWidgetConfig) this.config).groundSpeed().get().booleanValue()) {
            return Math.sqrt((deltaX * deltaX) + (deltaZ * deltaZ));
        }
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/SpeedHudWidget$SpeedHudWidgetConfig.class */
    public static class SpeedHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> displayAtZeroSpeed = new ConfigProperty<>(true);

        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision = new ConfigProperty<>(2);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> groundSpeed = new ConfigProperty<>(false);

        public ConfigProperty<Boolean> displayAtZeroSpeed() {
            return this.displayAtZeroSpeed;
        }

        public ConfigProperty<Integer> precision() {
            return this.precision;
        }

        public ConfigProperty<Boolean> groundSpeed() {
            return this.groundSpeed;
        }
    }
}

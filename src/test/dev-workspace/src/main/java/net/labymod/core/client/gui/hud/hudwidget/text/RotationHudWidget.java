package net.labymod.core.client.gui.hud.hudwidget.text;

import java.text.DecimalFormat;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/RotationHudWidget.class */
@SpriteSlot(x = 3, y = 4)
@IntroducedIn("4.1.0")
public class RotationHudWidget extends TextHudWidget<RotationHudWidgetConfig> {
    private TextLine rotationLine;
    private TextLine yawLine;
    private TextLine pitchLine;
    private DecimalFormat precisionPattern;

    public RotationHudWidget() {
        super("rotation", RotationHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(RotationHudWidgetConfig config) {
        super.load(config);
        this.rotationLine = super.createLine("Rotation", "0 0");
        this.yawLine = super.createLine("Yaw", (Object) 0);
        this.pitchLine = super.createLine("Pitch", (Object) 0);
        updateFormat();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget
    public void updateTextContent() {
        update();
    }

    private void update() {
        String strValueOf;
        String strValueOf2;
        Entity entity = this.labyAPI.minecraft().getCameraEntity();
        float yaw = entity == null ? 0.0f : entity.getRotationYaw() % 360.0f;
        float pitch = entity == null ? 0.0f : entity.getRotationPitch();
        if (yaw >= 180.0f) {
            yaw -= 360.0f;
        }
        if (yaw <= -180.0f) {
            yaw += 360.0f;
        }
        boolean hasPrecision = this.precisionPattern != null;
        if (hasPrecision) {
            strValueOf = this.precisionPattern.format(yaw);
        } else {
            strValueOf = String.valueOf(Math.round(yaw));
        }
        String formattedYaw = strValueOf;
        if (hasPrecision) {
            strValueOf2 = this.precisionPattern.format(pitch);
        } else {
            strValueOf2 = String.valueOf(Math.round(pitch));
        }
        String formattedPitch = strValueOf2;
        boolean showYaw = ((RotationHudWidgetConfig) this.config).showYaw().get().booleanValue();
        boolean showPitch = ((RotationHudWidgetConfig) this.config).showPitch().get().booleanValue();
        if (showYaw || showPitch) {
            if (showYaw && showPitch) {
                this.rotationLine.updateAndFlush(formattedYaw + ", " + formattedPitch);
            } else {
                this.rotationLine.updateAndFlush(showYaw ? formattedYaw : formattedPitch);
            }
        } else {
            this.rotationLine.updateAndFlush("<" + I18n.getTranslation("labymod.misc.disabled", new Object[0]) + ">");
        }
        this.yawLine.updateAndFlush(formattedYaw);
        this.pitchLine.updateAndFlush(formattedPitch);
    }

    private void updateFormat() {
        int digits = ((RotationHudWidgetConfig) this.config).precision().get().intValue();
        this.precisionPattern = digits > 0 ? new DecimalFormat("0." + "0".repeat(digits)) : null;
        update();
        boolean singleLine = ((RotationHudWidgetConfig) this.config).singleLine().get().booleanValue();
        boolean showYaw = ((RotationHudWidgetConfig) this.config).showYaw().get().booleanValue();
        boolean showPitch = ((RotationHudWidgetConfig) this.config).showPitch().get().booleanValue();
        this.rotationLine.setState((singleLine || !(showYaw || showPitch)) ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
        this.yawLine.setState((singleLine || !showYaw) ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.pitchLine.setState((singleLine || !showPitch) ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/RotationHudWidget$RotationHudWidgetConfig.class */
    public static class RotationHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine = new ConfigProperty<>(false);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showYaw = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showPitch = new ConfigProperty<>(true);

        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision = new ConfigProperty<>(0);

        public ConfigProperty<Integer> precision() {
            return this.precision;
        }

        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }

        public ConfigProperty<Boolean> showYaw() {
            return this.showYaw;
        }

        public ConfigProperty<Boolean> showPitch() {
            return this.showPitch;
        }
    }
}

package net.labymod.core.client.gui.hud.hudwidget.text;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CoordinateHudWidget.class */
@SpriteSlot(x = 5)
public class CoordinateHudWidget extends TextHudWidget<CoordinateHudWidgetConfig> {
    private static final String XYZ_FORMAT = "%s %s %s";
    private DecimalFormat precisionPattern;
    private TextLine xyzLine;
    private CoordinateTextLine xLine;
    private CoordinateTextLine yLine;
    private CoordinateTextLine zLine;
    private BiomeTextLine biomeLine;

    public CoordinateHudWidget() {
        super("coordinates", CoordinateHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(CoordinateHudWidgetConfig config) {
        super.load(config);
        this.xyzLine = createLine("XYZ", String.format(Locale.ROOT, XYZ_FORMAT, 0, 0, 0));
        this.xLine = (CoordinateTextLine) createLine("X", (Object) 0, CoordinateTextLine::new);
        this.yLine = (CoordinateTextLine) createLine("Y", (Object) 0, CoordinateTextLine::new);
        this.zLine = (CoordinateTextLine) createLine("Z", (Object) 0, CoordinateTextLine::new);
        this.biomeLine = (BiomeTextLine) createLine("Biome", "Plains", BiomeTextLine::new);
        updateFormat();
    }

    private void update() {
        String direction;
        Entity cameraEntity = this.labyAPI.minecraft().getCameraEntity();
        boolean inGame = cameraEntity != null;
        double x = inGame ? cameraEntity.position().getX() : 3141.59265359d;
        double y = inGame ? cameraEntity.position().getY() : 64.0d;
        double z = inGame ? cameraEntity.position().getZ() : -31.4159265359d;
        boolean useBlockCoordinate = ((CoordinateHudWidgetConfig) this.config).blockCoordinate().get().booleanValue();
        boolean hasPrecision = this.precisionPattern != null;
        String formattedX = getCoordinate(hasPrecision, useBlockCoordinate, x);
        String formattedY = getCoordinate(hasPrecision, useBlockCoordinate, y);
        String formattedZ = getCoordinate(hasPrecision, useBlockCoordinate, z);
        if (((CoordinateHudWidgetConfig) this.config).singleLine().get().booleanValue()) {
            this.xyzLine.updateAndFlush(String.format(Locale.ROOT, XYZ_FORMAT, formattedX, formattedY, formattedZ));
        } else {
            this.xLine.updateAndFlush(formattedX);
            this.yLine.updateAndFlush(formattedY);
            this.zLine.updateAndFlush(formattedZ);
            if (((CoordinateHudWidgetConfig) this.config).compass.get().booleanValue()) {
                float yaw = inGame ? MathHelper.wrapDegrees(cameraEntity.getRotationYaw()) + 180.0f : 0.0f;
                boolean xPositive = yaw > 0.0f && yaw < 180.0f;
                boolean zPositive = yaw > 90.0f && yaw < 270.0f;
                boolean axisX = (yaw > 10.0f && yaw < 180.0f - 10.0f) || (yaw > 180.0f + 10.0f && yaw < 360.0f - 10.0f);
                boolean axisZ = (yaw > 90.0f + 10.0f && yaw < 270.0f - 10.0f) || yaw > 270.0f + 10.0f || yaw < 90.0f - 10.0f;
                if (yaw >= 337.5d || yaw < 22.5d) {
                    direction = "N";
                } else if (yaw >= 22.5d && yaw < 67.5d) {
                    direction = "N/E";
                } else if (yaw >= 67.5d && yaw < 112.5d) {
                    direction = "E";
                } else if (yaw >= 112.5d && yaw < 157.5d) {
                    direction = "S/E";
                } else if (yaw >= 157.5d && yaw < 202.5d) {
                    direction = "S";
                } else if (yaw >= 202.5d && yaw < 247.5d) {
                    direction = "S/W";
                } else if (yaw >= 247.5d && yaw < 292.5d) {
                    direction = "W";
                } else if (yaw >= 292.5d && yaw < 337.5d) {
                    direction = "N/W";
                } else {
                    direction = "?";
                }
                TextColor labelColor = TextColor.color(((CoordinateHudWidgetConfig) this.config).labelColor().get().get());
                TextColor bracketColor = TextColor.color(((CoordinateHudWidgetConfig) this.config).bracketColor().get().get());
                TextComponent xComponent = Component.text(axisX ? xPositive ? "+" : "-" : "", bracketColor);
                TextComponent zComponent = Component.text(axisZ ? zPositive ? "+" : "-" : "", bracketColor);
                TextComponent yComponent = Component.text(direction, labelColor);
                this.xLine.updateSuffixAndFlush(xComponent);
                this.yLine.updateSuffixAndFlush(yComponent);
                this.zLine.updateSuffixAndFlush(zComponent);
            }
        }
        if (((CoordinateHudWidgetConfig) this.config).biome.get().booleanValue()) {
            String biome = inGame ? this.labyAPI.minecraft().clientWorld().getReadableBiomeName() : "Plains";
            this.biomeLine.updateAndFlush(biome);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        update();
    }

    private String getCoordinate(boolean hasPrecision, boolean useBlockCoordinate, double value) {
        if (hasPrecision) {
            return this.precisionPattern.format(value);
        }
        if (useBlockCoordinate) {
            return String.valueOf(MathHelper.floor(value));
        }
        return String.valueOf((int) value);
    }

    private void updateFormat() {
        int digits = ((CoordinateHudWidgetConfig) this.config).precision().get().intValue();
        this.precisionPattern = digits > 0 ? new DecimalFormat("0." + "0".repeat(digits)) : null;
        update();
        boolean singleLine = ((CoordinateHudWidgetConfig) this.config).singleLine().get().booleanValue();
        this.xyzLine.setState(singleLine ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
        this.xLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.yLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.zLine.setState(singleLine ? TextLine.State.DISABLED : TextLine.State.VISIBLE);
        this.biomeLine.setState(((CoordinateHudWidgetConfig) this.config).biome.get().booleanValue() ? TextLine.State.VISIBLE : TextLine.State.DISABLED);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CoordinateHudWidget$CoordinateHudWidgetConfig.class */
    public static class CoordinateHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> singleLine = new ConfigProperty<>(false);

        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision = new ConfigProperty<>(0);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> blockCoordinate = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        @SettingRequires(value = "singleLine", invert = true)
        private final ConfigProperty<Boolean> compass = new ConfigProperty<>(false);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> biome = new ConfigProperty<>(false);

        public ConfigProperty<Boolean> singleLine() {
            return this.singleLine;
        }

        public ConfigProperty<Integer> precision() {
            return this.precision;
        }

        public ConfigProperty<Boolean> blockCoordinate() {
            return this.blockCoordinate;
        }

        public ConfigProperty<Boolean> compass() {
            return this.compass;
        }

        public ConfigProperty<Boolean> biome() {
            return this.biome;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CoordinateHudWidget$CoordinateTextLine.class */
    private static class CoordinateTextLine extends TextLine {
        private RenderableComponent suffixRenderableComponent;
        private Component suffixComponent;

        public CoordinateTextLine(TextHudWidget<?> hudWidget, Component key, Object value) {
            super(hudWidget, key, value);
        }

        @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextLine
        public void renderLine(ScreenContext context, float x, float y, float space, HudSize hudWidgetSize) {
            boolean hasCompass = ((CoordinateHudWidgetConfig) config()).compass().get().booleanValue();
            if (hasCompass && this.suffixComponent != null && this.suffixRenderableComponent != null) {
                super.renderLine(context, 0.0f, y, space, hudWidgetSize);
                float suffixX = (hudWidgetSize.getActualWidth() - (space * 2.0f)) - this.suffixRenderableComponent.getWidth();
                ScreenCanvas renderState = context.canvas();
                int flags = 1;
                if (this.floatingPointPosition) {
                    flags = 1 | 4;
                }
                renderState.submitRenderableComponent(this.suffixRenderableComponent, suffixX, y, -1, flags);
                return;
            }
            super.renderLine(context, x, y, space, hudWidgetSize);
        }

        public void updateSuffixAndFlush(Component suffix) {
            if (Objects.equals(this.suffixComponent, suffix)) {
                return;
            }
            this.suffixComponent = suffix;
            this.suffixRenderableComponent = RenderableComponent.of(this.suffixComponent);
        }

        @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextLine
        protected boolean isLeftAligned() {
            return ((CoordinateHudWidgetConfig) config()).compass().get().booleanValue() || super.isLeftAligned();
        }

        @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextLine
        public float getWidth() {
            float padding = ((CoordinateHudWidgetConfig) config()).compass().get().booleanValue() ? 25.0f : 0.0f;
            return super.getWidth() + padding;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/CoordinateHudWidget$BiomeTextLine.class */
    private static class BiomeTextLine extends TextLine {
        public BiomeTextLine(TextHudWidget<?> hudWidget, Component key, Object value) {
            super(hudWidget, key, value);
        }

        @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextLine
        protected void flushInternal() {
            TextHudWidgetConfig config = (TextHudWidgetConfig) this.hudWidget.getConfig();
            TextColor bracketColor = TextColor.color(config.bracketColor().get().get());
            Component valueComponent = updateColor(this.valueComponent, bracketColor);
            this.renderableComponent = RenderableComponent.builder().disableCache().format(valueComponent);
        }

        @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextLine
        protected boolean isLeftAligned() {
            return ((CoordinateHudWidgetConfig) config()).compass().get().booleanValue() || super.isLeftAligned();
        }
    }
}

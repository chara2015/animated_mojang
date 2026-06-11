package net.labymod.core.client.gui.hud.hudwidget;

import java.text.DecimalFormat;
import java.util.Locale;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BlockBreakHudWidget.class */
@SpriteSlot(x = 1, y = 6)
public class BlockBreakHudWidget extends SimpleHudWidget<BlockBreakHudWidgetConfig> {
    private static final float TPS = 20.0f;
    private static final float DUMMY_SECONDS_REMAINING = 11.0f;
    private static final float DUMMY_PROGRESS = 0.9f;
    private DecimalFormat precisionPattern;

    public BlockBreakHudWidget() {
        super("block_break", BlockBreakHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.CROSSHAIR_TOP, NamedHudWidgetDropzones.CROSSHAIR_BOTTOM);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(BlockBreakHudWidgetConfig config) {
        super.load(config);
        int digits = ((BlockBreakHudWidgetConfig) this.config).precision().get().intValue();
        this.precisionPattern = new DecimalFormat(digits > 0 ? "0." + "0".repeat(digits) : "0");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        int i;
        int i2;
        Minecraft minecraft = this.labyAPI.minecraft();
        ScreenCanvas renderState = context.canvas();
        float progress = 0.9f;
        float ticksRemaining = 220.0f;
        BlockState blockState = getDestroyingBlockState();
        if (blockState != null) {
            float hardness = blockState.getHardness(minecraft.getClientPlayer());
            progress = minecraft.getDestroyProgress();
            ticksRemaining = (1.0f - progress) / hardness;
        }
        float secondsRemaining = ticksRemaining / TPS;
        Component component = Component.text(String.format(Locale.ROOT, "%s s", this.precisionPattern.format(secondsRemaining)));
        float width = renderState.getTextWidth(component);
        float height = renderState.getLineHeight();
        BlockBreakHudWidgetConfig.DisplayType display = ((BlockBreakHudWidgetConfig) this.config).display().get();
        BlockBreakHudWidgetConfig.DisplayType gradientDisplay = ((BlockBreakHudWidgetConfig) this.config).gradient().applyTo().get();
        int gradientColor = getGradientColor(progress);
        if (phase.canRender() && display.isText()) {
            if (gradientDisplay.isText() && gradientColor != -1) {
                i2 = gradientColor;
            } else {
                i2 = ((BlockBreakHudWidgetConfig) this.config).color().get().get();
            }
            renderState.submitComponent(component, 0.0f, 0.0f, i2, 1);
        }
        if (display.isProgressBar()) {
            if (phase.canRender()) {
                float progressY = display.isText() ? height : height / 2.0f;
                float f = width * progress;
                float f2 = progressY + 1.0f;
                if (gradientDisplay.isProgressBar() && gradientColor != -1) {
                    i = gradientColor;
                } else {
                    i = ((BlockBreakHudWidgetConfig) this.config).progressBarColor().get().get();
                }
                renderState.submitAbsoluteRect(0.0f, progressY, f, f2, i);
            }
            if (display.isText()) {
                height += 2.0f;
            }
        }
        if (isEditorContext || blockState != null || (size.getActualWidth() == 0.0f && size.getActualHeight() == 0.0f)) {
            size.set(width, height);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return getDestroyingBlockState() != null;
    }

    private BlockState getDestroyingBlockState() {
        BlockState blockState;
        if (this.labyAPI.minecraft().getDestroyProgress() != 0.0f) {
            HitResult hitResult = this.labyAPI.minecraft().getHitResult();
            if (hitResult instanceof BlockHitResult) {
                BlockHitResult hitResult2 = (BlockHitResult) hitResult;
                if (hitResult2.type() == HitResult.HitType.BLOCK && (blockState = this.labyAPI.minecraft().clientWorld().getBlockState(hitResult2.getBlockPosition())) != null && !blockState.block().isAir()) {
                    return blockState;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private int getGradientColor(float progress) {
        Color color;
        Color color2;
        float f;
        if (((BlockBreakHudWidgetConfig) this.config).gradient().enabled().get().booleanValue()) {
            if (progress < 0.5f) {
                color = ((BlockBreakHudWidgetConfig) this.config).gradient().startColor().get();
            } else {
                color = ((BlockBreakHudWidgetConfig) this.config).gradient().middleColor().get();
            }
            Color first = color;
            if (progress < 0.5f) {
                color2 = ((BlockBreakHudWidgetConfig) this.config).gradient().middleColor().get();
            } else {
                color2 = ((BlockBreakHudWidgetConfig) this.config).gradient().endColor().get();
            }
            Color second = color2;
            CubicBezier bezier = CubicBezier.EASE_IN_OUT;
            if (progress < 0.5f) {
                f = progress * 2.0f;
            } else {
                f = (progress - 0.5f) * 2.0f;
            }
            float interpolateProgress = f;
            return Color.ofRGB((int) (((double) first.getRed()) + (((double) (second.getRed() - first.getRed())) * bezier.curve(interpolateProgress))), (int) (((double) first.getGreen()) + (((double) (second.getGreen() - first.getGreen())) * bezier.curve(interpolateProgress))), (int) (((double) first.getBlue()) + (((double) (second.getBlue() - first.getBlue())) * bezier.curve(interpolateProgress)))).get();
        }
        return -1;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BlockBreakHudWidget$BlockBreakHudWidgetConfig.class */
    public static class BlockBreakHudWidgetConfig extends HudWidgetConfig {

        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> color = new ConfigProperty<>(Color.WHITE);

        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("progressBar")
        private final ConfigProperty<Color> progressBarColor = new ConfigProperty<>(Color.LIGHT_GRAY);
        private final GradientConfig gradient = new GradientConfig();

        @SliderWidget.SliderSetting(min = 0.0f, max = 2.0f, steps = 1.0f)
        private final ConfigProperty<Integer> precision = new ConfigProperty<>(1);

        @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.block_break.display.entries")
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DisplayType> display = ConfigProperty.createEnum(DisplayType.TEXT_AND_PROGRESS_BAR);

        public ConfigProperty<Color> color() {
            return this.color;
        }

        public ConfigProperty<Color> progressBarColor() {
            return this.progressBarColor;
        }

        public GradientConfig gradient() {
            return this.gradient;
        }

        public ConfigProperty<Integer> precision() {
            return this.precision;
        }

        public ConfigProperty<DisplayType> display() {
            return this.display;
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BlockBreakHudWidget$BlockBreakHudWidgetConfig$DisplayType.class */
        public enum DisplayType {
            TEXT,
            PROGRESS_BAR,
            TEXT_AND_PROGRESS_BAR;

            public boolean isText() {
                return this == TEXT || this == TEXT_AND_PROGRESS_BAR;
            }

            public boolean isProgressBar() {
                return this == PROGRESS_BAR || this == TEXT_AND_PROGRESS_BAR;
            }
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/BlockBreakHudWidget$BlockBreakHudWidgetConfig$GradientConfig.class */
        @SettingRequires("enabled")
        public static class GradientConfig extends Config {

            @SwitchWidget.SwitchSetting
            @ShowSettingInParent
            private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> startColor = new ConfigProperty<>(Color.GREEN);

            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> middleColor = new ConfigProperty<>(Color.YELLOW);

            @ColorPickerWidget.ColorPickerSetting
            private final ConfigProperty<Color> endColor = new ConfigProperty<>(Color.of(NamedTextColor.RED.getValue(), 255));

            @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.block_break.display.entries")
            @DropdownWidget.DropdownSetting
            private final ConfigProperty<DisplayType> applyTo = ConfigProperty.createEnum(DisplayType.PROGRESS_BAR);

            public ConfigProperty<Boolean> enabled() {
                return this.enabled;
            }

            public ConfigProperty<Color> startColor() {
                return this.startColor;
            }

            public ConfigProperty<Color> middleColor() {
                return this.middleColor;
            }

            public ConfigProperty<Color> endColor() {
                return this.endColor;
            }

            public ConfigProperty<DisplayType> applyTo() {
                return this.applyTo;
            }
        }
    }
}

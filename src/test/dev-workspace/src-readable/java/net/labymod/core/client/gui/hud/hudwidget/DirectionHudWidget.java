package net.labymod.core.client.gui.hud.hudwidget;

import java.util.Map;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.world.object.WorldObjectOverlay;
import net.labymod.core.flint.FlintUrls;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/DirectionHudWidget.class */
@SpriteSlot(x = 2, y = 4)
@IntroducedIn("4.1.0")
public class DirectionHudWidget extends ResizeableHudWidget<DirectionHudWidgetConfig> {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/DirectionHudWidget$DesignType.class */
    public enum DesignType {
        MINECRAFT,
        PUPG,
        COD,
        CSGO,
        GEO
    }

    public DirectionHudWidget() {
        super(FlintUrls.QUERY_DIRECTION_PARAM, DirectionHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.DIRECTION);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget
    public void render(@NotNull ScreenContext context, boolean isEditorContext, float width, float height) {
        Entity entity = this.labyAPI.minecraft().getCameraEntity();
        float degree = entity == null ? 35.0f : MathHelper.wrapDegrees(entity.getRotationYaw());
        if (degree <= 0.0f) {
            degree += 360.0f;
        }
        switch (((DirectionHudWidgetConfig) this.config).design().get()) {
            case MINECRAFT:
                renderMinecraft(context, degree);
                break;
            case PUPG:
                renderPUPG(context, degree);
                break;
            case COD:
                renderCOD(context, degree);
                break;
            case CSGO:
                renderCSGO(context, degree);
                break;
            case GEO:
                renderGeo(context, degree);
                break;
        }
    }

    private void renderMinecraft(ScreenContext context, float degree) {
        Scissor scissor = context.canvas().scissor();
        try {
            scissor.push(0.0f, 0.0f, this.width, this.height);
            float centerX = this.width / 2.0f;
            float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
            int accentColor = ((DirectionHudWidgetConfig) this.config).accentColor().get().get();
            ScreenCanvas renderState = context.canvas();
            renderState.submitRelativeRect(0.0f, 0.0f, this.width, this.height, -14606047);
            for (int i = 0; i < this.height / 4.0f; i++) {
                renderState.submitRelativeRect(centerX - 0.5f, (this.height - (i * 4)) - 3.0f, 1.0f, 3.0f, accentColor);
            }
            for (int layer = 0; layer < 2; layer++) {
                for (float rot = 0.0f; rot < 360.0f; rot = (float) (((double) rot) + 11.25d)) {
                    float offset = ((rot - degree) + (layer * 360 * (degree < 180.0f ? -1 : 1))) * spacing;
                    float posX = centerX + offset;
                    if (posX >= (-10) && posX <= this.width + 10) {
                        boolean isCardinalPoint = rot % 45.0f == 0.0f;
                        boolean isMiddleOfCardinal = ((double) rot) % 22.5d == 0.0d;
                        if (isCardinalPoint) {
                            renderText(context, toCardinal(rot), posX + 1.0f, (this.height / 2.0f) - 1.0f, 1.0f, -1, HorizontalAlignment.CENTER);
                        }
                        renderState.submitRelativeRect(posX - 0.5f, 0.0f, 1.0f, isCardinalPoint ? 4.0f : isMiddleOfCardinal ? 2.0f : 1.0f, -3750202);
                    }
                }
            }
            renderWorldObjects(context, degree, false, 0.0f, 0);
            scissor.pop();
        } catch (Throwable th) {
            scissor.pop();
            throw th;
        }
    }

    private void renderPUPG(ScreenContext context, float degree) {
        this.labyAPI.renderPipeline();
        float centerX = this.width / 2.0f;
        float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
        int accentColor = ((DirectionHudWidgetConfig) this.config).accentColor().get().get();
        int backgroundColor = ((DirectionHudWidgetConfig) this.config).primaryBackgroundColor().get().get();
        ScreenCanvas renderState = context.canvas();
        float fadeWidth = this.width / 8.0f;
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, 0.0f, 0.0f, fadeWidth, this.height, backgroundColor & 16777215, backgroundColor);
        renderState.submitRelativeRect(fadeWidth, 0.0f, this.width - (fadeWidth * 2.0f), this.height, backgroundColor);
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, this.width - fadeWidth, 0.0f, fadeWidth, this.height, backgroundColor, backgroundColor & 16777215);
        for (int layer = 0; layer < 2; layer++) {
            for (int rot = 0; rot < 360; rot++) {
                if (rot % 5 == 0) {
                    float offset = ((rot - degree) + (layer * 360 * (degree < 180.0f ? -1 : 1))) * spacing;
                    float posX = centerX + offset;
                    if (posX >= 0.0f && posX <= this.width) {
                        boolean isCardinalPoint = rot % 45 == 0;
                        int alphaMask = computeAlphaMask(this.width, offset, 100);
                        if (Math.abs(offset) < 30.0f) {
                            float opacity = (alphaMask >>> 24) / 255.0f;
                            alphaMask = (((int) ((opacity * MathHelper.square(offset / 30.0f)) * 255.0f)) << 24) | 16777215;
                        }
                        renderText(context, isCardinalPoint ? toCardinal(rot) : String.valueOf(rot), posX + (isCardinalPoint ? 0.5f : 0.25f), (this.height / 2.0f) + 1.0f, isCardinalPoint ? 1.0f : 0.5f, alphaMask, HorizontalAlignment.CENTER);
                        renderState.submitRelativeRect(posX - (isCardinalPoint ? 0.5f : 0.25f), ((this.height / 2.0f) - ((isCardinalPoint ? 5 : 3) / 2.0f)) - 2.0f, isCardinalPoint ? 1.0f : 0.5f, isCardinalPoint ? 5.0f : 3.0f, (isCardinalPoint ? -1 : 1157627903) & alphaMask);
                    }
                }
            }
        }
        renderWorldObjects(context, degree, true, this.width, 100);
        renderText(context, String.valueOf((int) degree), centerX + 1.0f, (this.height / 2.0f) + 1.0f, 1.0f, -1, HorizontalAlignment.CENTER);
        renderState.submitTriangle(centerX + 2.0f, 1.0f, centerX - 2.0f, 1.0f, centerX, 5.0f, accentColor);
    }

    private void renderCOD(ScreenContext context, float degree) {
        float centerX = this.width / 2.0f;
        float fadeWidth = this.width / 8.0f;
        int primaryBackgroundColor = ((DirectionHudWidgetConfig) this.config).primaryBackgroundColor().get().get();
        int secondaryBackgroundColor = ((DirectionHudWidgetConfig) this.config).secondaryBackgroundColor().get().get();
        int accentColor = ((DirectionHudWidgetConfig) this.config).accentColor().get().get();
        float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeGradientRect(GradientDirection.TOP_TO_BOTTOM, fadeWidth, 0.0f, this.width - (fadeWidth * 2.0f), this.height - 8.0f, secondaryBackgroundColor, primaryBackgroundColor);
        renderState.submitRelativeGradientRect(GradientDirection.TOP_TO_BOTTOM, centerX - 0.5f, 0.0f, 1.0f, this.height, accentColor & 150994943, accentColor);
        float shadeWidth = fadeWidth / 40;
        int alpha = primaryBackgroundColor >>> 24;
        int rgb = primaryBackgroundColor & 16777215;
        for (int i = 0; i < 40; i++) {
            float opacity = i / 40;
            int color = (((int) (opacity * alpha)) << 24) | rgb;
            renderState.submitRelativeGradientRect(GradientDirection.TOP_TO_BOTTOM, i * shadeWidth, 0.0f, shadeWidth, this.height - 8.0f, secondaryBackgroundColor, color);
            renderState.submitRelativeGradientRect(GradientDirection.TOP_TO_BOTTOM, this.width - ((i + 1) * shadeWidth), 0.0f, shadeWidth, this.height - 8.0f, secondaryBackgroundColor, color);
        }
        for (int layer = 0; layer < 2; layer++) {
            for (int rot = 0; rot < 360; rot++) {
                float layerShift = layer * 360 * (degree < 180.0f ? -1 : 1);
                float offset = ((rot - degree) + layerShift) * spacing;
                float posX = centerX + offset;
                if (posX >= 0.0f && posX <= this.width) {
                    boolean isCardinalPoint = rot % 45 == 0;
                    boolean isLabeled = rot % 15 == 0;
                    int alphaMask = computeAlphaMask(this.width, offset, 100);
                    renderState.submitRelativeRect(posX - 0.5f, (this.height - 8.0f) - (isLabeled ? 3 : 1), 1.0f, isLabeled ? 3.0f : 1.0f, alphaMask & (-8947849));
                    if (isLabeled) {
                        renderText(context, isCardinalPoint ? toCardinal(rot) : String.valueOf(rot), (posX - 0.5f) + (isCardinalPoint ? 0.7f : 1.0f), (this.height - 15.0f) - (isCardinalPoint ? 2 : 1), isCardinalPoint ? 0.7f : 0.5f, alphaMask, HorizontalAlignment.CENTER);
                    }
                }
            }
        }
        renderWorldObjects(context, degree, true, this.width, 100);
        renderText(context, toCardinal(degree), centerX - 1.0f, this.height - 7.0f, 1.0f, accentColor, HorizontalAlignment.RIGHT);
        renderText(context, String.valueOf((int) degree), centerX + 2.0f, this.height - 7.0f, 1.0f, accentColor, HorizontalAlignment.LEFT);
    }

    private void renderCSGO(ScreenContext context, float degree) {
        float centerX = this.width / 2.0f;
        int primaryBackgroundColor = ((DirectionHudWidgetConfig) this.config).primaryBackgroundColor().get().get();
        int secondaryBackgroundColor = ((DirectionHudWidgetConfig) this.config).secondaryBackgroundColor().get().get();
        int accentColor = ((DirectionHudWidgetConfig) this.config).accentColor().get().get();
        float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, 0.0f, 0.0f, this.width / 2.0f, this.height - 10.0f, secondaryBackgroundColor, primaryBackgroundColor);
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, this.width / 2.0f, 0.0f, this.width / 2.0f, this.height - 10.0f, primaryBackgroundColor, secondaryBackgroundColor);
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, 0.0f, this.height - 10.0f, this.width / 2.0f, 0.5f, secondaryBackgroundColor, 1157627903);
        renderState.submitRelativeGradientRect(GradientDirection.LEFT_TO_RIGHT, this.width / 2.0f, this.height - 10.0f, this.width / 2.0f, 0.5f, 1157627903, secondaryBackgroundColor);
        renderState.submitRelativeGradientRect(GradientDirection.TOP_TO_BOTTOM, centerX - 0.25f, 0.0f, 0.5f, this.height, accentColor & 150994943, accentColor);
        for (int layer = 0; layer < 2; layer++) {
            for (int rot = 0; rot < 360; rot++) {
                float layerShift = layer * 360 * (degree < 180.0f ? -1 : 1);
                float offset = ((rot - degree) + layerShift) * spacing;
                float posX = centerX + offset;
                if (posX >= 0.0f && posX <= this.width) {
                    boolean isCardinalPoint = rot % 45 == 0;
                    boolean isLabeled = rot % 10 == 0;
                    boolean isTick = isLabeled || isCardinalPoint;
                    boolean isCompact = this.height < 20.0f;
                    int alphaMask = computeAlphaMask(this.width, offset, 100);
                    float tickHeight = isLabeled ? isCardinalPoint ? 2 : 3 : 1;
                    renderState.submitRelativeRect(posX - 0.25f, (this.height - 10.0f) - (tickHeight * (isCompact ? 0 : 1)), 0.5f, tickHeight, (-4473925) & alphaMask);
                    if (isTick) {
                        renderText(context, String.valueOf(rot), posX + 0.25f, (this.height - 18.0f) + (isCardinalPoint ? 2 : 0) + (isCompact ? 13 : 0), 0.5f, (-1118482) & alphaMask, HorizontalAlignment.CENTER);
                        if (isCardinalPoint) {
                            renderText(context, toCardinal(rot), posX + 0.25f, (this.height - 20.0f) + (isCompact ? 13 : 0), 0.5f, (-1118482) & alphaMask, HorizontalAlignment.CENTER);
                        }
                    }
                }
            }
        }
        renderWorldObjects(context, degree, true, this.width, 100);
        renderText(context, ((int) degree) + "°", centerX - 3.0f, this.height - 7.0f, 0.75f, accentColor, HorizontalAlignment.RIGHT);
        renderText(context, toCardinal(degree), centerX + 3.0f, this.height - 7.0f, 0.75f, accentColor, HorizontalAlignment.LEFT);
    }

    private void renderGeo(ScreenContext context, float degree) {
        this.labyAPI.renderPipeline();
        float centerX = this.width / 2.0f;
        float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
        int accentColor = ((DirectionHudWidgetConfig) this.config).accentColor().get().get();
        int backgroundColorSide = ((DirectionHudWidgetConfig) this.config).primaryBackgroundColor().get().get();
        int backgroundColorCenter = ((DirectionHudWidgetConfig) this.config).secondaryBackgroundColor().get().get();
        int sideAlpha = (backgroundColorSide >> 24) & 255;
        int centerAlpha = (backgroundColorCenter >> 24) & 255;
        int alpha = MathHelper.clamp(centerAlpha, sideAlpha / 2, 255);
        int backgroundColorCenter2 = (backgroundColorCenter & 16777215) | ((alpha << 24) & (-16777216));
        ScreenCanvas renderState = context.canvas();
        renderState.submitGuiRect(0.0f, 0.0f, this.width / 2.0f, this.height, GuiRectangleRenderState.RectConfig.builder().setRoundedData(RoundedData.builder().setLeftTopRadius(5.0f).setLeftBottomRadius(5.0f).setLowerEdgeSoftness(-0.125f).setUpperEdgeSoftness(0.5f).build()).setGradient(GradientDirection.RIGHT_TO_LEFT, backgroundColorCenter2, backgroundColorSide).build());
        renderState.submitGuiRect(this.width / 2.0f, 0.0f, this.width / 2.0f, this.height, GuiRectangleRenderState.RectConfig.builder().setRoundedData(RoundedData.builder().setRightTopRadius(5.0f).setRightBottomRadius(5.0f).setLowerEdgeSoftness(-0.125f).setUpperEdgeSoftness(0.5f).build()).setGradient(GradientDirection.LEFT_TO_RIGHT, backgroundColorCenter2, backgroundColorSide).build());
        for (int layer = 0; layer < 2; layer++) {
            for (int rot = 0; rot < 360; rot++) {
                if (rot % 5 == 0) {
                    float offset = ((rot - degree) + (layer * 360 * (degree < 180.0f ? -1 : 1))) * spacing;
                    float posX = centerX + offset;
                    if (posX >= 0.0f && posX <= this.width) {
                        boolean isCardinalPoint = rot % 45 == 0;
                        int alphaMask = computeAlphaMask(this.width - 15.0f, offset, 4);
                        if (isCardinalPoint) {
                            renderText(context, toCardinal(rot), posX + 0.5f, (this.height / 2.0f) - 3.0f, 0.8f, accentColor & alphaMask, HorizontalAlignment.CENTER);
                        } else {
                            boolean isNextToCardinal = ((rot - 5) % 45 == 0 || (rot + 5) % 45 == 0) ? false : true;
                            if (isNextToCardinal || spacing >= 2.0f) {
                                renderState.submitRelativeRect(posX - 0.5f, this.height / 4.0f, 1.0f, this.height / 2.0f, 1157627903 & alphaMask);
                            }
                        }
                    }
                }
            }
        }
        renderWorldObjects(context, degree, true, this.width - 15.0f, 4);
        renderState.submitTriangle(centerX + 1.0f, 0.0f, centerX - 1.0f, 0.0f, centerX, 4.0f, 1157627903);
        renderState.submitTriangle(centerX - 1.0f, this.height, centerX + 1.0f, this.height, centerX, this.height - 4.0f, 1157627903);
    }

    private void renderText(ScreenContext context, String text, float x, float y, float scale, int color, HorizontalAlignment alignment) {
        ScreenCanvas renderState = context.canvas();
        float textWidth = alignment == HorizontalAlignment.LEFT ? 0.0f : renderState.getTextWidth(text) * scale;
        renderState.submitText(text, x - (alignment == HorizontalAlignment.RIGHT ? textWidth : alignment == HorizontalAlignment.CENTER ? textWidth / 2.0f : 0.0f), y, color, scale, 4);
    }

    private void renderWorldObjects(ScreenContext context, float degree, boolean hasOpacity, float opacityWidth, int opacityTransitionRadius) {
        float fComputeAlphaMask;
        if (!((DirectionHudWidgetConfig) this.config).showWorldObjectIndicators().get().booleanValue()) {
            return;
        }
        IngameOverlayActivity overlay = this.labyAPI.ingameOverlay().getActivity(WorldObjectOverlay.class).orElse(null);
        if (!(overlay instanceof WorldObjectOverlay)) {
            return;
        }
        WorldObjectOverlay worldObjectOverlay = (WorldObjectOverlay) overlay;
        Minecraft minecraft = this.labyAPI.minecraft();
        Entity cam = minecraft.getCameraEntity();
        if (cam == null) {
            return;
        }
        float centerX = this.width / 2.0f;
        float spacing = ((DirectionHudWidgetConfig) this.config).spacing().get().floatValue();
        for (Map.Entry<WorldObject, Widget> entry : worldObjectOverlay.getObjects().entrySet()) {
            WorldObject object = entry.getKey();
            Widget widget = entry.getValue();
            float yaw = MathHelper.convertDegrees(degree, worldObjectOverlay.getYaw(cam, object));
            float offset = (yaw - degree) * spacing;
            float posX = centerX + offset;
            if (posX >= 0.0f && posX <= this.width) {
                context.pushStack();
                float prevOpacity = this.labyAPI.renderPipeline().getAlpha();
                boolean prevVisible = widget.isVisible();
                Bounds bounds = widget.bounds();
                context.translate(((-bounds.getX(BoundsType.OUTER)) + posX) - (bounds.getWidth(BoundsType.OUTER) / 2.0f), (-bounds.getY(BoundsType.OUTER)) + 3.0f, 0.0f);
                RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
                if (hasOpacity) {
                    fComputeAlphaMask = (computeAlphaMask(opacityWidth, offset, opacityTransitionRadius) >>> 24) / 255.0f;
                } else {
                    fComputeAlphaMask = 1.0f;
                }
                renderPipeline.setAlpha(fComputeAlphaMask);
                widget.setVisible(true);
                widget.renderWidget(context);
                this.labyAPI.renderPipeline().setAlpha(prevOpacity);
                widget.setVisible(prevVisible);
                context.popStack();
            }
        }
    }

    @NotNull
    private String toCardinal(float degree) {
        String text;
        switch (Math.round(degree / 45.0f)) {
            case 1:
                text = "SW";
                break;
            case 2:
                text = "W";
                break;
            case 3:
                text = "NW";
                break;
            case 4:
                text = "N";
                break;
            case 5:
                text = "NE";
                break;
            case 6:
                text = "E";
                break;
            case 7:
                text = "SE";
                break;
            default:
                text = "S";
                break;
        }
        return text;
    }

    private int computeAlphaMask(float width, float offset, int transitionRadius) {
        float length = width / 2.0f;
        float opacity = ((-MathHelper.square(offset)) + MathHelper.square(length)) / (transitionRadius * length);
        return (((int) (MathHelper.clamp(opacity, 0.0f, 1.0f) * 255.0f)) << 24) | 16777215;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/DirectionHudWidget$DirectionHudWidgetConfig.class */
    public static class DirectionHudWidgetConfig extends ResizeableHudWidget.ResizeableHudWidgetConfig {

        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DesignType> design;

        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> accentColor;

        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> primaryBackgroundColor;

        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> secondaryBackgroundColor;

        @SliderWidget.SliderSetting(min = 1.0f, max = 5.0f, steps = 0.1f)
        private final ConfigProperty<Float> spacing;

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showWorldObjectIndicators;

        public DirectionHudWidgetConfig() {
            super(50.0f, 200.0f, 400.0f, 12.0f, 20.0f, 25.0f);
            this.design = new ConfigProperty<>(DesignType.PUPG);
            this.accentColor = new ConfigProperty<>(Color.of(-1));
            this.primaryBackgroundColor = new ConfigProperty<>(Color.of(Integer.MIN_VALUE));
            this.secondaryBackgroundColor = new ConfigProperty<>(Color.of(0));
            this.spacing = new ConfigProperty<>(Float.valueOf(5.0f));
            this.showWorldObjectIndicators = new ConfigProperty<>(true);
        }

        @NotNull
        public ConfigProperty<DesignType> design() {
            return this.design;
        }

        @NotNull
        public ConfigProperty<Color> accentColor() {
            return this.accentColor;
        }

        @NotNull
        public ConfigProperty<Color> primaryBackgroundColor() {
            return this.primaryBackgroundColor;
        }

        @NotNull
        public ConfigProperty<Color> secondaryBackgroundColor() {
            return this.secondaryBackgroundColor;
        }

        @NotNull
        public ConfigProperty<Float> spacing() {
            return this.spacing;
        }

        @NotNull
        public ConfigProperty<Boolean> showWorldObjectIndicators() {
            return this.showWorldObjectIndicators;
        }
    }
}

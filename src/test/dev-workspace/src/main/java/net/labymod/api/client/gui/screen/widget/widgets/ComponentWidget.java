package net.labymod.api.client.gui.screen.widget.widgets;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/ComponentWidget.class */
@AutoWidget
public class ComponentWidget extends SimpleWidget {
    private static final int VISUAL_SHIFT = 1;
    private Component component;
    private RenderableComponent renderableComponent;
    private ComponentRenderMeta renderMeta;
    private ComponentRenderMeta lastRenderMeta;
    private final LssProperty<TextOverflowStrategy> overflowStrategy = new LssProperty<>(TextOverflowStrategy.WRAP);
    private final LssProperty<Boolean> renderHover = new LssProperty<>(true);
    private final LssProperty<Integer> textColor = new LssProperty<>(-1);
    private final LssProperty<Integer> iconColor = new LssProperty<>(-1);
    private final LssProperty<Boolean> allowColors = new LssProperty<>(true);
    private final LssProperty<Boolean> shadow = new LssProperty<>(true);
    private final LssProperty<Float> lineSpacing = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<FontSize> fontSize = new LssProperty<>(FontSize.predefined(FontSize.PredefinedFontSize.MEDIUM));
    private final LssProperty<Boolean> scaleToFit = new LssProperty<>(false);
    private final LssProperty<Boolean> cache = new LssProperty<>(true);
    private final LssProperty<Integer> maxLines = new LssProperty<>(0);
    private final LssProperty<Boolean> maxLinesClipText = new LssProperty<>(true);
    private final LssProperty<Boolean> leadingSpaces = new LssProperty<>(false);
    private final LssProperty<Boolean> useChatOptions = new LssProperty<>(false);
    private final LssProperty<Boolean> clippingTextTooltip = new LssProperty<>(true);
    private final LssProperty<Boolean> visualShift = new LssProperty<>(true);
    private final LssProperty<Long> textColorTransitionDuration = new LssProperty<>(0L);

    private ComponentWidget(Component component, float lineSpacing) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }
        this.component = component;
        this.lineSpacing.set(Float.valueOf(lineSpacing));
        setSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.fitContent());
        setSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.fitContent());
        scaleX().addChangeListener((type, oldValue, newValue) -> {
            if (!Objects.equals(newValue, scaleX().defaultValue())) {
                throw new LssPropertyException("Cannot set scale-x (scale) property of a ComponentWidget, use font-size instead");
            }
        });
        scaleY().addChangeListener((type2, oldValue2, newValue2) -> {
            if (!Objects.equals(newValue2, scaleY().defaultValue())) {
                throw new LssPropertyException("Cannot set scale-y (scale) property of a ComponentWidget, use font-size instead");
            }
        });
        updateComponent(true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (!newRect.isRoundedSizeEqual(previousRect)) {
            updateComponent(true);
            handleScaleToFit();
        }
    }

    public void setText(String text) {
        setComponent(Component.text(text));
    }

    public void setComponent(Component component) {
        this.component = component;
        updateComponent(true);
        handleAttributes();
        updateBounds();
    }

    public void updateComponent() {
        updateComponent(true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void handleAttributes() {
        super.handleAttributes();
        updateComponent(false);
        handleScaleToFit();
    }

    private void updateComponent(boolean force) {
        if (!force && this.renderableComponent != null && !fontSize().wasUpdatedThisFrame() && !lineSpacing().wasUpdatedThisFrame() && !overflowStrategy().wasUpdatedThisFrame() && !sizes().wasUpdatedThisFrame() && !maxLines().wasUpdatedThisFrame() && !leadingSpaces().wasUpdatedThisFrame() && !maxLinesClipText().wasUpdatedThisFrame() && !bounds().wasUpdatedThisFrame()) {
            return;
        }
        this.renderableComponent = createRenderableComponent();
        super.handleAttributes();
    }

    private RenderableComponent createRenderableComponent() {
        boolean forceVanillaFont = super.forceVanillaFont().get().booleanValue();
        RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
        RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(forceVanillaFont);
        attributes.apply();
        float fontSize = this.fontSize.get().getSize();
        Float maxWidth = predictWidth();
        RenderableComponent component = RenderableComponent.builder().maxWidth(maxWidth != null ? maxWidth.floatValue() / fontSize : -1.0f).alignment(HorizontalAlignment.of(alignmentX().get())).lineSpacing(this.lineSpacing.get().floatValue()).overflow(this.overflowStrategy.get()).cache(this.cache.get().booleanValue()).maxLines(this.maxLines.get().intValue()).maxLinesClipText(this.maxLinesClipText.get().booleanValue()).useChatOptions(this.useChatOptions.get().booleanValue()).format(this.component);
        renderAttributesStack.pop();
        return component;
    }

    private void handleScaleToFit() {
        if (this.scaleToFit.get().booleanValue() && this.renderableComponent != null) {
            if (!hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT) || !hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
                float fontSize = this.fontSize.get().getSize();
                float componentWidth = this.renderableComponent.getWidth() * fontSize;
                float componentHeight = this.renderableComponent.getHeight() * fontSize;
                Bounds parentBounds = super.getParent().bounds();
                if (!parentBounds.hasSize()) {
                    return;
                }
                float parentWidth = parentBounds.getWidth(BoundsType.INNER);
                float parentHeight = parentBounds.getHeight(BoundsType.INNER);
                float newFontSizeWidth = 1.0f;
                float newFontSizeHeight = 1.0f;
                if (parentWidth != 0.0f && componentWidth > parentWidth) {
                    newFontSizeWidth = (parentWidth / componentWidth) - 0.05f;
                }
                if (parentHeight != 0.0f && componentHeight > parentHeight) {
                    newFontSizeHeight = (parentHeight / componentHeight) - 0.05f;
                }
                float newValue = Math.min(newFontSizeWidth, newFontSizeHeight);
                FontSize nearest = FontSize.nextLowerPredefined(newValue);
                this.fontSize.set(nearest != null ? nearest : FontSize.custom(newValue));
                updateComponent(true);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (this.renderableComponent == null) {
            updateComponent(true);
            handleScaleToFit();
        }
        Bounds bounds = bounds();
        float x = bounds.getX(BoundsType.INNER);
        float y = bounds.getY(BoundsType.INNER);
        float fontSize = this.fontSize.get().getSize();
        if (alignmentX().get() == WidgetAlignment.CENTER) {
            x += (bounds.getWidth(BoundsType.INNER) - (this.renderableComponent.getWidth() * fontSize)) / 2.0f;
        }
        if (alignmentX().get() == WidgetAlignment.RIGHT) {
            x += bounds.getWidth(BoundsType.INNER) - (this.renderableComponent.getWidth() * fontSize);
        }
        if (visualShift().get().booleanValue()) {
            x += 1.0f;
            y += 1.0f;
        }
        boolean forceVanillaFont = super.forceVanillaFont().get().booleanValue();
        RenderAttributesStack renderAttributesStack = RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
        RenderAttributes attributes = renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(forceVanillaFont);
        attributes.setFontWeight(getFontWeight());
        attributes.apply();
        int textArgb = ColorUtil.lerpedColor(this.textColorTransitionDuration.get().longValue(), context.getTickDelta(), this.textColor);
        this.renderMeta = Laby.references().componentRendererBuilder().useFloatingPointPosition(useFloatingPointPosition().get().booleanValue()).shadow(this.shadow.get().booleanValue()).pos(x, y).scale(fontSize).mousePos(context.mouse()).text(this.renderableComponent).allowColors(this.allowColors.get().booleanValue()).textColor(textArgb).iconColor(this.iconColor.get().intValue()).render(context);
        renderAttributesStack.pop();
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        super.renderHoverComponent(context);
        if (!this.renderHover.get().booleanValue() || this.renderMeta == null || !isHovered()) {
            this.lastRenderMeta = null;
            return;
        }
        this.renderMeta.renderHover(context.stack(), context.mouse());
        this.lastRenderMeta = this.renderMeta;
        this.renderMeta = null;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.lastRenderMeta == null || mouseButton != MouseButton.LEFT) {
            return super.mouseClicked(mouse, mouseButton);
        }
        return this.lastRenderMeta.interact() || super.mouseClicked(mouse, mouseButton);
    }

    public Component component() {
        return this.component;
    }

    public RenderableComponent renderable() {
        return this.renderableComponent;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float width = (this.renderableComponent.getWidth() * fontSize().get().getSize()) + bounds().getHorizontalOffset(type);
        if (width > 0.0f) {
            width += 1.0f;
        }
        return width;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float contentHeight = this.renderableComponent.getHeight() * this.fontSize.get().getSize();
        return contentHeight + bounds().getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    @Deprecated(forRemoval = true, since = "4.1.4")
    public LssProperty<Boolean> forceVanillaFont() {
        return super.forceVanillaFont();
    }

    public LssProperty<TextOverflowStrategy> overflowStrategy() {
        return this.overflowStrategy;
    }

    public LssProperty<Boolean> renderHover() {
        return this.renderHover;
    }

    public LssProperty<Integer> textColor() {
        return this.textColor;
    }

    public LssProperty<Integer> iconColor() {
        return this.iconColor;
    }

    public LssProperty<Boolean> allowColors() {
        return this.allowColors;
    }

    public LssProperty<Boolean> shadow() {
        return this.shadow;
    }

    public LssProperty<Float> lineSpacing() {
        return this.lineSpacing;
    }

    public LssProperty<FontSize> fontSize() {
        return this.fontSize;
    }

    public LssProperty<Boolean> scaleToFit() {
        return this.scaleToFit;
    }

    public LssProperty<Boolean> cache() {
        return this.cache;
    }

    public LssProperty<Integer> maxLines() {
        return this.maxLines;
    }

    public LssProperty<Boolean> leadingSpaces() {
        return this.leadingSpaces;
    }

    public LssProperty<Boolean> useChatOptions() {
        return this.useChatOptions;
    }

    public LssProperty<Boolean> clippingTextTooltip() {
        return this.clippingTextTooltip;
    }

    public LssProperty<Boolean> maxLinesClipText() {
        return this.maxLinesClipText;
    }

    public LssProperty<Boolean> visualShift() {
        return this.visualShift;
    }

    public LssProperty<Long> textColorTransitionDuration() {
        return this.textColorTransitionDuration;
    }

    public static ComponentWidget text(String text) {
        return component(Component.text(text == null ? "" : text), 0.0f);
    }

    public static ComponentWidget text(String text, TextColor textColor) {
        return component(Component.text(text == null ? "" : text, textColor), 0.0f);
    }

    public static ComponentWidget text(String text, Style style) {
        return component(Component.text(text, style));
    }

    public static ComponentWidget empty() {
        return new ComponentWidget(Component.empty(), 0.0f);
    }

    public static ComponentWidget i18n(String key) {
        return component(Component.translatable(key, new Component[0]), 0.0f);
    }

    public static ComponentWidget i18n(String key, Object... args) {
        Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, arguments), 0.0f);
    }

    public static ComponentWidget i18n(String key, TextColor textColor, Object... args) {
        Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, textColor, arguments));
    }

    public static ComponentWidget i18n(String key, Style style, Object... args) {
        Component[] arguments = new Component[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = Component.text(String.valueOf(args[i]));
        }
        return component(Component.translatable(key, style, arguments));
    }

    public static ComponentWidget i18n(String key, TextColor textColor) {
        return component(Component.translatable(key, textColor), 0.0f);
    }

    public static ComponentWidget component(Component component) {
        return component(component, 0.0f);
    }

    public static ComponentWidget component(Component component, float lineSpacing) {
        return new ComponentWidget(component, lineSpacing);
    }
}

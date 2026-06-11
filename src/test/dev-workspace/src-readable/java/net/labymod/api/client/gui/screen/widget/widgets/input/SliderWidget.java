package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.action.SliderInteraction;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget.class */
@AutoWidget
@SettingWidget
public class SliderWidget extends SimpleWidget {
    public static final int STATE_WIDTH = 8;
    private static final boolean SUPPORTS_SCROLLING = MinecraftVersions.V1_19_4.orNewer();
    private SliderInteraction interaction;
    private float steps;
    private int decimals;
    private float min;
    private float max;
    private float value;
    private ComponentWidget sliderText;
    private Formatter<Component> formatter;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget$Formatter.class */
    @FunctionalInterface
    public interface Formatter<T> {
        T format(float f);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget$SliderSetting.class */
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SliderSetting {
        float steps() default 1.0f;

        float min();

        float max();
    }

    public SliderWidget() {
        this(SliderInteraction.NOOP);
    }

    public SliderWidget(SliderInteraction interaction) {
        this(1.0f, interaction);
    }

    public SliderWidget(float steps, SliderInteraction interaction) {
        this.steps = 1.0f;
        this.min = 0.0f;
        this.max = 100.0f;
        this.formatter = value -> {
            return Component.text(String.format(Locale.ROOT, "%." + this.decimals + "f", Float.valueOf(value)));
        };
        steps(steps);
        this.decimals = (steps < 1.0f || steps != ((float) ((int) steps))) ? new BigDecimal(String.valueOf(steps)).scale() : 0;
        this.interaction = interaction;
        setHoverCursor(CursorTypes.RESIZE_EW, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.sliderText = ComponentWidget.component(component());
        this.sliderText.addId("slider-text");
        super.addChild(this.sliderText);
        setStencil(SUPPORTS_SCROLLING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Slider";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    public SliderWidget range(float min, float max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public SliderWidget steps(float steps) {
        if (steps < 0.0f) {
            throw new RuntimeException("Steps value of slider cannot be less than zero! (" + steps + ")");
        }
        this.steps = steps;
        return this;
    }

    public SliderWidget withFormatter(Supplier<Component> formatter) {
        return withFormatter(value -> {
            return (Component) formatter.get();
        });
    }

    public SliderWidget withFormatter(Formatter<Component> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Deprecated(forRemoval = true, since = "4.1.11")
    public SliderWidget formatter(Function<Float, RenderableComponent> formatter) {
        return withFormatter(value -> {
            RenderableComponent apply = (RenderableComponent) formatter.apply(Float.valueOf(value));
            return apply.toComponent();
        });
    }

    public float getPercentage() {
        float max = this.max - this.min;
        float value = this.value - this.min;
        return (1.0f / max) * value;
    }

    public void setPercentage(float percentage) {
        setValue(this.min + ((this.max - this.min) * percentage));
    }

    public float getValue() {
        return this.value;
    }

    public Component component() {
        return this.formatter.format(this.value);
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public void setValue(double value) {
        setValue(value, true);
    }

    public void setValue(double value, boolean notify) {
        float newValue = (float) Math.min(this.max, Math.max(this.min, value));
        if (this.steps > 0.0f) {
            newValue = Math.round(newValue / this.steps) * this.steps;
        }
        if (Math.abs(newValue - this.value) >= this.steps - 0.001f) {
            this.value = newValue;
            if (notify) {
                this.interaction.updateValue(this.value);
            }
        }
        if (this.sliderText != null) {
            this.sliderText.setComponent(component());
        }
    }

    public void setInteraction(SliderInteraction interaction) {
        this.interaction = (SliderInteraction) Objects.requireNonNullElse(interaction, SliderInteraction.NOOP);
    }

    public Formatter<Component> formatter() {
        return this.formatter;
    }

    @Deprecated(forRemoval = true, since = "4.1.11")
    public Function<Float, RenderableComponent> getFormatter() {
        return value -> {
            Component component = this.formatter.format(value.floatValue());
            return RenderableComponent.of(component, false);
        };
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (isDragging()) {
            Bounds bounds = bounds();
            float left = bounds.getX(BoundsType.MIDDLE);
            float offsetX = (((float) context.mouse().getXDouble()) - left) - 4.0f;
            float percentage = (1.0f / (bounds.getWidth(BoundsType.MIDDLE) - 8.0f)) * offsetX;
            setPercentage(percentage);
        }
        if (SUPPORTS_SCROLLING) {
            updateScrollingString();
        }
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return isHovered() || super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void setDragging(boolean dragging) {
        super.setDragging(dragging);
        setHoverCursor(dragging ? CursorTypes.RESIZE_EW : CursorTypes.POINTING_HAND);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 50.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 20.0f;
    }

    private void updateScrollingString() {
        RenderableComponent renderable;
        if (this.sliderText == null || (renderable = this.sliderText.renderable()) == null) {
            return;
        }
        float fontSize = this.sliderText.fontSize().get().getSize();
        float componentWidth = renderable.getWidth() * fontSize;
        float buttonWidth = bounds().getWidth(BoundsType.MIDDLE) - 4.0f;
        float offset = ButtonWidget.getTextScrollingOffset(componentWidth, buttonWidth);
        this.sliderText.translateX().set(Float.valueOf(offset));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<SliderSetting, SliderWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public SliderWidget[] create(Setting setting, SliderSetting annotation, SettingAccessor accessor) {
            Class<?> type = accessor.getType();
            SliderWidget widget = new SliderWidget(annotation.steps(), value -> {
                if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                    accessor.set(Integer.valueOf((int) value));
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                    accessor.set(Float.valueOf(value));
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                    accessor.set(Double.valueOf(value));
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                    accessor.set(Short.valueOf((short) value));
                } else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                    accessor.set(Byte.valueOf((byte) value));
                } else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                    accessor.set(Long.valueOf((long) value));
                }
            });
            widget.range(annotation.min(), annotation.max());
            if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                widget.setValue(((Integer) accessor.get()).intValue());
            } else if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                widget.setValue(((Float) accessor.get()).floatValue());
            } else if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                widget.setValue(((Double) accessor.get()).doubleValue());
            } else if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                widget.setValue(((Short) accessor.get()).shortValue());
            } else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                widget.setValue(((Byte) accessor.get()).byteValue());
            } else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                widget.setValue(((Long) accessor.get()).longValue());
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                    widget.setValue(((Integer) newValue).intValue());
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                    widget.setValue(((Float) newValue).floatValue());
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                    widget.setValue(((Double) newValue).doubleValue());
                    return;
                }
                if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                    widget.setValue(((Short) newValue).shortValue());
                } else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                    widget.setValue(((Byte) newValue).byteValue());
                } else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                    widget.setValue(((Long) newValue).longValue());
                }
            });
            return new SliderWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return PrimitiveHelper.NUMBER_PRIMITIVES;
        }
    }
}

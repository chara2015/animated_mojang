package net.labymod.api.client.gui.screen.widget.widgets.input.color;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.ColorPickerOverlayWidget;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.Color;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/ColorPickerWidget.class */
@AutoWidget
@SettingWidget
@Link("color-picker.lss")
public class ColorPickerWidget extends OverlayWidget {
    private final ColorData colorData;
    private DivWidget colorPreview;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/ColorPickerWidget$ColorPickerSetting.class */
    @SettingElement
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ColorPickerSetting {
        boolean alpha() default false;

        boolean removeAlpha() default true;

        boolean chroma() default false;

        boolean chromaSpeed() default true;

        boolean removeChromaSpeed() default false;
    }

    private ColorPickerWidget(ColorData colorData) {
        super(WidgetReference.ClickRemoveStrategy.OUTSIDE, WidgetReference.KeyPressRemoveStrategy.ESCAPE, true);
        this.colorData = colorData;
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.colorPreview = new DivWidget();
        addChild(this.colorPreview);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (this.colorPreview != null) {
            this.colorPreview.backgroundColor().set(Integer.valueOf(this.colorData.getActualColor().get()));
        }
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget
    @NotNull
    protected Parent content() {
        return new ColorPickerOverlayWidget(this.colorData);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    public boolean enabledAlpha() {
        return this.colorData.enabledAlpha();
    }

    public boolean enabledChroma() {
        return this.colorData.enabledChroma();
    }

    public boolean enabledChromaSpeed() {
        return this.colorData.enabledChromaSpeed();
    }

    public ColorPickerWidget alpha(boolean alpha) {
        this.colorData.alphaEnabled(alpha);
        return this;
    }

    public ColorPickerWidget chroma(boolean chroma) {
        this.colorData.chromaEnabled(chroma);
        return this;
    }

    public ColorPickerWidget chromaSpeed(boolean chromaSpeed) {
        this.colorData.chromaSpeedEnabled(chromaSpeed);
        return this;
    }

    public ColorPickerWidget set(Color color) {
        this.colorData.set(color);
        return this;
    }

    public ColorPickerWidget addUpdateListener(Object instance, Consumer<Color> updateListener) {
        this.colorData.addUpdateListener(instance, () -> {
            updateListener.accept(this.colorData.getActualColor());
        });
        return this;
    }

    public Color value() {
        return this.colorData.getActualColor();
    }

    public static ColorPickerWidget of(Color color) {
        return new ColorPickerWidget(new ColorData(color));
    }

    public static ColorPickerWidget of(ConfigProperty<Color> property) {
        ColorPickerWidget widget = of(property.get());
        ColorData colorData = widget.colorData;
        colorData.addUpdateListener(widget, () -> {
            property.set(colorData.getActualColor());
        });
        property.addChangeListener((t, oldValue, newValue) -> {
            colorData.set(newValue);
        });
        return widget;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/ColorPickerWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<ColorPickerSetting, ColorPickerWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public ColorPickerWidget[] create(Setting setting, ColorPickerSetting annotation, SettingAccessor accessor) {
            ColorPickerWidget widget;
            Object accessorObject = accessor.get();
            if (accessorObject instanceof Color) {
                widget = ColorPickerWidget.of((ConfigProperty<Color>) accessor.property());
            } else {
                if (annotation.chroma()) {
                    throw new IllegalArgumentException("Chroma is only supported for Color objects");
                }
                widget = ColorPickerWidget.of(Color.of(((Integer) accessorObject).intValue()));
                ColorData colorData = widget.colorData;
                colorData.addUpdateListener(this, () -> {
                    accessor.set(Integer.valueOf(colorData.getColor().get()));
                });
            }
            ColorData colorData2 = widget.colorData;
            colorData2.chromaEnabled(annotation.chroma());
            colorData2.alphaEnabled(annotation.alpha());
            colorData2.removeAlpha(annotation.removeAlpha());
            colorData2.chromaSpeedEnabled(annotation.chromaSpeed());
            colorData2.removeChromaSpeed(annotation.removeChromaSpeed());
            return new ColorPickerWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{Integer.class, Integer.TYPE, Color.class};
        }
    }
}

package net.labymod.api.client.gui.hud.hudwidget;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.client.gui.hud.position.VerticalHudWidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/HudWidgetConfig.class */
@SettingOrder(-127)
public class HudWidgetConfig extends Config {
    public static final int GLOBAL_ORDER = 10;

    @SliderWidget.SliderSetting(min = 0.5f, max = 1.5f, steps = 0.0f)
    @Exclude
    @CustomTranslation("scale")
    private final ConfigProperty<Float> scale = new ConfigProperty<>(Float.valueOf(1.0f));

    @DropdownWidget.DropdownSetting
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.horizontalAlignment")
    @SettingSection(value = "alignment", translation = "labymod.hudWidget.global")
    private final ConfigProperty<HorizontalHudWidgetAlignment> horizontalAlignment = ConfigProperty.createEnum(HorizontalHudWidgetAlignment.AUTO);

    @DropdownWidget.DropdownSetting
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.horizontalOrientation")
    private final ConfigProperty<HorizontalHudWidgetAlignment> horizontalOrientation = ConfigProperty.createEnum(HorizontalHudWidgetAlignment.AUTO);

    @DropdownWidget.DropdownSetting
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.verticalOrientation")
    private final ConfigProperty<VerticalHudWidgetAlignment> verticalOrientation = ConfigProperty.createEnum(VerticalHudWidgetAlignment.AUTO);

    @SwitchWidget.SwitchSetting
    @SettingOrder(10)
    @CustomTranslation("labymod.hudWidget.useGlobal")
    @SettingSection(value = "general", translation = "labymod.hudWidget.global")
    private final ConfigProperty<Boolean> useGlobal = new ConfigProperty<>(true);

    @Exclude
    private RectangleAreaPosition areaIdentifier = RectangleAreaPosition.TOP_LEFT;

    @Exclude
    private float x;

    @Exclude
    private float y;

    @Exclude
    private boolean enabled;

    @Exclude
    private String parentId;

    @Exclude
    private String dropzoneId;

    public ConfigProperty<Boolean> useGlobal() {
        return this.useGlobal;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public RectangleAreaPosition areaIdentifier() {
        return this.areaIdentifier;
    }

    public void setAreaIdentifier(RectangleAreaPosition areaIdentifier) {
        this.areaIdentifier = areaIdentifier;
    }

    public ConfigProperty<Float> scale() {
        return this.scale;
    }

    public ConfigProperty<HorizontalHudWidgetAlignment> horizontalAlignment() {
        return this.horizontalAlignment;
    }

    public ConfigProperty<VerticalHudWidgetAlignment> verticalOrientation() {
        return this.verticalOrientation;
    }

    public ConfigProperty<HorizontalHudWidgetAlignment> horizontalOrientation() {
        return this.horizontalOrientation;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getDropzoneId() {
        return this.dropzoneId;
    }

    public void setDropzoneId(String dropzoneId) {
        this.dropzoneId = dropzoneId;
    }

    public void setDropzone(HudWidgetDropzone dropzone) {
        this.dropzoneId = dropzone.getId();
    }

    protected <T> ConfigProperty<T> property(Function<GlobalHudWidgetConfig, ConfigProperty<T>> globalFunction, ConfigProperty<T> property) {
        return (ConfigProperty) function(globalFunction, property);
    }

    protected <T extends Config> T config(Function<GlobalHudWidgetConfig, T> globalFunction, T config) {
        return (T) function(globalFunction, config);
    }

    protected <T> T function(Function<GlobalHudWidgetConfig, T> globalFunction, T object) {
        if (useGlobal().get().booleanValue()) {
            return globalFunction.apply(Laby.labyAPI().hudWidgetRegistry().globalHudWidgetConfig());
        }
        return object;
    }

    public boolean setParentToTailOfChainIn(RectangleAreaPosition areaIdentifier) {
        for (KeyValue<HudWidget<?>> element : Laby.labyAPI().hudWidgetRegistry().getElements()) {
            HudWidget<?> head = element.getValue();
            if (head.config != 0 && head.config.areaIdentifier() == areaIdentifier && head.getParent() == null) {
                HudWidget<?> tail = head.lastWidget();
                setParentId(tail.getId());
                return true;
            }
        }
        return false;
    }
}

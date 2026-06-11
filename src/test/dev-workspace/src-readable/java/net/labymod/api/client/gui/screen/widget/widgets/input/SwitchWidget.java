package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.switchable.BooleanSwitchableHandler;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.util.PrimitiveHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SwitchWidget.class */
@AutoWidget
@SettingWidget
public class SwitchWidget extends SimpleWidget {
    private static final String DEFAULT_ENABLED = "labymod.ui.switch.enabled";
    private static final String DEFAULT_DISABLED = "labymod.ui.switch.disabled";
    private final Switchable switchable;
    private boolean value;
    private String enabledText = "";
    private String disabledText = "";
    private String enabledTranslatableKey = null;
    private String disabledTranslatableKey = null;
    private final LssProperty<Integer> textHoverColor = new LssProperty<>(Integer.valueOf(NamedTextColor.YELLOW.getValue()));

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SwitchWidget$SwitchSetting.class */
    @SettingElement(switchable = BooleanSwitchableHandler.class)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SwitchSetting {
        boolean hotkey() default false;
    }

    protected SwitchWidget(Switchable switchable) {
        this.switchable = switchable;
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    public static SwitchWidget create(Switchable switchable) {
        return translatable(DEFAULT_ENABLED, DEFAULT_DISABLED, switchable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Switch";
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        this.value = !this.value;
        if (this.switchable != null) {
            this.switchable.switchValue(this.value);
        }
        Laby.references().soundService().play(this.value ? SoundType.SWITCH_TOGGLE_ON : SoundType.SWITCH_TOGGLE_OFF);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHovered() && mouseButton == MouseButton.LEFT) {
            onPress();
            callActionListeners();
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return true;
    }

    public String getText() {
        return this.value ? this.enabledText : this.disabledText;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 50.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 20.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        if (this.textHoverColor.get() == null) {
            return false;
        }
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    public static SwitchWidget translatable(String enabledTranslatableKey, String disabledTranslatableKey, Switchable switchable) {
        SwitchWidget widget = new SwitchWidget(switchable);
        widget.enabledTranslatableKey = enabledTranslatableKey;
        widget.disabledTranslatableKey = disabledTranslatableKey;
        return widget;
    }

    public static SwitchWidget text(String enabledText, String disabledText, Switchable switchable) {
        SwitchWidget widget = new SwitchWidget(switchable);
        widget.enabledText = enabledText;
        widget.disabledText = disabledText;
        return widget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.enabledTranslatableKey != null) {
            String enabledTranslation = I18n.getTranslation(this.enabledTranslatableKey, new Object[0]);
            if (enabledTranslation == null) {
                this.enabledText = I18n.translate(DEFAULT_ENABLED, new Object[0]);
            } else {
                this.enabledText = enabledTranslation;
            }
        }
        if (this.disabledTranslatableKey != null) {
            String disabledTranslation = I18n.getTranslation(this.disabledTranslatableKey, new Object[0]);
            if (disabledTranslation == null) {
                this.disabledText = I18n.translate(DEFAULT_DISABLED, new Object[0]);
            } else {
                this.disabledText = disabledTranslation;
            }
        }
    }

    public LssProperty<Integer> textHoverColor() {
        return this.textHoverColor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SwitchWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<SwitchSetting, Widget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Widget[] create(Setting setting, SwitchSetting annotation, SettingAccessor accessor) {
            if (annotation.hotkey()) {
                String metaKey = setting.getId() + ".hotkey";
                SwitchWidget switchWidget = createSwitch(setting, accessor);
                KeybindWidget keybindWidget = new KeybindWidget(key -> {
                    accessor.config().configMeta().put(metaKey, key.getActualName());
                });
                if (accessor.config().hasConfigMeta(metaKey)) {
                    keybindWidget.setKeyUpdater(() -> {
                        return KeyMapper.getKey(accessor.config().configMeta().get(metaKey));
                    });
                    Key key2 = KeyMapper.getKey(accessor.config().configMeta().get(metaKey));
                    if (key2 != null) {
                        keybindWidget.key(key2);
                    }
                }
                return new Widget[]{switchWidget, keybindWidget};
            }
            SwitchWidget switchWidget2 = createSwitch(setting, accessor);
            return new SwitchWidget[]{switchWidget2};
        }

        private SwitchWidget createSwitch(Setting setting, SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            SwitchWidget widget = new SwitchWidget((v1) -> {
                r2.set(v1);
            });
            widget.enabledTranslatableKey = setting.getTranslationKey() + ".enabled";
            widget.disabledTranslatableKey = setting.getTranslationKey() + ".disabled";
            widget.setValue(((Boolean) accessor.get()).booleanValue());
            accessor.property().addChangeListener((type, oldValue, newValue) -> {
                widget.setValue((newValue instanceof Boolean) && ((Boolean) newValue).booleanValue());
            });
            return widget;
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return PrimitiveHelper.BOOLEAN;
        }
    }
}

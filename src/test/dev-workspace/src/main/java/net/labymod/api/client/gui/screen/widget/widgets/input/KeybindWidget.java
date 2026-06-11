package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/KeybindWidget.class */
@AutoWidget
@SettingWidget
public class KeybindWidget extends TextFieldWidget {
    private final Selectable<Key> selectable;
    private Supplier<Key> keyUpdater;
    private Key key = null;
    private boolean listening = false;
    private boolean acceptMouseButtons = true;
    private String lastVisibleText;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/KeybindWidget$KeyBindSetting.class */
    @SettingElement
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface KeyBindSetting {
        boolean acceptMouseButtons() default false;
    }

    public KeybindWidget(Selectable<Key> selectable) {
        this.selectable = selectable;
        this.placeholder = Component.translatable("labymod.ui.keybind.pressKey", new Component[0]);
        setHoverCursor(CursorTypes.POINTING_HAND);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (this.keyUpdater != null) {
            updateKey(this.keyUpdater.get());
        }
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        if (this.listening && !isFocused()) {
            this.listening = false;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton button) {
        if (isListening() && this.acceptMouseButtons && isHovered()) {
            updateKey(button);
            return true;
        }
        if (button == MouseButton.LEFT) {
            this.listening = isHovered();
        }
        return super.mouseClicked(mouse, button);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (isListening() && key == Key.ESCAPE) {
            return updateKey(Key.NONE);
        }
        if (isListening() && !key.isUnknown()) {
            return updateKey(key);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateKey(Key key) {
        this.key = key;
        this.selectable.select(this.key);
        setFocused(false);
        this.listening = false;
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean shouldHandleEscape() {
        return isListening();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return super.isHovered();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public boolean isCursorVisible() {
        return false;
    }

    public KeybindWidget acceptMouseButtons(boolean acceptMouseButtons) {
        this.acceptMouseButtons = acceptMouseButtons;
        return this;
    }

    public KeybindWidget key(Key key) {
        this.key = key;
        this.selectable.select(this.key);
        return this;
    }

    public Key key() {
        return this.key;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public String getVisibleText() {
        String visibleText = super.getVisibleText();
        if (this.lastVisibleText == null) {
            this.lastVisibleText = getFormattedText();
        }
        if (!this.lastVisibleText.equals(visibleText)) {
            String formattedText = getFormattedText();
            if (visibleText.equals(formattedText)) {
                setHoverComponent(null);
            } else {
                setHoverComponent(Component.text(formattedText));
            }
        }
        return visibleText;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    protected String getFormattedText() {
        if (this.key == null || this.key == Key.NONE) {
            return I18n.translate("labymod.ui.keybind.none", new Object[0]);
        }
        if (this.key instanceof MouseButton) {
            return I18n.translate("labymod.ui.keybind.mouse", this.key.getName());
        }
        return I18n.translate("labymod.ui.keybind.keyboard", this.key.getName());
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public boolean shouldDisplayPlaceHolder() {
        return isListening();
    }

    public boolean isListening() {
        return this.listening;
    }

    public void setKeyUpdater(Supplier<Key> keyUpdater) {
        this.keyUpdater = keyUpdater;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/KeybindWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<KeyBindSetting, KeybindWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public KeybindWidget[] create(Setting setting, KeyBindSetting annotation, SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            KeybindWidget widget = new KeybindWidget((v1) -> {
                r2.set(v1);
            });
            widget.acceptMouseButtons(annotation.acceptMouseButtons());
            widget.key((Key) accessor.get());
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                widget.updateKey(newValue instanceof Key ? (Key) newValue : Key.NONE);
            });
            return new KeybindWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{Key.class};
        }
    }
}

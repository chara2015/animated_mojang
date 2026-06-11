package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/MultiKeybindWidget.class */
@AutoWidget
@SettingWidget
public class MultiKeybindWidget extends TextFieldWidget {
    private final Selectable<Key[]> selectable;
    private final Set<Key> keys = new HashSet();
    private boolean listening = false;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/MultiKeybindWidget$MultiKeyBindSetting.class */
    @SettingElement
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MultiKeyBindSetting {
    }

    public MultiKeybindWidget(Selectable<Key[]> selectable) {
        this.selectable = selectable;
        this.placeholder = Component.translatable("labymod.ui.multiKeybind.pressKeys", new Component[0]);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        if (this.listening != isFocused()) {
            this.listening = isFocused();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHovered() && mouseButton == MouseButton.LEFT) {
            setFocused(true);
            this.keys.clear();
        }
        super.mouseClicked(mouse, mouseButton);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (this.listening) {
            if (key == Key.ESCAPE) {
                setFocused(false);
                this.listening = false;
                this.keys.clear();
                this.selectable.select(new Key[0]);
                return true;
            }
            CollectionHelper.removeIf(this.keys, k -> {
                return (k == Key.L_SHIFT || k == Key.R_SHIFT || k == Key.L_CONTROL || k == Key.R_CONTROL || key.isAction() != k.isAction() || key.getId() != k.getId()) ? false : true;
            });
            this.keys.add(key);
            this.selectable.select((Key[]) this.keys.toArray(new Key[0]));
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return super.isHovered() || this.listening;
    }

    public Set<Key> getKeys() {
        return this.keys;
    }

    public void setKeys(Set<Key> keys) {
        this.keys.clear();
        this.keys.addAll(keys);
        this.selectable.select((Key[]) this.keys.toArray(new Key[0]));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public boolean shouldDisplayPlaceHolder() {
        return this.listening && this.keys.isEmpty();
    }

    public boolean isListening() {
        return this.listening;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public boolean isCursorVisible() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget
    public String getVisibleText() {
        if (this.keys.isEmpty()) {
            return I18n.translate("labymod.ui.keybind.none", new Object[0]);
        }
        return Key.concat(this.keys);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean shouldHandleEscape() {
        return this.listening;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/MultiKeybindWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<MultiKeyBindSetting, MultiKeybindWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public MultiKeybindWidget[] create(Setting setting, MultiKeyBindSetting annotation, SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            MultiKeybindWidget widget = new MultiKeybindWidget((v1) -> {
                r2.set(v1);
            });
            Key[] keys = (Key[]) accessor.get();
            widget.setKeys(keys != null ? new HashSet<>(Arrays.asList(keys)) : Collections.emptySet());
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                Set<Key> newKeys = newValue instanceof Key[] ? new HashSet<>(Arrays.asList((Key[]) newValue)) : Collections.emptySet();
                widget.keys.clear();
                widget.keys.addAll(newKeys);
            });
            return new MultiKeybindWidget[]{widget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{Key[].class};
        }
    }
}

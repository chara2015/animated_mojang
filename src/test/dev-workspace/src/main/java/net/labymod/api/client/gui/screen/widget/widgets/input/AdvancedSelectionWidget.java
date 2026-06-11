package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget.class */
@AutoWidget
@SettingWidget
@Link("advanced-selection.lss")
public class AdvancedSelectionWidget<T> extends HorizontalListWidget {
    private Selectable<T> selectable;
    private SelectionEntry<T> selected;
    private String translationKeyPrefix;
    private final List<SelectionEntry<T>> entries = new ArrayList();
    private StringParser stringParser = object -> {
        if (this.translationKeyPrefix == null) {
            return Component.text(object);
        }
        String key = this.translationKeyPrefix + TextFormat.SNAKE_CASE.toCamelCase(object, true);
        return I18n.has(key) ? Component.translatable(key, new Component[0]) : Component.text(object);
    };

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget$AdvancedSelectionSetting.class */
    @SettingElement(extended = true)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AdvancedSelectionSetting {
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget$StringParser.class */
    public interface StringParser {
        Component parse(String str);
    }

    private AdvancedSelectionWidget() {
    }

    public static <T> AdvancedSelectionWidget<T> create(Selectable<T> selectable) {
        return new AdvancedSelectionWidget().selectable(selectable);
    }

    public static <T> AdvancedSelectionWidget<T> create(T defaultValue, Selectable<T> selectable) {
        AdvancedSelectionWidget<T> widget = create(selectable);
        widget.setSelected(defaultValue);
        return widget;
    }

    private static Widget createTitleWidget(StringParser stringParser, SelectionEntry<?> entry) {
        Component component;
        if (entry == null || entry.getValue() == null) {
            return ComponentWidget.empty();
        }
        Object value = entry.getValue();
        if (value instanceof Widget) {
            return (Widget) value;
        }
        if (stringParser != null && ((value instanceof String) || (value instanceof Enum))) {
            component = stringParser.parse(value.toString());
        } else if (value instanceof Component) {
            component = (Component) value;
        } else {
            component = Component.text(value.toString());
        }
        return ComponentWidget.component(component);
    }

    private static Widget createEntryWidget(StringParser stringParser, SelectionEntry<?> entry) {
        SelectionEntryWidget<?> entryWidget = (SelectionEntryWidget) new SelectionEntryWidget(entry).addId("entry");
        if (entry.thumbnailIcon() != null) {
            IconWidget thumbnailWidget = (IconWidget) new IconWidget(entry.thumbnailIcon()).addId("thumbnail");
            thumbnailWidget.addChild(new DivWidget().addId("overlay"));
            entryWidget.addChild(thumbnailWidget);
        }
        Widget titleWidget = createTitleWidget(stringParser, entry).addId("title");
        entryWidget.addChild(titleWidget);
        return entryWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        for (SelectionEntry<T> entry : this.entries) {
            super.addEntry(createEntryWidget(this.stringParser, entry));
        }
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        for (T child : this.children) {
            Widget widgetChildWidget = child.childWidget();
            if (widgetChildWidget instanceof SelectionEntryWidget) {
                SelectionEntryWidget<?> entryWidget = (SelectionEntryWidget) widgetChildWidget;
                child.setSelected(Objects.equals(entryWidget.selectionEntry(), this.selected));
            }
        }
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        for (T child : this.children) {
            if (child.childWidget() instanceof SelectionEntryWidget) {
                SelectionEntryWidget<?> entryWidget = (SelectionEntryWidget) child.childWidget();
                if (child.isHovered() && child.isInteractable()) {
                    setSelected(entryWidget.selectionEntry().getValue());
                    return true;
                }
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    public AdvancedSelectionWidget<T> selectable(Selectable<T> selectable) {
        this.selectable = selectable;
        return this;
    }

    public void add(@NotNull T value, @Nullable Icon thumbnailIcon) {
        SelectionEntry<T> entry = new SelectionEntry<>(value, thumbnailIcon);
        if (this.selected == null) {
            this.selected = entry;
        }
        this.entries.add(entry);
    }

    public void add(@NotNull T value) {
        add(value, null);
    }

    public void clear() {
        this.entries.clear();
    }

    public void setStringParser(StringParser stringParser) {
        this.stringParser = stringParser;
    }

    public List<SelectionEntry<T>> getEntries() {
        return this.entries;
    }

    public T getSelected() {
        if (this.selected == null) {
            return null;
        }
        return this.selected.getValue();
    }

    public void setSelected(T value) {
        if (value == null) {
            return;
        }
        boolean changed = false;
        this.selected = new SelectionEntry<>(value, null);
        Iterator<SelectionEntry<T>> it = this.entries.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SelectionEntry<T> entry = it.next();
            if (value.equals(entry.getValue())) {
                this.selected = entry;
                changed = true;
                break;
            }
        }
        if (!changed) {
            return;
        }
        if (this.selectable != null) {
            this.selectable.select(value);
        }
        callActionListeners();
        reInitialize();
    }

    public AdvancedSelectionWidget<T> translationKeyPrefix(String translationKey) {
        String str;
        if (translationKey == null) {
            str = null;
        } else if (translationKey.endsWith(".")) {
            str = translationKey;
        } else {
            str = translationKey + ".";
        }
        this.translationKeyPrefix = str;
        return this;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget$SelectionEntry.class */
    public static class SelectionEntry<T> {
        private final T value;
        private final Icon thumbnailIcon;

        public SelectionEntry(T value, @Nullable Icon thumbnailIcon) {
            this.value = value;
            this.thumbnailIcon = thumbnailIcon;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            SelectionEntry<?> entry = (SelectionEntry) object;
            return Objects.equals(this.value, entry.value);
        }

        public int hashCode() {
            if (this.value != null) {
                return this.value.hashCode();
            }
            return 0;
        }

        public T getValue() {
            return this.value;
        }

        @Nullable
        public Icon thumbnailIcon() {
            return this.thumbnailIcon;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget$SelectionEntryWidget.class */
    public static class SelectionEntryWidget<T> extends DivWidget {
        private final SelectionEntry<T> selectionEntry;

        public SelectionEntryWidget(SelectionEntry<T> selectionEntry) {
            this.selectionEntry = selectionEntry;
        }

        public SelectionEntry<T> selectionEntry() {
            return this.selectionEntry;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/AdvancedSelectionWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<AdvancedSelectionSetting, AdvancedSelectionWidget<?>> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public AdvancedSelectionWidget<?>[] create(Setting setting, AdvancedSelectionSetting annotation, SettingAccessor accessor) {
            Class<?> type = accessor.getType();
            String translationKeyPrefix = setting.getTranslationKey() + ".entries";
            Object obj = accessor.get();
            Objects.requireNonNull(accessor);
            AdvancedSelectionWidget<?> advancedSelectionWidgetTranslationKeyPrefix = AdvancedSelectionWidget.create(obj, accessor::set).translationKeyPrefix(translationKeyPrefix);
            if (type.isEnum()) {
                for (Enum<?> enumConstant : (Enum[]) type.getEnumConstants()) {
                    advancedSelectionWidgetTranslationKeyPrefix.add(enumConstant);
                }
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                advancedSelectionWidgetTranslationKeyPrefix.setSelected(newValue);
            });
            return new AdvancedSelectionWidget[]{advancedSelectionWidgetTranslationKeyPrefix};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[0];
        }
    }
}

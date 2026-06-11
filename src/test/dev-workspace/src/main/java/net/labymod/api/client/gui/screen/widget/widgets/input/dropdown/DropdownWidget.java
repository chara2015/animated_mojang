package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownPopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.DefaultEntryRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/DropdownWidget.class */
@AutoWidget
@SettingWidget
@Link("activity/overlay/dropdown/dropdown.lss")
public class DropdownWidget<T> extends AbstractWidget<Widget> {
    private static final ModifyReason DROPDOWN_POSITION = ModifyReason.of("dropdownPosition");
    protected final LssProperty<Float> wrapperPadding = new LssProperty<>(Float.valueOf(1.0f));
    private final LssProperty<Float> arrowWidth = new LssProperty<>(Float.valueOf(30.0f));
    protected final List<T> entries = new ArrayList();
    protected EntryRenderer<T> entryRenderer = new DefaultEntryRenderer();
    protected boolean open;
    protected boolean dropUp;
    protected long lastTimeOpenChanged;
    protected WidgetReference reference;
    private T selected;
    private ChangeListener<T> changeListener;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/DropdownWidget$ChangeListener.class */
    public interface ChangeListener<T> {
        void onChange(T t);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/DropdownWidget$DropdownEntryTranslationPrefix.class */
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DropdownEntryTranslationPrefix {
        String value();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/DropdownWidget$DropdownSetting.class */
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DropdownSetting {
    }

    public DropdownWidget() {
        setAttributeState(AttributeState.ENABLED, true);
        setPressable(() -> {
            toggleOpen();
        });
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.selected != null) {
            Widget widget = this.entryRenderer.createSelectedWidget(this.selected);
            widget.addId("selected");
            addChild(widget);
        }
    }

    private void toggleOpen() {
        if (this.open) {
            close();
        } else {
            open();
        }
    }

    public void openAt(Rectangle attachTo, List<StyleSheet> styleSheets) {
        if (!hasEntries()) {
            return;
        }
        DropdownPopupWidget<T> popup = new DropdownPopupWidget<>(this);
        Window window = this.labyAPI.minecraft().minecraftWindow();
        this.dropUp = attachTo.getY() > ((float) window.getScaledHeight()) / 2.0f;
        if (this.dropUp) {
            popup.addId("drop-up");
        }
        for (CharSequence id : getIds()) {
            popup.addId(String.valueOf(id) + "-popup");
        }
        this.reference = displayInOverlay(styleSheets, popup);
        this.reference.boundsUpdater((ref, bounds) -> {
            boolean dropUp = isDropUp();
            float width = attachTo.getWidth() - (wrapperPadding().get().floatValue() * 2.0f);
            float height = ref.widget().getEffectiveHeight();
            bounds.setSize(width, height, DROPDOWN_POSITION);
            float x = attachTo.getX() + wrapperPadding().get().floatValue();
            float y = attachTo.getY() + (dropUp ? -height : attachTo.getHeight());
            bounds.setPosition(x, y, DROPDOWN_POSITION);
        });
        this.reference.destroyHandlers().add(this::close);
        this.open = true;
        this.lastTimeOpenChanged = TimeUtil.getMillis();
    }

    public void openAt(Rectangle attachTo) {
        StyleSheet styleSheet = new LinkReference("labymod", "lss/activity/overlay/dropdown/dropdown.lss").loadStyleSheet();
        openAt(attachTo, Arrays.asList(styleSheet));
    }

    public void open() {
        openAt(Bounds.absoluteBounds(this), getStyleSheets());
    }

    public void close() {
        close(true);
    }

    public void close(boolean playSound) {
        this.open = false;
        this.lastTimeOpenChanged = TimeUtil.getMillis();
        if (this.reference != null && this.reference.isAlive()) {
            this.reference.remove();
        }
        if (this.labyAPI.minecraft().mouse().isInside(Bounds.absoluteBounds(this)) && playSound) {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
        }
    }

    protected void update() {
        setAttributeState(AttributeState.ENABLED, hasEntries());
    }

    public void add(T entry) {
        this.entries.add(entry);
        update();
    }

    public void addAll(Collection<T> entries) {
        this.entries.addAll(entries);
        update();
    }

    @Deprecated
    public void addAll(List<T> entries) {
        addAll((Collection) entries);
    }

    public void addAll(T[] entries) {
        this.entries.addAll(Arrays.asList(entries));
        update();
    }

    public void remove(T entry) {
        this.entries.remove(entry);
        update();
    }

    public void clear() {
        this.entries.clear();
        update();
    }

    public void setSelected(T entry) {
        setSelected(entry, true);
    }

    public void setSelected(T entry, boolean notify) {
        this.selected = entry;
        if (isInitialized()) {
            reInitialize();
        }
        if (notify && this.changeListener != null) {
            this.changeListener.onChange(entry);
        }
        update();
    }

    public T getSelected() {
        return this.selected;
    }

    public void setChangeListener(ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    public void setEntryRenderer(EntryRenderer<T> entryRenderer) {
        this.entryRenderer = entryRenderer;
    }

    public List<T> entries() {
        return this.entries;
    }

    public float getWidth() {
        return bounds().getWidth();
    }

    public void onSelect(T entry) {
        setSelected(entry);
    }

    public EntryRenderer<T> entryRenderer() {
        return this.entryRenderer;
    }

    @Nullable
    public WidgetReference getReference() {
        return this.reference;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isDropUp() {
        return this.dropUp;
    }

    public boolean hasEntries() {
        return !this.entries.isEmpty();
    }

    public long getLastTimeOpenChanged() {
        return this.lastTimeOpenChanged;
    }

    public float getAnimationProgress() {
        if (this.reference == null) {
            return 0.0f;
        }
        long animationDuration = TimeUtil.getMillis() - this.lastTimeOpenChanged;
        float progress = (float) CubicBezier.EASE_OUT.curve(Math.min(1.0f, animationDuration / 100.0f));
        return this.open ? progress : 1.0f - progress;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float sizeOr;
        boolean isFitContent = hasSize(SizeType.MAX, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT);
        if (isFitContent) {
            sizeOr = Float.MAX_VALUE;
        } else {
            sizeOr = getSizeOr(SizeType.MAX, WidgetSide.WIDTH, Float.MAX_VALUE);
        }
        float maxWidth = sizeOr;
        float width = this.selected == null ? 0.0f : this.entryRenderer.getWidth(this.selected, maxWidth);
        for (T entry : this.entries) {
            width = Math.max(width, this.entryRenderer.getWidth(entry, maxWidth));
        }
        return width + arrowWidth().get().floatValue() + (wrapperPadding().get().floatValue() * 2.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "ButtonDropdown";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        close(false);
        this.lastTimeOpenChanged = 0L;
    }

    public LssProperty<Float> arrowWidth() {
        return this.arrowWidth;
    }

    public LssProperty<Float> wrapperPadding() {
        return this.wrapperPadding;
    }

    @Deprecated
    public void translationKeyPrefix(String key) {
        setTranslationKeyPrefix(key);
    }

    public void setTranslationKeyPrefix(String key) {
        if (!(this.entryRenderer instanceof DefaultEntryRenderer)) {
            throw new IllegalStateException("Cannot set translation key prefix for custom entry renderer");
        }
        if (key.endsWith(".")) {
            key = key.substring(0, key.length() - 1);
        }
        ((DefaultEntryRenderer) this.entryRenderer).setTranslationKeyPrefix(key);
    }

    @Deprecated
    public static <T> DropdownWidget<T> create(T selected, ChangeListener<T> changeListener) {
        DropdownWidget<T> widget = new DropdownWidget<>();
        widget.setSelected(selected);
        widget.setChangeListener(changeListener);
        return widget;
    }

    @Deprecated
    public static <T> DropdownWidget<T> create(ChangeListener<T> changeListener) {
        DropdownWidget<T> widget = new DropdownWidget<>();
        widget.setChangeListener(changeListener);
        return widget;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/DropdownWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<DropdownSetting, DropdownWidget<?>> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public DropdownWidget<?>[] create(Setting setting, DropdownSetting annotation, SettingAccessor accessor) {
            Class<?> type = accessor.getType();
            DropdownWidget<?> dropdownWidget = new DropdownWidget<>();
            dropdownWidget.setSelected(accessor.get());
            Objects.requireNonNull(accessor);
            dropdownWidget.setChangeListener(accessor::set);
            DropdownEntryTranslationPrefix prefixAnnotation = (DropdownEntryTranslationPrefix) accessor.getField().getAnnotation(DropdownEntryTranslationPrefix.class);
            DefaultEntryRenderer<Object> entryRenderer = (DefaultEntryRenderer) dropdownWidget.entryRenderer();
            if (prefixAnnotation == null) {
                entryRenderer.setTranslationKeyPrefix(accessor.setting().getTranslationKey() + ".entries");
            } else {
                entryRenderer.setTranslationKeyPrefix(prefixAnnotation.value());
            }
            if (type.isEnum()) {
                for (Enum<?> enumConstant : (Enum[]) type.getEnumConstants()) {
                    dropdownWidget.add(enumConstant);
                }
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                dropdownWidget.setSelected(newValue);
            });
            return new DropdownWidget[]{dropdownWidget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[0];
        }
    }
}

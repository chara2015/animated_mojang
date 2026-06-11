package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/ButtonWidget.class */
@AutoWidget
@SettingWidget
public class ButtonWidget extends HorizontalListWidget {
    public static final boolean SUPPORTS_SCROLLING = MinecraftVersions.V1_19_4.orNewer();
    protected Component component;
    protected ComponentWidget componentWidget;
    private Icon tempIcon;
    private final LssProperty<Boolean> scaleToFit = new LssProperty<>(true);
    private final LssProperty<Integer> disabledColor = new LssProperty<>(0);
    private final LssProperty<Icon> icon = new LssProperty<>(null);
    private final LssProperty<Component> text = new LssProperty<>(null);
    private final LssProperty<HorizontalAlignment> alignment = new LssProperty<>(HorizontalAlignment.CENTER);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/ButtonWidget$ButtonSetting.class */
    @SettingElement
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ButtonSetting {
        @Deprecated
        String text() default "";

        @Deprecated
        String translation() default "";
    }

    public ButtonWidget() {
        setEnabled(true);
        addId("button", "primary-button");
        setHoverCursor(CursorTypes.POINTING_HAND);
    }

    protected ButtonWidget(Component component, Icon icon) {
        this.component = component;
        this.icon.set(icon);
        setEnabled(true);
        addId("button", "primary-button");
        setHoverCursor(CursorTypes.POINTING_HAND);
    }

    protected ButtonWidget(Icon icon) {
        this.icon.set(icon);
        setHoverCursor(CursorTypes.POINTING_HAND);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Button";
    }

    public static ButtonWidget icon(Icon icon, Pressable pressable) {
        return component(null, icon, pressable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return isAttributeStateEnabled(AttributeState.ENABLED) && super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        callActionListeners();
        return super.onPress();
    }

    public void bindToBranch(String branchName) {
        boolean isBranch = Laby.labyAPI().getBranchName().equals(branchName);
        if (!isBranch) {
            setHoverComponent(Component.translatable("labymod.misc.wip", new Component[0]));
        }
        setEnabled(isBranch);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasTabFocus() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.tempIcon != null) {
            this.icon.set(this.tempIcon);
            this.tempIcon = null;
        }
        if (this.icon.get() != null) {
            HorizontalListEntry icon = addEntry(new IconWidget(this.icon.get()));
            icon.alignment().set(HorizontalAlignment.CENTER);
            icon.addId("button-icon");
        }
        if (this.component == null || findFirstChildIf(widget -> {
            return widget.hasId("button-text");
        }) != null) {
            return;
        }
        ComponentWidget componentWidgetComponent = ComponentWidget.component(this.component);
        this.componentWidget = componentWidgetComponent;
        HorizontalListEntry text = addEntry(componentWidgetComponent);
        text.alignment().set(HorizontalAlignment.CENTER);
        text.addId("button-text");
        text.setActive(isAttributeStateEnabled(AttributeState.ENABLED));
        setStencil(SUPPORTS_SCROLLING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean shouldFocusOnClick() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (SUPPORTS_SCROLLING) {
            updateScrollingString();
        }
        super.renderWidget(context);
    }

    private void updateScrollingString() {
        RenderableComponent renderable;
        if (this.componentWidget == null || (renderable = this.componentWidget.renderable()) == null) {
            return;
        }
        float fontSize = this.componentWidget.fontSize().get().getSize();
        float componentWidth = renderable.getWidth() * fontSize;
        float buttonWidth = bounds().getWidth(BoundsType.MIDDLE) - 4.0f;
        float offset = getTextScrollingOffset(componentWidth, buttonWidth);
        this.componentWidget.translateX().set(Float.valueOf(offset));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (!this.text.isDefaultValue()) {
            updateComponent(this.text.get());
        }
        if (this.componentWidget != null && this.componentWidget.renderable().isClipped() && !hasHoverComponent()) {
            setHoverComponent(this.component);
        }
    }

    public static ButtonWidget advancedSettings() {
        return icon(Textures.SpriteCommon.SETTINGS);
    }

    public static ButtonWidget deleteButton() {
        return icon(Textures.SpriteCommon.TRASH);
    }

    public static ButtonWidget icon(Icon icon) {
        return component(null, icon, null);
    }

    public ButtonWidget updateComponent(Component component) {
        if (this.component != null && this.component.equals(component)) {
            return this;
        }
        this.component = component;
        if (component == null) {
            return this;
        }
        if (this.componentWidget == null) {
            if (this.initialized) {
                ComponentWidget componentWidgetComponent = ComponentWidget.component(component);
                this.componentWidget = componentWidgetComponent;
                HorizontalListEntry text = addEntryInitialized(componentWidgetComponent);
                text.alignment().set(HorizontalAlignment.CENTER);
                text.addId("button-text");
                text.setActive(isAttributeStateEnabled(AttributeState.ENABLED));
                setStencil(SUPPORTS_SCROLLING);
            }
        } else {
            this.componentWidget.setComponent(component);
            updateContentBounds();
        }
        return this;
    }

    public void setEnabled(boolean enabled) {
        setAttributeState(AttributeState.ENABLED, enabled);
    }

    public void updateIcon(Icon icon) {
        this.tempIcon = icon;
        reInitialize();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }

    public LssProperty<Boolean> scaleToFit() {
        return this.scaleToFit;
    }

    public LssProperty<Integer> disabledColor() {
        return this.disabledColor;
    }

    public LssProperty<Icon> icon() {
        return this.icon;
    }

    public LssProperty<Component> text() {
        return this.text;
    }

    public LssProperty<HorizontalAlignment> alignment() {
        return this.alignment;
    }

    public static ButtonWidget i18n(String key) {
        return i18n(key, null, null);
    }

    public static ButtonWidget i18n(String key, Pressable pressable) {
        return i18n(key, null, pressable);
    }

    public static ButtonWidget i18n(String key, Icon icon) {
        return i18n(key, icon, null);
    }

    public static ButtonWidget i18n(String key, Icon icon, Pressable pressable) {
        return component(Component.translatable(key, new Component[0]), icon, pressable);
    }

    public static ButtonWidget i18nMinecraft(String key) {
        return i18nMinecraft(key, null, null);
    }

    public static ButtonWidget i18nMinecraft(String key, Pressable pressable) {
        return i18nMinecraft(key, null, pressable);
    }

    public static ButtonWidget i18nMinecraft(String key, Icon icon) {
        return i18nMinecraft(key, icon, null);
    }

    public static ButtonWidget i18nMinecraft(String key, Icon icon, Pressable pressable) {
        String translation = Laby.labyAPI().minecraft().getTranslation(key);
        return component(Component.text(translation), icon, pressable);
    }

    public static ButtonWidget text(String text) {
        return text(text, null, null);
    }

    public static ButtonWidget text(String text, Pressable pressable) {
        return text(text, null, pressable);
    }

    public static ButtonWidget text(String text, Icon icon) {
        return text(text, icon, null);
    }

    public static ButtonWidget text(String text, Icon icon, Pressable pressable) {
        return component(Component.text(text), icon, pressable);
    }

    public static ButtonWidget component(Component component) {
        return component(component, null, null);
    }

    public static ButtonWidget component(Component component, Pressable pressable) {
        return component(component, null, pressable);
    }

    public static ButtonWidget component(Component component, Icon icon) {
        return new ButtonWidget(component, icon);
    }

    public static ButtonWidget component(Component component, Icon icon, Pressable pressable) {
        ButtonWidget button = new ButtonWidget(component, icon);
        button.setPressable(pressable);
        return button;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/ButtonWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<ButtonSetting, ButtonWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public /* bridge */ /* synthetic */ Widget[] create(Setting setting, Annotation annotation, SettingInfo settingInfo, SettingAccessor settingAccessor) {
            return create(setting, (ButtonSetting) annotation, (SettingInfo<?>) settingInfo, settingAccessor);
        }

        public ButtonWidget[] create(Setting setting, ButtonSetting annotation, SettingInfo<?> info, SettingAccessor accessor) {
            if (!annotation.text().isEmpty()) {
                return new ButtonWidget[]{ButtonWidget.text(annotation.text(), invokeButtonPress(setting, info))};
            }
            if (!annotation.translation().isEmpty()) {
                return new ButtonWidget[]{ButtonWidget.i18n(annotation.translation(), invokeButtonPress(setting, info))};
            }
            return new ButtonWidget[]{ButtonWidget.i18n(setting.getTranslationKey() + ".text", invokeButtonPress(setting, info))};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[0];
        }

        private Pressable invokeButtonPress(Setting setting, SettingInfo<?> settingInfo) {
            return () -> {
                try {
                    Method method = (Method) settingInfo.getMember();
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length == 0) {
                        method.invoke(settingInfo.config(), new Object[0]);
                        return;
                    }
                    Object[] arguments = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        if (parameter.getType() == Setting.class) {
                            arguments[i] = setting;
                        }
                    }
                    method.invoke(settingInfo.config(), arguments);
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                }
            };
        }
    }

    public static float getTextScrollingOffset(float componentWidth, float buttonWidth) {
        if (componentWidth <= buttonWidth || !SUPPORTS_SCROLLING) {
            return 0.0f;
        }
        int overflow = ((int) componentWidth) - ((int) buttonWidth);
        float time = TimeUtil.getMillis() / 1000.0f;
        float speed = Math.max(overflow * 0.5f, 3.0f);
        float rot = MathHelper.cos((6.283185307179586d * ((double) time)) / ((double) speed));
        float sin = (MathHelper.sin(1.5707963267948966d * ((double) rot)) / 2.0f) + 0.5f;
        return ((componentWidth / 2.0f) - (buttonWidth / 2.0f)) - (overflow * sin);
    }
}

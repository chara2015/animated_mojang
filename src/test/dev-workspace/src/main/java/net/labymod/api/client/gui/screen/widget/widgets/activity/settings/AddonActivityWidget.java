package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/AddonActivityWidget.class */
@AutoWidget
@net.labymod.api.configuration.settings.annotation.SettingWidget
@Deprecated
public class AddonActivityWidget extends SimpleWidget implements SettingActivitySupplier {
    private final Supplier<Activity> supplier;
    private String text;
    private String translatable;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/AddonActivityWidget$AddonActivitySetting.class */
    @Target({ElementType.METHOD})
    @Deprecated
    @SettingElement
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AddonActivitySetting {
        @Deprecated
        String text() default "";

        @Deprecated
        String translation() default "";
    }

    private AddonActivityWidget(Supplier<Activity> supplier) {
        this.supplier = supplier;
    }

    public String getText() {
        return this.translatable != null ? I18n.getTranslation(this.translatable, new Object[0]) : this.text;
    }

    public Activity activity() {
        return this.supplier.get();
    }

    @Override // net.labymod.api.configuration.settings.util.SettingActivitySupplier
    public Activity activity(Setting setting) {
        return activity();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/AddonActivityWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<AddonActivitySetting, AddonActivityWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public /* bridge */ /* synthetic */ Widget[] create(Setting setting, Annotation annotation, SettingInfo settingInfo, SettingAccessor settingAccessor) {
            return create(setting, (AddonActivitySetting) annotation, (SettingInfo<?>) settingInfo, settingAccessor);
        }

        public AddonActivityWidget[] create(Setting setting, AddonActivitySetting annotation, SettingInfo<?> info, SettingAccessor accessor) {
            AddonActivityWidget addonActivityWidget = new AddonActivityWidget(invokeButtonPress(info));
            if (!annotation.text().isEmpty()) {
                addonActivityWidget.text = annotation.text();
                return new AddonActivityWidget[]{addonActivityWidget};
            }
            if (!annotation.translation().isEmpty()) {
                addonActivityWidget.translatable = annotation.translation();
                return new AddonActivityWidget[]{addonActivityWidget};
            }
            addonActivityWidget.translatable = setting.getTranslationKey() + ".text";
            return new AddonActivityWidget[]{addonActivityWidget};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[0];
        }

        private Supplier<Activity> invokeButtonPress(SettingInfo<?> settingInfo) {
            return () -> {
                try {
                    return (Activity) ((Method) settingInfo.getMember()).invoke(settingInfo.config(), new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                    return null;
                }
            };
        }
    }
}

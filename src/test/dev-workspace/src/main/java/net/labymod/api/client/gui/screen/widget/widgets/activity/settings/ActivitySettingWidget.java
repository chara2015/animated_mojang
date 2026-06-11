package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/ActivitySettingWidget.class */
@AutoWidget
@net.labymod.api.configuration.settings.annotation.SettingWidget
public class ActivitySettingWidget extends ButtonWidget implements SettingActivitySupplier {
    private final Supplier<Activity> supplier;
    private final String translationKey;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/ActivitySettingWidget$ActivitySetting.class */
    @SettingElement
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ActivitySetting {
    }

    private ActivitySettingWidget(String translationKey, Supplier<Activity> supplier) {
        this.translationKey = translationKey;
        this.supplier = supplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String translation = I18n.getTranslation(this.translationKey, new Object[0]);
        if (translation == null) {
            this.component = null;
            icon().set(Textures.SpriteCommon.SETTINGS);
        } else {
            this.component = Component.text(translation);
            icon().set(null);
        }
        super.initialize(parent);
    }

    @Override // net.labymod.api.configuration.settings.util.SettingActivitySupplier
    public Activity activity(Setting setting) {
        return this.supplier.get();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/ActivitySettingWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<ActivitySetting, ActivitySettingWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public /* bridge */ /* synthetic */ Widget[] create(Setting setting, Annotation annotation, SettingInfo settingInfo, SettingAccessor settingAccessor) {
            return create(setting, (ActivitySetting) annotation, (SettingInfo<?>) settingInfo, settingAccessor);
        }

        public ActivitySettingWidget[] create(Setting setting, ActivitySetting annotation, SettingInfo<?> info, SettingAccessor accessor) {
            ActivitySettingWidget addonActivityWidget = new ActivitySettingWidget(setting.getTranslationKey() + ".text", invokeButtonPress(info));
            return new ActivitySettingWidget[]{addonActivityWidget};
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

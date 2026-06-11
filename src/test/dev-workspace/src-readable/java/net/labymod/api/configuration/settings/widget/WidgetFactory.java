package net.labymod.api.configuration.settings.widget;

import java.lang.annotation.Annotation;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/widget/WidgetFactory.class */
public interface WidgetFactory<T extends Annotation, K extends Widget> {
    Class<?>[] types();

    default K[] create(Setting setting, T t, SettingInfo<?> settingInfo, SettingAccessor settingAccessor) {
        return (K[]) create(setting, t, settingAccessor);
    }

    default K[] create(Setting setting, T annotation, SettingAccessor accessor) {
        throw new UnsupportedOperationException("Not implemented by " + getClass().getSimpleName());
    }
}

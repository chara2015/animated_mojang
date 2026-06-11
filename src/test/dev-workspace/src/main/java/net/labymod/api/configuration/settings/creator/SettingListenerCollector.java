package net.labymod.api.configuration.settings.creator;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/SettingListenerCollector.class */
public class SettingListenerCollector {
    public static List<SettingListenerMethod> collect(Class<? extends Config> configClass) {
        List<SettingListenerMethod> listenerMethods = new ArrayList<>();
        Reflection.getMethods(configClass, false, method -> {
            SettingListener annotation = (SettingListener) method.getAnnotation(SettingListener.class);
            if (annotation == null || method.getReturnType() != Void.TYPE || method.getParameterCount() != 1 || !method.getParameterTypes()[0].isAssignableFrom(SettingElement.class)) {
                return;
            }
            listenerMethods.add(new SettingListenerMethod(annotation, method));
        });
        return listenerMethods;
    }
}

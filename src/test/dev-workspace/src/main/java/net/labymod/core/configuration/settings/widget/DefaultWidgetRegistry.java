package net.labymod.core.configuration.settings.widget;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.configuration.settings.widget.WidgetStorage;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/settings/widget/DefaultWidgetRegistry.class */
@Singleton
@Implements(WidgetRegistry.class)
public class DefaultWidgetRegistry implements WidgetRegistry {
    private final Logging logger;
    private final Map<Class<? extends Annotation>, Class<? extends Widget>> registry = new HashMap();

    @Inject
    public DefaultWidgetRegistry(EventBus eventBus) {
        eventBus.registerListener(this);
        this.logger = Logging.create((Class<?>) WidgetRegistry.class);
    }

    @Subscribe
    public void loadWidgetStorages(ServiceLoadEvent event) {
        loadWidgetStorage(event.classLoader());
    }

    @Override // net.labymod.api.configuration.settings.widget.WidgetRegistry
    public void loadWidgetStorage(ClassLoader loader) {
        List<Class<? extends Widget>> storage = new ArrayList<>();
        CustomServiceLoader<WidgetStorage> storageLoader = CustomServiceLoader.load(WidgetStorage.class, loader, CustomServiceLoader.ServiceType.CROSS_VERSION);
        for (WidgetStorage widgetStorage : storageLoader) {
            widgetStorage.store(storage);
        }
        storage.forEach(this::register);
        storage.clear();
    }

    @Override // net.labymod.api.configuration.settings.widget.WidgetRegistry
    public void register(Class<? extends Widget> cls) {
        for (Class<?> cls2 : cls.getClasses()) {
            if (cls2.getAnnotation(SettingElement.class) != null) {
                this.registry.put((Class<? extends Annotation>) cls2, cls);
                return;
            }
        }
    }

    @Override // net.labymod.api.configuration.settings.widget.WidgetRegistry
    public Widget[] createWidgets(Setting setting, Annotation annotation, SettingInfo<?> info, SettingAccessor accessor) {
        try {
            Class<? extends Widget> clazz = getWidgetTypeByAnnotation(annotation);
            if (clazz == null) {
                return null;
            }
            for (Class<?> declaredClass : clazz.getDeclaredClasses()) {
                if (WidgetFactory.class.isAssignableFrom(declaredClass)) {
                    return createWidgets((WidgetFactory) declaredClass.getConstructor(new Class[0]).newInstance(new Object[0]), setting, annotation, info, accessor);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // net.labymod.api.configuration.settings.widget.WidgetRegistry
    public Class<? extends Widget> getWidgetTypeByAnnotation(Annotation annotation) {
        return this.registry.get(annotation.annotationType());
    }

    private Widget[] createWidgets(WidgetFactory<Annotation, Widget> widgetFactory, Setting setting, Annotation annotation, SettingInfo<?> info, SettingAccessor accessor) {
        Class<?>[] types = widgetFactory.types();
        if (types.length != 0) {
            String[] typeString = new String[types.length];
            boolean typesMatch = false;
            for (int i = 0; i < types.length; i++) {
                Class<?> type = types[i];
                if (accessor.getType() == type || type.isAssignableFrom(accessor.getType())) {
                    typesMatch = true;
                }
                typeString[i] = type.getSimpleName();
            }
            if (!typesMatch) {
                Logging logging = this.logger;
                Object[] objArr = new Object[3];
                objArr[0] = setting.getTranslationKey();
                objArr[1] = accessor.getType().getSimpleName();
                objArr[2] = typeString.length == 1 ? ": " + typeString[0] : " one of these types: " + String.join(", ", typeString);
                logging.error("The setting field \"{}\" has the type {} but has to be {}", objArr);
                return null;
            }
        }
        return widgetFactory.create(setting, annotation, info, accessor);
    }
}

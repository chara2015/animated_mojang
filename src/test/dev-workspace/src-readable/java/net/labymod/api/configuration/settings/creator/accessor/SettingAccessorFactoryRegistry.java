package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.type.SettingElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/accessor/SettingAccessorFactoryRegistry.class */
public class SettingAccessorFactoryRegistry {
    private final SettingAccessorFactory fallbackFactory = new ReflectionSettingAccessorFactory();
    private final List<SettingAccessorFactory> factories = new ArrayList();
    private final LabyAPI labyAPI;

    public SettingAccessorFactoryRegistry(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        registerDefaults();
    }

    private void registerDefaults() {
        register(new ConfigPropertySettingAccessorFactory());
    }

    public void register(SettingAccessorFactory factory) {
        this.factories.add(factory);
    }

    public SettingAccessor createAccessor(SettingElement setting, MemberInspector element, Config config) {
        AnnotatedElement annotatedElementMember = element.member();
        if (!(annotatedElementMember instanceof Field)) {
            return null;
        }
        Field field = (Field) annotatedElementMember;
        int modifiers = field.getModifiers();
        if ((Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) || Modifier.isTransient(modifiers)) {
            return null;
        }
        SettingAccessorContext context = new SettingAccessorContext(element, field);
        for (SettingAccessorFactory factory : this.factories) {
            if (factory.isAssignableFrom(field.getType())) {
                return factory.create(setting, context, config);
            }
        }
        return this.fallbackFactory.create(setting, context, config);
    }
}

package net.labymod.api.configuration.loader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.ConfigPath;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/ConfigLoader.class */
public interface ConfigLoader {
    <T extends ConfigAccessor> T load(Class<T> cls) throws ConfigurationLoadException;

    void save(ConfigAccessor configAccessor) throws ConfigurationSaveException;

    Object serialize(ConfigAccessor configAccessor) throws Exception;

    void setVariable(String str, Object obj);

    String getFileExtension();

    void invalidate(Class<? extends ConfigAccessor> cls) throws IOException;

    static String getName(Class<? extends ConfigAccessor> clazz) {
        ConfigName annotation = null;
        Iterator<Class<?>> it = Reflection.getClassTree(clazz).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Class<?> treeClass = it.next();
            ConfigName treeClassAnnotation = (ConfigName) treeClass.getAnnotation(ConfigName.class);
            if (treeClassAnnotation != null) {
                annotation = treeClassAnnotation;
                break;
            }
        }
        return annotation == null ? "settings" : annotation.value();
    }

    default String getRelativePath(Class<? extends ConfigAccessor> clazz) {
        ConfigPath annotation = null;
        Iterator<Class<?>> it = Reflection.getClassTree(clazz).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Class<?> treeClass = it.next();
            ConfigPath treeClassAnnotation = (ConfigPath) treeClass.getAnnotation(ConfigPath.class);
            if (treeClassAnnotation != null) {
                annotation = treeClassAnnotation;
                break;
            }
        }
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[2];
        objArr[0] = annotation == null ? "." : annotation.value();
        objArr[1] = getName(clazz);
        return String.format(locale, "%s/%s", objArr);
    }
}

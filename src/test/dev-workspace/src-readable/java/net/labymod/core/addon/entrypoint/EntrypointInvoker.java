package net.labymod.core.addon.entrypoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/entrypoint/EntrypointInvoker.class */
@ApiStatus.Internal
public final class EntrypointInvoker {
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) EntrypointInvoker.class);

    public static void invoke(AddonEntrypoint entrypoint, ClassLoader classLoader) {
        String entrypointName = entrypoint.name();
        boolean invoked = invoke(classLoader, entrypointName);
        if (!invoked) {
            LOGGER.error("The entry point {} could not be invoked because no empty constructor was found!", entrypointName);
        }
    }

    private static boolean invoke(ClassLoader classLoader, String qualifiedEntrypoint) {
        try {
            Class<?> entrypointClass = classLoader.loadClass(qualifiedEntrypoint);
            Class<?> ift = classLoader.loadClass("net.labymod.api.addon.entrypoint.Entrypoint");
            if (!ift.isAssignableFrom(entrypointClass)) {
                throw new AddonLoadException();
            }
            Constructor<?> constructor = Reflection.searchEmptyConstructor(entrypointClass);
            if (constructor == null) {
                return false;
            }
            Object entrypointObject = constructor.newInstance(new Object[0]);
            Method initializeMethod = entrypointClass.getDeclaredMethod("initialize", Version.class);
            initializeMethod.invoke(entrypointObject, DefaultLabyModLoader.getInstance().version());
            return true;
        } catch (ReflectiveOperationException exception) {
            throw new AddonLoadException("Failed to instantiate addon entry point " + qualifiedEntrypoint, exception);
        }
    }
}

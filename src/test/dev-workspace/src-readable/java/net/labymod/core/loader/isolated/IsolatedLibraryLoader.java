package net.labymod.core.loader.isolated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/IsolatedLibraryLoader.class */
public abstract class IsolatedLibraryLoader {
    protected static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) IsolatedLibrary.class);
    protected final List<IsolatedLibraryPredicate> predicates = new ArrayList();
    protected final Map<String, IsolatedClassLoader> classLoaderMap = new HashMap();
    protected final Set<IsolatedLibrary> loadedLibraries = new HashSet();
    protected final List<IsolatedLibraryFinishHandler> finishHandlers = new ArrayList();

    protected abstract void onLoad(ClassLoader classLoader);

    public IsolatedLibraryLoader() {
        registerClassLoader("org.lwjgl", IsolatedClassLoaders.LWJGL_CLASS_LOADER);
        registerClassLoader("org.lwjgl.lwjgl", IsolatedClassLoaders.LWJGL2_CLASS_LOADER);
        registerClassLoader("net.java.dev.jna", IsolatedClassLoaders.JNA_CLASS_LOADER);
        registerClassLoader("net.labymod", IsolatedClassLoaders.THIN_LWJGL_LOADER);
    }

    public void load(ClassLoader loader) {
        onLoad(loader);
        onFinish();
    }

    public void addFilter(IsolatedLibraryPredicate predicate) {
        this.predicates.add(predicate);
    }

    public void addFinishHandler(IsolatedLibraryFinishHandler finishHandler) {
        this.finishHandlers.add(finishHandler);
    }

    private void onFinish() {
        for (IsolatedLibrary loadedLibrary : this.loadedLibraries) {
            for (IsolatedLibraryFinishHandler finishHandler : this.finishHandlers) {
                finishHandler.onAccept(loadedLibrary);
            }
        }
        for (IsolatedLibraryFinishHandler finishHandler2 : this.finishHandlers) {
            finishHandler2.onFinish();
        }
    }

    public void registerClassLoader(String group, IsolatedClassLoader classLoader) {
        registerClassLoader(group, classLoader, true);
    }

    public void registerClassLoader(String group, IsolatedClassLoader classLoader, boolean isolated) {
        this.classLoaderMap.put(group, classLoader);
        if (!isolated) {
            PlatformEnvironment.getPlatformClassloader().registerChildClassloader(classLoader.getName(), classLoader);
        }
    }

    protected IsolatedClassLoader getClassLoader(String group) {
        IsolatedClassLoader loader = this.classLoaderMap.get(group);
        if (loader == null) {
            throw new IllegalStateException("No ClassLoader could be found for the group \"" + group + "\"");
        }
        return loader;
    }
}

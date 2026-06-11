package net.labymod.api.classloader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/classloader/ClassLoaderUtil.class */
public final class ClassLoaderUtil {
    private ClassLoaderUtil() {
    }

    public static Class<?> forName(String name, boolean initialized, ClassLoader loader) throws ClassNotFoundException {
        return Class.forName(name, initialized, loader);
    }
}

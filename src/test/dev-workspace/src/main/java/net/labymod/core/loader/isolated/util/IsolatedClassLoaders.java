package net.labymod.core.loader.isolated.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/util/IsolatedClassLoaders.class */
public final class IsolatedClassLoaders {
    public static final IsolatedClassLoader LWJGL_CLASS_LOADER = new IsolatedClassLoader("LWJGL3");
    public static final IsolatedClassLoader LWJGL2_CLASS_LOADER = new IsolatedClassLoader("LWJGL2");
    public static final IsolatedClassLoader JNA_CLASS_LOADER = new IsolatedClassLoader("JNA");
    public static final IsolatedClassLoader THIN_LWJGL_LOADER = new IsolatedClassLoader("THIN-LWJGL");

    private IsolatedClassLoaders() {
    }
}

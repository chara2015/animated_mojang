package net.labymod.api.mixin.dynamic;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mixin/dynamic/DynamicMixinUtil.class */
public final class DynamicMixinUtil {
    private static LoadResources loadResources = (v0, v1) -> {
        return v0.getResources(v1);
    };

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mixin/dynamic/DynamicMixinUtil$LoadResources.class */
    public interface LoadResources {
        Enumeration<URL> findResources(ClassLoader classLoader, String str) throws IOException;
    }

    public static void setLoadResources(LoadResources loadResources2) {
        loadResources = loadResources2;
    }

    public static Enumeration<URL> getResources(ClassLoader loader, String name) throws IOException {
        return loadResources.findResources(loader, name);
    }
}

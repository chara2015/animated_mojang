package net.labymod.api.volt.asm.util;

import java.net.URL;
import java.util.function.Function;
import java.util.function.IntSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/util/ASMContext.class */
public class ASMContext {
    private static ClassLoader platformClassLoader;
    private static final IntSupplier CLASS_VERSION = () -> {
        return 52;
    };
    private static boolean devEnvironment = false;
    private static Function<String, URL> resourceFinder = name -> {
        return platformClassLoader.getResource(name);
    };

    public static boolean isDevEnvironment() {
        return devEnvironment;
    }

    public static void setDevEnvironment(boolean devEnvironment2) {
        devEnvironment = devEnvironment2;
    }

    public static ClassLoader getPlatformClassLoader() {
        return platformClassLoader;
    }

    public static void setPlatformClassLoader(ClassLoader platformClassLoader2) {
        platformClassLoader = platformClassLoader2;
    }

    public static void setResourceFinder(Function<String, URL> resourceFinder2) {
        resourceFinder = resourceFinder2;
    }

    public static URL findResource(String name) {
        return resourceFinder.apply(name);
    }

    public static int getClassVersion() {
        return CLASS_VERSION.getAsInt();
    }
}

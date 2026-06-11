package net.labymod.core.loader;

import java.io.InputStream;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/ReflectLabyModLoader.class */
public final class ReflectLabyModLoader {
    public static void invokeGameOptions(List<String> arguments, Path gameDirectory, Path assetsDirectory, String profile, boolean labyModDevEnvironment, boolean addonDevEnvironment, String minecraftVersion) {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            (void) Reflection.findVirtual(labyModLoader.getClass(), "gameOptions", MethodType.methodType(Void.TYPE, List.class, Path.class, Path.class, String.class, Boolean.TYPE, Boolean.TYPE, String.class)).invoke(labyModLoader, arguments, gameDirectory, assetsDirectory, profile, labyModDevEnvironment, addonDevEnvironment, minecraftVersion);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static String[] invokeLaunchArguments() {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            List<String> list = (List) Reflection.findVirtual(labyModLoader.getClass(), "getArguments", MethodType.methodType(List.class)).invoke(labyModLoader);
            return (String[]) list.toArray(new String[0]);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static void invokeLoadAddons() {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            (void) Reflection.findVirtual(labyModLoader.getClass(), "loadAddons", MethodType.methodType(Void.TYPE)).invoke(labyModLoader);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static void invokeLoadIsolatedLibraries() {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            (void) Reflection.findVirtual(labyModLoader.getClass(), "loadIsolatedLibraries", MethodType.methodType(Void.TYPE)).invoke(labyModLoader);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static String invokeGetGameVersion() {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            Class<?> versionClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.api.models.version.Version");
            Object getVersion = (Object) Reflection.findVirtual(labyModLoader.getClass(), "version", MethodType.methodType(versionClass)).invoke(labyModLoader);
            return (String) Reflection.findVirtual(versionClass, "toString", MethodType.methodType(String.class)).invoke(getVersion);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static boolean invokeIsLabyModDevEnvironment() {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            return (boolean) Reflection.findVirtual(labyModLoader.getClass(), "isLabyModDevelopmentEnvironment", MethodType.methodType(Boolean.TYPE)).invoke(labyModLoader);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static InputStream invokeGetMixinServiceResourceAsStream(String name, InputStream parentInputStream) {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            return (InputStream) Reflection.findVirtual(labyModLoader.getClass(), "getMixinServiceResourceAsStream", MethodType.methodType(InputStream.class, String.class, InputStream.class)).invoke(labyModLoader, name, parentInputStream);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    public static byte[] invokeGetMixinServiceClassBytes(String name, String transformedName, byte[] parentClassData) {
        try {
            Object labyModLoader = invokeGetLabyModLoader();
            return (byte[]) Reflection.findVirtual(labyModLoader.getClass(), "getMixinServiceClassBytes", MethodType.methodType(byte[].class, String.class, String.class, byte[].class)).invoke(labyModLoader, name, transformedName, parentClassData);
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }

    private static Object invokeGetLabyModLoader() {
        try {
            Class<?> interfaceClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.api.loader.LabyModLoader");
            Class<?> implementationClass = PlatformEnvironment.getPlatformClassloader().findClass("net.labymod.core.loader.DefaultLabyModLoader");
            return (Object) Reflection.findStatic(implementationClass, "getInstance", MethodType.methodType(interfaceClass)).invoke();
        } catch (Throwable throwable) {
            throw new RuntimeException("A fatal error has occurred.", throwable);
        }
    }
}

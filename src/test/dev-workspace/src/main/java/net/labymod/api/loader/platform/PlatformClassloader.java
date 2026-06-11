package net.labymod.api.loader.platform;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import org.objectweb.asm.commons.Remapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/platform/PlatformClassloader.class */
public interface PlatformClassloader {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/platform/PlatformClassloader$TransformerPhase.class */
    public enum TransformerPhase {
        PRE,
        NORMAL,
        POST
    }

    void addUrl(URL url) throws MalformedURLException;

    Class<?> findClass(String str) throws ClassNotFoundException;

    void registerChildClassloader(String str, PlatformChildClassloader platformChildClassloader);

    void registerTransformer(TransformerPhase transformerPhase, String str);

    void registerTransformer(TransformerPhase transformerPhase, PlatformClassTransformer platformClassTransformer);

    PlatformAccessWidener getAccessWidener();

    ClassLoader getPlatformClassloader();

    @DevelopmentEnvironmentOnly
    List<URL> getPotentialClasspathAddons();

    List<URL> getClasspath();

    Platform getPlatform();

    Remapper getRuntimeRemapper();

    Enumeration<URL> getResources(ClassLoader classLoader, String str) throws IOException;

    default void addPath(Path path) {
        try {
            addUrl(path.toUri().toURL());
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
    }

    default boolean searchOnClasspath(String name) {
        String property = System.getProperty("java.class.path");
        String[] libraries = property.split(File.pathSeparator);
        for (String library : libraries) {
            if (library.contains(name)) {
                return true;
            }
        }
        return false;
    }
}

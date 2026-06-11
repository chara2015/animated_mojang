package net.labymod.core.loader.vanilla.launchwrapper.platform;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import net.labymod.api.loader.platform.Platform;
import net.labymod.api.loader.platform.PlatformAccessWidener;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import net.labymod.api.loader.platform.PlatformClassTransformer;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.classpath.Classpath;
import net.minecraft.launchwrapper.loader.BaseClassLoader;
import org.objectweb.asm.commons.Remapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/platform/LaunchWrapperPlatformClassloader.class */
public class LaunchWrapperPlatformClassloader implements PlatformClassloader {
    private final PlatformAccessWidener platformAccessWidener = new LaunchWrapperPlatformAccessWidener();

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public void addUrl(URL url) {
        Launch.classLoader.addURL(url);
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return Launch.classLoader.findClass(name);
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public void registerChildClassloader(String name, PlatformChildClassloader classloader) {
        Launch.classLoader.registerChildClassloader(new WrappedPlatformChildClassloader(classloader));
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public void registerTransformer(PlatformClassloader.TransformerPhase phase, String transformerName) {
        Launch.classLoader.registerTransformer(transformerName);
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public void registerTransformer(PlatformClassloader.TransformerPhase phase, PlatformClassTransformer transformer) {
        IClassTransformer classTransformer = null;
        if (transformer instanceof IClassTransformer) {
            classTransformer = (IClassTransformer) transformer;
        }
        if (classTransformer == null) {
            return;
        }
        Launch.classLoader.registerTransformer(classTransformer);
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public PlatformAccessWidener getAccessWidener() {
        return this.platformAccessWidener;
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public ClassLoader getPlatformClassloader() {
        return Launch.classLoader;
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public List<URL> getPotentialClasspathAddons() {
        return Classpath.potentialClasspathAddons();
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public List<URL> getClasspath() {
        return Launch.classLoader.getSources();
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public Platform getPlatform() {
        return Platform.VANILLA;
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public Remapper getRuntimeRemapper() {
        return null;
    }

    @Override // net.labymod.api.loader.platform.PlatformClassloader
    public Enumeration<URL> getResources(ClassLoader loader, String name) throws IOException {
        if (loader instanceof BaseClassLoader) {
            BaseClassLoader baseClassLoader = (BaseClassLoader) loader;
            return baseClassLoader.findResourceObjects(name);
        }
        return loader.getResources(name);
    }
}

package net.labymod.core.addon.loader.prepare;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.Constants;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.addon.exception.AddonInitException;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.addon.exception.UnsupportedAddonException;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.models.addon.annotation.AddonEntryPoint;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.AddonRemapper;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.entrypoint.EntrypointInvoker;
import net.labymod.core.addon.file.AddonResourceFinder;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.addon.transformer.WrappedAddonClassTransformer;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.loader.ReflectLabyModLoader;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/prepare/AddonPreparer.class */
public class AddonPreparer extends AddonLoaderSubService {
    private final DefaultAddonService addonService;
    private final LabyModLoader labyModLoader;
    private final AddonRemapper addonRemapper;
    private final Map<AddonEntryPoint.Point, List<WrappedAddonEntrypoint>> addonEntrypoints;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/prepare/AddonPreparer$AddonPrepareContext.class */
    public enum AddonPrepareContext {
        DEFAULT,
        RUNTIME,
        CLASSPATH
    }

    public AddonPreparer(AddonLoader addonLoader, DefaultAddonService addonService) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.PREPARE);
        this.addonService = addonService;
        this.labyModLoader = LabyMod.getInstance().labyModLoader();
        this.addonRemapper = new AddonRemapper();
        this.addonEntrypoints = new HashMap();
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        for (InstalledAddonInfo addonInfo : getAddons()) {
            try {
                loadAddon(addonInfo);
                this.logger.info("Loading addon {} v{}", addonInfo.getNamespace(), addonInfo.getVersion());
            } catch (Exception exception) {
                this.logger.warn("Unable to load addon {}. {}: {}", addonInfo.getNamespace(), exception.getClass().getSimpleName(), exception.getMessage(), exception);
            }
        }
    }

    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void completed() throws Exception {
        this.logger.info("Preparing all Loaded Addons...", new Object[0]);
        prepareLoadedAddons();
    }

    private void loadAddon(InstalledAddonInfo addonInfo) throws IOException {
        loadAddon(addonInfo, AddonPrepareContext.DEFAULT);
    }

    public void loadAddon(InstalledAddonInfo addonInfo, AddonPrepareContext context) throws IOException {
        AddonClassLoader classLoader = loadAddonClassLoader(addonInfo, addonInfo.getPath());
        prepareAddon(classLoader, context);
    }

    public void prepareAddon(AddonClassLoader addonClassLoader, AddonPrepareContext context) throws IOException {
        InstalledAddonInfo addonInfo;
        try {
            boolean onClasspath = context == AddonPrepareContext.CLASSPATH && this.labyModLoader.isAddonDevelopmentEnvironment();
            LoadedAddon loadedAddon = initAddon(addonClassLoader, onClasspath);
            if (onClasspath && (addonInfo = addonClassLoader.getAddonInfo()) != null) {
                this.addonService.setClassPathAddonNamespace(addonInfo.getNamespace());
            }
            this.addonService.addLoadedAddon(loadedAddon, context == AddonPrepareContext.RUNTIME);
        } catch (AddonInitException exception) {
            this.logger.error("Could not initialize addon", exception);
            if (addonClassLoader != null) {
                addonClassLoader.close();
            }
        } catch (AddonLoadException | UnsupportedAddonException exception2) {
            this.logger.error("Could not load addon", exception2);
        }
    }

    public LoadedAddon initAddon(AddonClassLoader classLoader, boolean onClasspath) {
        InstalledAddonInfo addonInfo = classLoader.getAddonInfo();
        String mainClassName = addonInfo.getMainClass();
        if (mainClassName == null) {
            throw new AddonInitException("No main class found");
        }
        Class<?> mainClass = null;
        try {
            mainClass = classLoader.loadClass(mainClassName);
            List<LoadedAddonClassTransformer> transformers = new ArrayList<>();
            if (addonInfo.getTransformers() != null) {
                for (String transformer : addonInfo.getTransformers()) {
                    transformers.add(parseTransformer(classLoader, transformer));
                }
            }
            return new LoadedAddon(classLoader, mainClass, transformers, loadMixinConfigs(classLoader, addonInfo, onClasspath), addonInfo, onClasspath);
        } catch (ClassNotFoundException | LinkageError exception) {
            throw new AddonInitException("Failed to load main class " + String.valueOf(mainClass), exception);
        }
    }

    private LoadedAddonClassTransformer parseTransformer(AddonClassLoader loader, String name) {
        try {
            Class<?> transformerClass = loader.loadClass(name);
            Class<?> itf = loader.loadClass("net.labymod.api.addon.transform.AddonClassTransformer");
            if (!itf.isAssignableFrom(transformerClass)) {
                throw new AddonLoadException("Transformer " + name + " doesn't implement AddonTransformer");
            }
            Object transformer = itf.cast(transformerClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            return new WrappedAddonClassTransformer(itf, transformer);
        } catch (ReflectiveOperationException e) {
            throw new AddonInitException("Failed to instantiate addon transformer " + name);
        }
    }

    private List<String> loadMixinConfigs(ClassLoader classLoader, InstalledAddonInfo addonInfo, boolean onClasspath) {
        List<String> mixinConfigurations = new ArrayList<>();
        String runningVersion = this.labyModLoader.version().toString();
        if (onClasspath) {
            PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            AtomicInteger index = new AtomicInteger();
            for (URL url : platformClassloader.getPotentialClasspathAddons()) {
                try {
                    mixinConfigurations.addAll(AddonResourceFinder.discoverResources(url, location -> {
                        String name = location.name();
                        return name.startsWith(runningVersion + "-") && name.endsWith(".mixins.json");
                    }, location2 -> {
                        String name = location2.name();
                        String uniqueName = name.replace("-0", "-" + index.getAndIncrement());
                        try {
                            DefaultLabyModLoader.getInstance().registerMixinResourceSource(uniqueName, location2.uri().toURL());
                            return uniqueName;
                        } catch (MalformedURLException e) {
                            return name;
                        }
                    }));
                } catch (Exception exception) {
                    exception.printStackTrace(System.err);
                }
            }
            return mixinConfigurations;
        }
        String mixinConfiguration = String.format(Locale.ROOT, "%s-%s.mixins.json", runningVersion, addonInfo.getNamespace());
        URL resource = classLoader.getResource(mixinConfiguration);
        if (resource != null) {
            mixinConfigurations.add(mixinConfiguration);
        }
        return mixinConfigurations;
    }

    private void prepareLoadedAddons() {
        Collection<LoadedAddon> loadedAddons = this.addonService.getLoadedAddons();
        List<LoadedAddon> incompatibleAddons = new ArrayList<>();
        for (LoadedAddon loadedAddon : loadedAddons) {
            String[] incompatibles = loadedAddon.info().getIncompatibleAddons();
            if (incompatibles != null) {
                int length = incompatibles.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        String incompatible = incompatibles[i];
                        Optional<LoadedAddon> optionalAddon = this.addonService.getOptionalAddon(incompatible);
                        if (optionalAddon.isEmpty()) {
                            i++;
                        } else {
                            LoadedAddon incompatibleAddon = optionalAddon.get();
                            this.logger.info("The addon \"{}\" is unloaded because an incompatible addon was found! ({})", loadedAddon.info().getNamespace(), incompatibleAddon.info().getNamespace());
                            incompatibleAddons.add(loadedAddon);
                            break;
                        }
                    }
                }
            }
        }
        if (!incompatibleAddons.isEmpty()) {
            this.addonService.unloadAddons(incompatibleAddons);
        }
        Collection<LoadedAddon> loadedAddons2 = this.addonService.getLoadedAddons();
        for (LoadedAddon loadedAddon2 : loadedAddons2) {
            this.logger.info("Initializing Addon {} v{}", loadedAddon2.info().getNamespace(), loadedAddon2.info().getVersion());
            processEntrypoints(loadedAddon2);
            prepareEarlyAddonTransformation(loadedAddon2);
        }
        invokeAddonEntryPoints(AddonEntryPoint.Point.EARLY_INITIALIZATION, null);
    }

    public void invokeAddonEntryPoints(AddonEntryPoint.Point point, LoadedAddon addon) {
        List<WrappedAddonEntrypoint> sortedEntryPoints = this.addonEntrypoints.get(point);
        if (sortedEntryPoints == null) {
            return;
        }
        sortedEntryPoints.sort(Comparator.comparingInt((v0) -> {
            return v0.getPriority();
        }));
        for (WrappedAddonEntrypoint sortedEntrypoint : sortedEntryPoints) {
            LoadedAddon entrypointAddon = sortedEntrypoint.addon();
            if (addon == null || addon.equals(entrypointAddon)) {
                try {
                    EntrypointInvoker.invoke(sortedEntrypoint.entrypoint(), sortedEntrypoint.addon().getClassLoader());
                } catch (AddonLoadException exception) {
                    Logging logging = this.logger;
                    Object[] objArr = new Object[3];
                    objArr[0] = entrypointAddon.info().getNamespace();
                    objArr[1] = sortedEntrypoint.entrypoint().name();
                    objArr[2] = exception.getCause() == null ? exception : exception.getCause();
                    logging.error("The addon \"{}\" is unloaded because the \"{}\" entrypoint threw an exception!", objArr);
                    this.addonService.unloadAddon(entrypointAddon);
                }
            }
        }
    }

    private void processEntrypoints(LoadedAddon addon) {
        InstalledAddonInfo info = addon.info();
        Map<AddonEntryPoint.Point, List<AddonEntrypoint>> addonEntrypoints = info.getEntrypoints();
        if (addonEntrypoints == null || addonEntrypoints.isEmpty()) {
            return;
        }
        for (Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry : addonEntrypoints.entrySet()) {
            AddonEntryPoint.Point point = entry.getKey();
            List<WrappedAddonEntrypoint> wrappedEntrypoints = this.addonEntrypoints.computeIfAbsent(point, k -> {
                return new ArrayList();
            });
            for (AddonEntrypoint addonEntrypoint : entry.getValue()) {
                if (addonEntrypoint.version() == null || addonEntrypoint.version().equals(this.labyModLoader.version().toString())) {
                    wrappedEntrypoints.add(new WrappedAddonEntrypoint(addon, addonEntrypoint));
                }
            }
        }
    }

    private void prepareEarlyAddonTransformation(LoadedAddon addon) {
        ClassLoader classLoader = addon.getClassLoader();
        if (!(classLoader instanceof AddonClassLoader)) {
            throw new AddonLoadException("Unexpected AddonClassloader: " + addon.getClassLoader().getClass().getName());
        }
        AddonClassLoader classLoader2 = (AddonClassLoader) classLoader;
        InstalledAddonInfo addonInfo = addon.info();
        PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
        platformClassloader.getAccessWidener().findAndCreateAccessWidener(classLoader2, addonInfo.getNamespace(), ReflectLabyModLoader.invokeGetGameVersion());
        if (addonInfo.getEarlyTransformers() != null) {
            for (String earlyTransformer : addonInfo.getEarlyTransformers()) {
                platformClassloader.registerTransformer(PlatformClassloader.TransformerPhase.PRE, earlyTransformer);
            }
        }
    }

    public AddonClassLoader loadAddonClassLoader(InstalledAddonInfo addonInfo, Path path) throws IOException {
        String namespace = addonInfo.getNamespace();
        if (this.addonService.getAddon(addonInfo.getNamespace()).isPresent()) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Another addon was found that has the same namespace. (%s)", namespace));
        }
        if (!addonInfo.isCurrentOsSupported()) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Addon %s cannot run on %s", addonInfo.getDisplayName(), OperatingSystem.getPlatform()));
        }
        String gameVersion = ReflectLabyModLoader.invokeGetGameVersion();
        if (!addonInfo.getCompatibleMinecraftVersions().isCompatible(VersionDeserializer.from(gameVersion))) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Addon %s cannot run on Minecraft %s", addonInfo.getDisplayName(), gameVersion));
        }
        String mainClassName = addonInfo.getMainClass();
        if (mainClassName == null) {
            throw new AddonLoadException("No main class specified in the addon.json");
        }
        if (this.labyModLoader.isDevelopmentEnvironment()) {
            try {
                path = this.addonRemapper.remap(path);
            } catch (Throwable throwable) {
                this.logger.error("Error occurred while remapping addon", throwable);
            }
        }
        Collection<URL> classpath = new ArrayList<>(1);
        classpath.add(path.toUri().toURL());
        if (addonInfo.getMavenDependencies() != null) {
            classpath.addAll(Arrays.asList(installDependencies(addonInfo.getMavenDependencies())));
        }
        AddonClassLoader addonClassLoader = new AddonClassLoader((URL[]) classpath.toArray(new URL[0]), AddonPreparer.class.getClassLoader(), addonInfo);
        PlatformEnvironment.getPlatformClassloader().registerChildClassloader(namespace, addonClassLoader);
        return addonClassLoader;
    }

    private URL[] installDependencies(MavenDependency[] dependencies) {
        URL[] urls = new URL[dependencies.length];
        try {
            IOUtil.createDirectories(Constants.Files.LIBRARIES);
            for (int i = 0; i < dependencies.length; i++) {
                MavenDependency dependency = dependencies[i];
                Path path = this.addonLoader.libraryLoader().loadMavenDependency(dependency);
                urls[i] = path.toUri().toURL();
            }
            return urls;
        } catch (IOException exception) {
            throw new AddonLoadException("Failed to install maven dependencies for an addon", exception);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint.class */
    private static final class WrappedAddonEntrypoint extends Record {
        private final LoadedAddon addon;
        private final AddonEntrypoint entrypoint;

        private WrappedAddonEntrypoint(LoadedAddon addon, AddonEntrypoint entrypoint) {
            this.addon = addon;
            this.entrypoint = entrypoint;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedAddonEntrypoint.class), WrappedAddonEntrypoint.class, "addon;entrypoint", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->addon:Lnet/labymod/api/addon/LoadedAddon;", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->entrypoint:Lnet/labymod/api/models/addon/info/AddonEntrypoint;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedAddonEntrypoint.class), WrappedAddonEntrypoint.class, "addon;entrypoint", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->addon:Lnet/labymod/api/addon/LoadedAddon;", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->entrypoint:Lnet/labymod/api/models/addon/info/AddonEntrypoint;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedAddonEntrypoint.class, Object.class), WrappedAddonEntrypoint.class, "addon;entrypoint", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->addon:Lnet/labymod/api/addon/LoadedAddon;", "FIELD:Lnet/labymod/core/addon/loader/prepare/AddonPreparer$WrappedAddonEntrypoint;->entrypoint:Lnet/labymod/api/models/addon/info/AddonEntrypoint;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public LoadedAddon addon() {
            return this.addon;
        }

        public AddonEntrypoint entrypoint() {
            return this.entrypoint;
        }

        public int getPriority() {
            return this.entrypoint.priority();
        }
    }
}

package net.labymod.core.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.mixin.dynamic.DynamicMixinUtil;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.Version;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.service.Service;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.loader.isolated.IsolatedLibraryLoader;
import net.labymod.core.loader.isolated.ManifestIsolatedLibraryLoader;
import net.labymod.core.loader.isolated.jna.JNALoader;
import net.labymod.core.loader.isolated.lwjgl.LWJGL2Filter;
import net.labymod.core.loader.isolated.lwjgl.LWJGL3NativeFilter;
import net.labymod.core.loader.isolated.lwjgl.LWJGL3Preloader;
import net.labymod.core.loader.isolated.lwjgl.ThinLWJGL;
import net.labymod.core.main.AssetLoader;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.util.crashtracker.CrashTracker;
import net.labymod.util.property.SystemProperties;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.transformer.Config;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/DefaultLabyModLoader.class */
public class DefaultLabyModLoader implements LabyModLoader {
    private static final String LABYMOD_REFMAP_SUFFIX = "labymod4.refmap.json";
    public static final PrintStream SYSOUT = System.out;
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("LabyModLoader");
    private static final String DEFAULT_RELEASE_CHANNEL = "production";
    private static LabyModLoader instance;
    private List<String> arguments;
    private Path gameDirectory;
    private Path assetsDirectory;
    private String profile;
    private String rawUuid;
    private UUID uuid;
    private boolean labyModDevelopmentEnvironment;
    private boolean addonDevelopmentEnvironment;
    private Version version;
    private String effectiveReleaseChannel;
    private final Map<String, URL> mixinResourceSources = new HashMap();
    private final List<CrashTracker.MixinConfig> mixinConfigs = new ArrayList();

    public static LabyModLoader getInstance() {
        if (instance == null) {
            instance = new DefaultLabyModLoader();
        }
        return instance;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public void gameOptions(List<String> arguments, Path gameDirectory, Path assetsDirectory, String profile, boolean labyModDevEnvironment, boolean addonDevEnvironment, String version) {
        this.arguments = arguments;
        this.gameDirectory = gameDirectory;
        this.assetsDirectory = assetsDirectory;
        this.profile = profile;
        this.labyModDevelopmentEnvironment = labyModDevEnvironment;
        this.addonDevelopmentEnvironment = addonDevEnvironment;
        this.version = VersionDeserializer.from(version);
        customizedArguments();
        CrashTracker.setAdditionalDataSupplier(() -> {
            JsonObject jsonObject = new JsonObject();
            JsonArray installedAddons = new JsonArray();
            for (LoadedAddon loadedAddon : DefaultAddonService.getInstance().getLoadedAddons()) {
                InstalledAddonInfo info = loadedAddon.info();
                if (info != null) {
                    JsonObject addonObject = new JsonObject();
                    addonObject.addProperty("namespace", info.getNamespace());
                    addonObject.addProperty("version", info.getVersion());
                    addonObject.addProperty("local", Boolean.valueOf(!info.isFlintAddon()));
                    installedAddons.add(addonObject);
                }
            }
            if (installedAddons.size() != 0) {
                jsonObject.add("installedAddons", installedAddons);
            }
            JsonArray installedMods = new JsonArray();
            for (KeyValue<ModLoader> element : Laby.references().modLoaderRegistry().getElements()) {
                ModLoader loader = element.getValue();
                if (loader != null) {
                    for (ModInfo info2 : loader.getModInfos()) {
                        JsonObject modObject = new JsonObject();
                        modObject.addProperty("modId", info2.getId());
                        modObject.addProperty("version", info2.version().toString());
                        modObject.addProperty("loader", loader.getId());
                        installedMods.add(modObject);
                    }
                }
            }
            if (installedMods.size() != 0) {
                jsonObject.add("installedMods", installedMods);
            }
            return jsonObject.toString();
        });
        if (!this.labyModDevelopmentEnvironment) {
            AssetLoader.getInstance().loadAssets();
        }
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public void loadIsolatedLibraries() {
        if (this.labyModDevelopmentEnvironment) {
            return;
        }
        IsolatedLibraryLoader isolatedLibraryLoader = new ManifestIsolatedLibraryLoader();
        isolatedLibraryLoader.addFilter(new LWJGL3NativeFilter());
        isolatedLibraryLoader.addFinishHandler(new LWJGL3Preloader());
        isolatedLibraryLoader.addFilter(new LWJGL2Filter());
        isolatedLibraryLoader.addFinishHandler(new ThinLWJGL());
        isolatedLibraryLoader.addFinishHandler(new JNALoader());
        isolatedLibraryLoader.load(PlatformEnvironment.getPlatformClassloader().getPlatformClassloader());
    }

    private void customizedArguments() {
        List<String> customizedArguments = new ArrayList<>(this.arguments);
        customizedArguments.add("--gameDir");
        customizedArguments.add(IOUtil.toFile(this.gameDirectory).getAbsolutePath());
        customizedArguments.add("--assetsDir");
        customizedArguments.add(IOUtil.toFile(this.assetsDirectory).getAbsolutePath());
        customizedArguments.add("--version");
        customizedArguments.add(this.version.toString());
        this.arguments = customizedArguments;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public void loadAddons() {
        DynamicMixinUtil.setLoadResources(PlatformEnvironment::getResources);
        CrashTracker.setAddonNamespaceFunction(cls -> {
            try {
                ClassLoader classLoader = cls.getClassLoader();
                return (String) DefaultAddonService.getInstance().getAddon(classLoader).map((v0) -> {
                    return v0.info();
                }).map((v0) -> {
                    return v0.getNamespace();
                }).orElse(null);
            } catch (Exception e) {
                return null;
            }
        });
        CrashTracker.setAddonExistsFunction(namespace -> {
            return Boolean.valueOf(DefaultAddonService.getInstance().getAddonInfo(namespace) != null);
        });
        CrashTracker.setMixinConfigSupplier(() -> {
            return this.mixinConfigs;
        });
        ((Service) MappingService.instance()).prepareSynchronously();
        DefaultAddonService addonService = DefaultAddonService.getInstance();
        addonService.prepareSynchronously();
        for (Config config : Mixins.getConfigs()) {
            CrashTracker.MixinConfig mixinConfig = convertMixinConfig(config);
            if (mixinConfig != null) {
                this.mixinConfigs.add(mixinConfig);
            }
        }
        Collection<LoadedAddon> addons = addonService.getLoadedAddons();
        if (addons.isEmpty()) {
            return;
        }
        addons.forEach(addon -> {
            addon.getTransformers().forEach((v0) -> {
                v0.init();
            });
        });
    }

    private CrashTracker.MixinConfig convertMixinConfig(Config config) {
        if (config == null) {
            return null;
        }
        String mixinName = config.getName();
        String refMapName = mixinName.replace(".mixins.json", ".refmap.json");
        IMixinConfig mixinConfig = config.getConfig();
        try {
            Object refMapperConfig = mixinConfig.getClass().getField("refMapperConfig").get(mixinConfig);
            if (refMapperConfig instanceof String) {
                refMapName = (String) refMapperConfig;
            }
        } catch (Exception e) {
        }
        String modId = null;
        if (mixinConfig.hasDecoration("fabric-modId")) {
            modId = (String) mixinConfig.getDecoration("fabric-modId");
        } else if (mixinConfig.hasDecoration("labymod-namespace")) {
            modId = (String) mixinConfig.getDecoration("labymod-namespace");
        }
        return new CrashTracker.MixinConfig(mixinName, refMapName, modId, convertMixinConfig(config.getParent()));
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public InputStream getMixinServiceResourceAsStream(String name, InputStream parentInputStream) {
        if (this.addonDevelopmentEnvironment && name.endsWith(LABYMOD_REFMAP_SUFFIX)) {
            return null;
        }
        URL mixinResourceSource = this.mixinResourceSources.get(name);
        if (mixinResourceSource != null) {
            try {
                return mixinResourceSource.openStream();
            } catch (IOException e) {
            }
        }
        for (LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            InputStream stream = addon.getClassLoader().getResourceAsStream(name);
            if (stream != null) {
                return stream;
            }
        }
        return parentInputStream;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public byte[] getMixinServiceClassBytes(String name, String transformedName, byte[] classBytes) throws IOException {
        if (classBytes != null) {
            return classBytes;
        }
        for (LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            URLClassLoader appClassLoader = (URLClassLoader) addon.getClassLoader();
            InputStream classStream = null;
            try {
                String resourcePath = transformedName.replace('.', '/').concat(".class");
                classStream = appClassLoader.getResourceAsStream(resourcePath);
                if (classStream != null) {
                    byte[] bytes = IOUtil.readBytes(classStream);
                    IOUtil.closeSilent(classStream);
                    return bytes;
                }
                IOUtil.closeSilent(classStream);
            } catch (Exception e) {
                IOUtil.closeSilent(classStream);
            } catch (Throwable th) {
                IOUtil.closeSilent(classStream);
                throw th;
            }
        }
        return classBytes;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public void registerMixinResourceSource(String name, URL url) {
        this.mixinResourceSources.put(name, url);
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public Version version() {
        return this.version;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public List<String> getArguments() {
        return this.arguments;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public Path getGameDirectory() {
        return this.gameDirectory;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public Path getAssetsDirectory() {
        return this.assetsDirectory;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public String getProfile() {
        return this.profile;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public String getEffectiveReleaseChannel() {
        if (this.effectiveReleaseChannel == null) {
            if (isAddonDevelopmentEnvironment()) {
                this.effectiveReleaseChannel = SystemProperties.ADDON_RELEASE_CHANNEL.getOrDefault(DEFAULT_RELEASE_CHANNEL);
            } else {
                String currentReleaseChannel = BuildData.releaseType();
                if (currentReleaseChannel == null) {
                    this.effectiveReleaseChannel = DEFAULT_RELEASE_CHANNEL;
                } else {
                    this.effectiveReleaseChannel = currentReleaseChannel;
                }
            }
        }
        return this.effectiveReleaseChannel;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public boolean isLabyModDevelopmentEnvironment() {
        return this.labyModDevelopmentEnvironment;
    }

    @Override // net.labymod.api.loader.LabyModLoader
    public boolean isAddonDevelopmentEnvironment() {
        return this.addonDevelopmentEnvironment;
    }

    public String getRawUniqueId() {
        return this.rawUuid;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    private boolean hasArgument(String argumentKey) {
        return this.arguments.stream().anyMatch(argument -> {
            return argument.equalsIgnoreCase(argumentKey);
        });
    }
}

package net.labymod.api.addon;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/LoadedAddon.class */
public class LoadedAddon {
    private final ClassLoader classLoader;
    private final Class<?> mainClass;
    private final List<LoadedAddonClassTransformer> transformers;
    private final List<String> mixinConfigs;
    private final InstalledAddonInfo info;
    private final List<Widget> settings;
    private final boolean classpath;
    private Version version;

    @Nullable
    private Object instance;

    public LoadedAddon(ClassLoader classLoader, Class<?> mainClass, List<LoadedAddonClassTransformer> transformers, List<String> mixinConfigs, InstalledAddonInfo info) {
        this(classLoader, mainClass, transformers, mixinConfigs, info, false);
    }

    @ApiStatus.Internal
    public LoadedAddon(ClassLoader classLoader, Class<?> mainClass, List<LoadedAddonClassTransformer> transformers, List<String> mixinConfigs, InstalledAddonInfo info, boolean classPath) {
        this.settings = new ArrayList();
        this.classLoader = classLoader;
        this.mainClass = mainClass;
        this.transformers = transformers;
        this.mixinConfigs = mixinConfigs;
        this.info = info;
        this.classpath = classPath;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Class<?> getMainClass() {
        return this.mainClass;
    }

    public List<LoadedAddonClassTransformer> getTransformers() {
        return this.transformers;
    }

    public List<String> getMixinConfigs() {
        return this.mixinConfigs;
    }

    public InstalledAddonInfo info() {
        return this.info;
    }

    public List<Widget> getSettings() {
        return this.settings;
    }

    public Version getVersion() {
        if (this.version == null) {
            this.version = VersionDeserializer.from(info().getVersion());
        }
        return this.version;
    }

    @ApiStatus.Internal
    public boolean isClasspath() {
        return this.classpath;
    }

    @ApiStatus.Internal
    @Nullable
    public Object getInstance() {
        return this.instance;
    }

    @ApiStatus.Internal
    public void setInstance(@Nullable Object instance) {
        this.instance = instance;
    }

    public String toString() {
        return info().getNamespace() + " v" + info().getVersion();
    }
}

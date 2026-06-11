package net.labymod.api.models.addon.info;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.models.addon.annotation.AddonEntryPoint;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/info/InstalledAddonInfo.class */
public class InstalledAddonInfo {
    private String namespace;
    private String[] incompatibles;
    private OperatingSystem[] os;
    private VersionCompatibility compatibleMinecraftVersions;
    private AddonMeta[] meta;
    private MavenDependency[] mavenDependencies;
    private AddonDependency[] addonDependencies;
    private String displayName;
    private String description;
    private String version;
    private String author;
    private String mainClass;
    private Map<AddonEntryPoint.Point, List<AddonEntrypoint>> entrypoints;
    private String[] earlyTransformers;
    private String[] transformers;
    private int requiredLabyModBuild;
    private String releaseChannel;
    private boolean flint;
    private String directory;
    private String fileName;
    private String fileHash;

    public String getNamespace() {
        return this.namespace;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVersion() {
        return this.version;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDirectory() {
        return this.directory;
    }

    public Path getDirectoryPath() {
        return Paths.get(this.directory, new String[0]);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileHash() {
        return this.fileHash;
    }

    public Path getPath() {
        return Paths.get(this.directory, new String[0]).resolve(this.fileName);
    }

    public VersionCompatibility getCompatibleMinecraftVersions() {
        return this.compatibleMinecraftVersions;
    }

    public String[] getIncompatibleAddons() {
        return this.incompatibles;
    }

    public AddonMeta[] meta() {
        return this.meta;
    }

    public boolean hasMeta(AddonMeta meta) {
        for (AddonMeta presentMeta : this.meta) {
            if (presentMeta == meta) {
                return true;
            }
        }
        return false;
    }

    public boolean isIncompatibleWith(String addonId) {
        for (String incompatible : this.incompatibles) {
            if (incompatible.equalsIgnoreCase(addonId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentOsSupported() {
        if (this.os == null || this.os.length == 0) {
            return true;
        }
        OperatingSystem platform = OperatingSystem.getPlatform();
        for (OperatingSystem os : this.os) {
            if (platform == os) {
                return true;
            }
        }
        return false;
    }

    public OperatingSystem[] getSupportedOperatingSystems() {
        return this.os;
    }

    public MavenDependency[] getMavenDependencies() {
        return this.mavenDependencies;
    }

    @Deprecated
    public AddonDependency[] getAddonDependencies() {
        return this.addonDependencies;
    }

    public AddonDependency[] getCompatibleAddonDependencies(Version version) {
        if (this.addonDependencies == null) {
            return new AddonDependency[0];
        }
        List<AddonDependency> compatible = new ArrayList<>();
        for (AddonDependency dependency : this.addonDependencies) {
            Optional<VersionCompatibility> compatability = dependency.getCompatability();
            if (!compatability.isPresent() || compatability.get().isCompatible(version)) {
                compatible.add(dependency);
            }
        }
        return (AddonDependency[]) compatible.toArray(new AddonDependency[0]);
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public int getRequiredLabyModBuild() {
        return this.requiredLabyModBuild;
    }

    public String getReleaseChannel() {
        return this.releaseChannel;
    }

    public Map<AddonEntryPoint.Point, List<AddonEntrypoint>> getEntrypoints() {
        return this.entrypoints;
    }

    public String[] getEarlyTransformers() {
        return this.earlyTransformers;
    }

    public String[] getTransformers() {
        return this.transformers;
    }

    public boolean isFlintAddon() {
        return this.flint;
    }

    public boolean hasAddonDependencies() {
        return (this.addonDependencies == null || this.addonDependencies.length == 0) ? false : true;
    }

    public InstalledAddonInfo merge(InstalledAddonInfo other) {
        InstalledAddonInfo merged = new InstalledAddonInfo();
        merged.namespace = getValue(this.namespace, other.namespace);
        merged.displayName = getValue(this.displayName, other.displayName);
        merged.description = getValue(this.description, other.description);
        merged.version = getValue(this.version, other.version);
        merged.author = getValue(this.author, other.author);
        merged.mainClass = getValue(this.mainClass, other.mainClass);
        merged.os = (this.os == null || this.os.length == 0) ? other.os : this.os;
        merged.compatibleMinecraftVersions = this.compatibleMinecraftVersions;
        merged.meta = this.meta;
        ArrayList arrayList = new ArrayList();
        addAll(arrayList, this.mavenDependencies);
        addAll(arrayList, other.mavenDependencies);
        merged.mavenDependencies = (MavenDependency[]) arrayList.toArray(new MavenDependency[0]);
        ArrayList arrayList2 = new ArrayList();
        addAll(arrayList2, this.addonDependencies);
        addAll(arrayList2, other.addonDependencies);
        merged.addonDependencies = (AddonDependency[]) arrayList2.toArray(new AddonDependency[0]);
        ArrayList arrayList3 = new ArrayList();
        addAll(arrayList3, this.incompatibles);
        addAll(arrayList3, other.incompatibles);
        merged.incompatibles = (String[]) arrayList3.toArray(new String[0]);
        ArrayList arrayList4 = new ArrayList();
        addAll(arrayList4, this.earlyTransformers);
        addAll(arrayList4, other.earlyTransformers);
        merged.earlyTransformers = (String[]) arrayList4.toArray(new String[0]);
        ArrayList arrayList5 = new ArrayList();
        addAll(arrayList5, this.transformers);
        addAll(arrayList5, other.transformers);
        merged.transformers = (String[]) arrayList5.toArray(new String[0]);
        Map<AddonEntryPoint.Point, List<AddonEntrypoint>> entrypoints = new HashMap<>();
        if (this.entrypoints != null) {
            for (Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry : this.entrypoints.entrySet()) {
                entrypoints.computeIfAbsent(entry.getKey(), k -> {
                    return new ArrayList();
                }).addAll(entry.getValue());
            }
        }
        if (other.entrypoints != null) {
            for (Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry2 : other.entrypoints.entrySet()) {
                entrypoints.computeIfAbsent(entry2.getKey(), k2 -> {
                    return new ArrayList();
                }).addAll(entry2.getValue());
            }
        }
        merged.entrypoints = entrypoints;
        return merged;
    }

    private String getValue(String value, String otherValue) {
        return (value == null || value.isEmpty()) ? otherValue : value;
    }

    private <T> void addAll(Collection<? super T> collection, T... elements) {
        if (elements == null) {
            return;
        }
        Collections.addAll(collection, elements);
    }
}

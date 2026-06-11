package net.labymod.core.client.resources.pack;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.client.resources.pack.ResourceFile;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.event.client.resources.pack.IncompatibleResourcePacksEvent;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/pack/AbstractResourcePackScanner.class */
public abstract class AbstractResourcePackScanner implements ResourcePackScanner {
    private static final String SEPARATOR = "=".repeat(75);
    protected static final Logging LOGGER = Logging.getLogger();
    protected static final String IGNORED_FILES = "labymod/ignored_files.json";
    private final Map<String, Set<String>> blacklistedFiles = new HashMap();

    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void addBlacklist(ResourceFile file) {
        this.blacklistedFiles.computeIfAbsent(file.baseDirectory(), s -> {
            return new HashSet();
        }).add(file.fileName());
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void removeBlacklist(ResourceFile file) {
        Set<String> blacklistedFiles = this.blacklistedFiles.get(file.baseDirectory());
        if (blacklistedFiles == null || blacklistedFiles.isEmpty()) {
            return;
        }
        blacklistedFiles.remove(file.fileName());
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void removeBlacklist(String fileName) {
        for (Set<String> blacklistedFiles : this.blacklistedFiles.values()) {
            blacklistedFiles.removeIf(name -> {
                return name.equals(fileName);
            });
        }
    }

    public Map<String, Set<String>> getBlacklistedFiles() {
        return this.blacklistedFiles;
    }

    protected void collectIgnoredFiles(Reader reader, Collection<String> ignoredFiles) {
        String[] files = (String[]) GsonUtil.DEFAULT_GSON.fromJson(reader, String[].class);
        if (files == null || files.length == 0) {
            return;
        }
        ignoredFiles.addAll(Arrays.asList(files));
    }

    protected void analyzeResourcePackFile(Collection<String> blacklistedFiles, Collection<String> ignoredFiles, Collection<String> unsupportedFiles, ResourceLocation location) {
        String path = location.getPath();
        String name = path.substring(path.lastIndexOf(47) + 1);
        if (ignoredFiles.contains(name)) {
            return;
        }
        for (String blacklistedFile : blacklistedFiles) {
            if (blacklistedFile.endsWith(name)) {
                unsupportedFiles.add(path);
            }
        }
    }

    protected final void onScanned(Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        Collection<IncompatibleResourcePack> incompatibleResourcePacks2 = Collections.unmodifiableCollection(incompatibleResourcePacks);
        printDebugMessage(incompatibleResourcePacks2);
        Laby.fireEvent(new IncompatibleResourcePacksEvent(incompatibleResourcePacks2));
    }

    private void printDebugMessage(Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        LOGGER.debug(SEPARATOR, new Object[0]);
        LOGGER.debug("Incompatible resource packs were found", new Object[0]);
        for (IncompatibleResourcePack incompatibleResourcePack : incompatibleResourcePacks) {
            LOGGER.debug("Name: {}", incompatibleResourcePack.name());
            Collection<String> ignoredFiles = incompatibleResourcePack.ignoredFiles();
            if (!ignoredFiles.isEmpty()) {
                LOGGER.debug("Some files have been marked as ignored, which are on the blacklist:", new Object[0]);
                for (String ignoredFile : ignoredFiles) {
                    LOGGER.debug("\t - {}", ignoredFile);
                }
            }
            LOGGER.debug("Following files which are on the blacklist have been modified:", new Object[0]);
            for (String unsupportedFile : incompatibleResourcePack.unsupportedFiles()) {
                LOGGER.debug("\t - {}", unsupportedFile);
            }
            LOGGER.debug("", new Object[0]);
        }
        LOGGER.debug(SEPARATOR, new Object[0]);
        LOGGER.debug("Some features may not work", new Object[0]);
    }
}

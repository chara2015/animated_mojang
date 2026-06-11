package net.labymod.v1_16_5.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.pack.AbstractResourcePackScanner;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/resources/pack/VersionedResourcePackScanner.class */
@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner {
    private static final String DEFAULT_NAMESPACE = "minecraft";

    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void scan() {
        ach resourceManager = djz.C().N();
        List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map(this::scanPack).filter((v0) -> {
            return v0.isIncompatible();
        }).toList();
        onScanned(incompatiblePacks);
    }

    private boolean isExternalPack(abj pack) {
        return (pack instanceof abh) || (pack instanceof abi);
    }

    private IncompatibleResourcePack scanPack(abj pack) {
        Collection<String> ignoredFiles = collectIgnoredFiles(pack);
        Set<String> unsupportedFiles = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : getBlacklistedFiles().entrySet()) {
            String directory = entry.getKey();
            Set<String> blacklistedFiles = entry.getValue();
            scanPack(pack, directory, location -> {
                analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation) location);
            });
        }
        return new IncompatibleResourcePack(pack.a(), ignoredFiles, unsupportedFiles);
    }

    private void scanPack(abj pack, String baseDirectory, Consumer<vk> output) {
        Collection<vk> locations = pack.a(abk.a, "minecraft", baseDirectory, Integer.MAX_VALUE, s -> {
            return true;
        });
        for (vk location : locations) {
            output.accept(location);
        }
    }

    private Collection<String> collectIgnoredFiles(abj pack) {
        Set<String> ignoredFiles = new HashSet<>();
        for (String namespace : pack.a(abk.a)) {
            try {
                InputStream resource = pack.a(abk.a, new vk(namespace, "labymod/ignored_files.json"));
                try {
                    InputStreamReader reader = new InputStreamReader(resource);
                    try {
                        collectIgnoredFiles(reader, ignoredFiles);
                        reader.close();
                    } catch (Throwable th) {
                        try {
                            reader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException exception) {
                    LOGGER.error("Could not read resource file", exception);
                }
            } catch (IOException e) {
            }
        }
        return ignoredFiles;
    }
}

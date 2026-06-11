package net.labymod.v1_12_2.client.resources.pack;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/pack/VersionedResourcePackScanner.class */
@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner {
    private static final String DEFAULT_NAMESPACE = "minecraft";

    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void scan() {
        ceu packRepository = bib.z().P();
        List<a> entries = packRepository.e();
        List<IncompatibleResourcePack> incompatibleResourcePacks = new ArrayList<>();
        for (a entry : entries) {
            cer resourcePack = entry.c();
            if (!isExternalPack(resourcePack)) {
                IncompatibleResourcePack incompatibleResourcePack = scanPack(resourcePack);
                if (!incompatibleResourcePack.isCompatible()) {
                    incompatibleResourcePacks.add(incompatibleResourcePack);
                }
            }
        }
        onScanned(incompatibleResourcePacks);
    }

    private boolean isExternalPack(cer pack) {
        return pack instanceof cej;
    }

    private IncompatibleResourcePack scanPack(cer pack) {
        Collection<String> ignoredFiles = collectIgnoredFiles(pack);
        Set<String> unsupportedFiles = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : getBlacklistedFiles().entrySet()) {
            String directory = entry.getKey();
            Set<String> blacklistedFiles = entry.getValue();
            scanPack(pack, directory, location -> {
                analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation) location);
            });
        }
        return new IncompatibleResourcePack(pack.b(), ignoredFiles, unsupportedFiles);
    }

    private void scanPack(cer pack, String baseDirectory, Consumer<nf> output) {
        ResourceLister.listResources(pack, "minecraft", baseDirectory, output);
    }

    private Collection<String> collectIgnoredFiles(cer pack) {
        Set<String> ignoredFiles = new HashSet<>();
        for (String namespace : pack.c()) {
            nf location = new nf(namespace, "labymod/ignored_files.json");
            if (pack.b(location)) {
                try {
                    InputStreamReader reader = new InputStreamReader(pack.a(location));
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
            }
        }
        return ignoredFiles;
    }
}

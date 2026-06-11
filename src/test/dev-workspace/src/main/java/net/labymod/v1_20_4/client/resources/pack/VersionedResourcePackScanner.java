package net.labymod.v1_20_4.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.pack.AbstractResourcePackScanner;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/resources/pack/VersionedResourcePackScanner.class */
@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner {
    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void scan() {
        aqi resourceManager = evi.O().Z();
        List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map(this::scanPack).filter((v0) -> {
            return v0.isIncompatible();
        }).toList();
        onScanned(incompatiblePacks);
    }

    private boolean isExternalPack(aow pack) {
        if (!(pack instanceof WrappedPackResources)) {
            return (pack instanceof aou) || (pack instanceof aoy) || (pack instanceof aoq);
        }
        WrappedPackResources wrappedPackResources = (WrappedPackResources) pack;
        return isExternalPack(wrappedPackResources.delegate());
    }

    private IncompatibleResourcePack scanPack(aow pack) {
        Collection<String> ignoredFiles = collectIgnoredFiles(pack);
        Set<String> unsupportedFiles = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : getBlacklistedFiles().entrySet()) {
            String directory = entry.getKey();
            Set<String> blacklistedFiles = entry.getValue();
            scanPack(pack, directory, (location, supplier) -> {
                analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation) location);
            });
        }
        return new IncompatibleResourcePack(pack.a(), ignoredFiles, unsupportedFiles);
    }

    private void scanPack(aow pack, String baseDirectory, a output) {
        pack.a(aox.a, Namespaces.MINECRAFT, baseDirectory, output);
    }

    private Collection<String> collectIgnoredFiles(aow pack) {
        Set<String> ignoredFiles = new HashSet<>();
        for (String namespace : pack.a(aox.a)) {
            aqa<InputStream> resource = pack.a(aox.a, new ahg(namespace, "labymod/ignored_files.json"));
            if (resource != null) {
                try {
                    InputStreamReader reader = new InputStreamReader((InputStream) resource.get());
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

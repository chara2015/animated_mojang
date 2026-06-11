package net.labymod.v1_21_3.client.resources.pack;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/pack/VersionedResourcePackScanner.class */
@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner {
    @Override // net.labymod.api.client.resources.pack.ResourcePackScanner
    public void scan() {
        avv resourceManager = fmg.Q().ac();
        List<IncompatibleResourcePack> incompatiblePacks = resourceManager.b().filter(this::isExternalPack).map(this::scanPack).filter((v0) -> {
            return v0.isIncompatible();
        }).toList();
        onScanned(incompatiblePacks);
    }

    private boolean isExternalPack(aug pack) {
        if (!(pack instanceof WrappedPackResources)) {
            return (pack instanceof aud) || (pack instanceof auj) || (pack instanceof atz);
        }
        WrappedPackResources wrappedPackResources = (WrappedPackResources) pack;
        return isExternalPack(wrappedPackResources.delegate());
    }

    private IncompatibleResourcePack scanPack(aug pack) {
        Collection<String> ignoredFiles = collectIgnoredFiles(pack);
        Set<String> unsupportedFiles = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : getBlacklistedFiles().entrySet()) {
            String directory = entry.getKey();
            Set<String> blacklistedFiles = entry.getValue();
            scanPack(pack, directory, (location, supplier) -> {
                analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation) location);
            });
        }
        return new IncompatibleResourcePack(pack.b(), ignoredFiles, unsupportedFiles);
    }

    private void scanPack(aug pack, String baseDirectory, a output) {
        pack.a(aui.a, Namespaces.MINECRAFT, baseDirectory, output);
    }

    private Collection<String> collectIgnoredFiles(aug pack) {
        Set<String> ignoredFiles = new HashSet<>();
        for (String namespace : pack.a(aui.a)) {
            avn<InputStream> resource = pack.a(aui.a, alz.a(namespace, "labymod/ignored_files.json"));
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

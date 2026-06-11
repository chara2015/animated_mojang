package net.labymod.v1_21_11.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.pack.AbstractResourcePackScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/pack/VersionedResourcePackScanner.class */
@Singleton
@Implements(ResourcePackScanner.class)
public class VersionedResourcePackScanner extends AbstractResourcePackScanner {
    public void scan() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        List<IncompatibleResourcePack> incompatiblePacks = resourceManager.listPacks().filter(this::isExternalPack).map(this::scanPack).filter((v0) -> {
            return v0.isIncompatible();
        }).toList();
        onScanned(incompatiblePacks);
    }

    private boolean isExternalPack(PackResources pack) {
        if (!(pack instanceof WrappedPackResources)) {
            return (pack instanceof FilePackResources) || (pack instanceof PathPackResources) || (pack instanceof CompositePackResources);
        }
        WrappedPackResources wrappedPackResources = (WrappedPackResources) pack;
        return isExternalPack(wrappedPackResources.delegate());
    }

    private IncompatibleResourcePack scanPack(PackResources pack) {
        Collection<String> ignoredFiles = collectIgnoredFiles(pack);
        Set<String> unsupportedFiles = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : getBlacklistedFiles().entrySet()) {
            String directory = entry.getKey();
            Set<String> blacklistedFiles = entry.getValue();
            scanPack(pack, directory, (location, supplier) -> {
                analyzeResourcePackFile(blacklistedFiles, ignoredFiles, unsupportedFiles, (ResourceLocation) location);
            });
        }
        return new IncompatibleResourcePack(pack.packId(), ignoredFiles, unsupportedFiles);
    }

    private void scanPack(PackResources pack, String baseDirectory, PackResources.ResourceOutput output) {
        pack.listResources(PackType.CLIENT_RESOURCES, "minecraft", baseDirectory, output);
    }

    private Collection<String> collectIgnoredFiles(PackResources pack) {
        Set<String> ignoredFiles = new HashSet<>();
        for (String namespace : pack.getNamespaces(PackType.CLIENT_RESOURCES)) {
            IoSupplier<InputStream> resource = pack.getResource(PackType.CLIENT_RESOURCES, Identifier.fromNamespaceAndPath(namespace, "labymod/ignored_files.json"));
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

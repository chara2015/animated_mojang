package net.minecraft.server.packs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.resources.IoSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PackResources.class */
public interface PackResources extends AutoCloseable {
    public static final String METADATA_EXTENSION = ".mcmeta";
    public static final String PACK_META = "pack.mcmeta";

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PackResources$ResourceOutput.class */
    @FunctionalInterface
    public interface ResourceOutput extends BiConsumer<Identifier, IoSupplier<InputStream>> {
    }

    IoSupplier<InputStream> getRootResource(String... strArr);

    IoSupplier<InputStream> getResource(PackType packType, Identifier identifier);

    void listResources(PackType packType, String str, String str2, ResourceOutput resourceOutput);

    Set<String> getNamespaces(PackType packType);

    <T> T getMetadataSection(MetadataSectionType<T> metadataSectionType) throws IOException;

    PackLocationInfo location();

    @Override // java.lang.AutoCloseable
    void close();

    default String packId() {
        return location().id();
    }

    default Optional<KnownPack> knownPackInfo() {
        return location().knownPackInfo();
    }
}

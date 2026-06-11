package net.labymod.core.addon;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.mapping.MixinRemapperInjector;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.remap.JarRemapper;
import net.labymod.api.util.HashUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.mapping.remap.FartJarRemapper;
import net.labymod.core.mapping.transformer.AccessWidenerTransformer;
import net.labymod.core.mapping.transformer.MixinTransformer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/AddonRemapper.class */
public class AddonRemapper {
    private static final String VERSION = MinecraftVersions.current().getRawVersion();
    private static final Logging LOGGER = Logging.getLogger();
    private MappingService mappingService;
    private MappingProvider mappingProvider;
    private boolean initialized;

    private void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.mappingService = MappingService.instance();
        this.mappingProvider = this.mappingService.mappings(MappingNamespace.MINECRAFT_OBFUSCATED, MappingNamespace.NAMED);
        MixinRemapperInjector.instance().injectRemapper(this.mappingService.mixinRemapper(this.mappingProvider));
    }

    public Path remap(Path source) {
        return remap(new ChecksumPath(source));
    }

    public Path remap(ChecksumPath checksumSource) {
        initialize();
        Path source = checksumSource.source();
        Path workingDirectory = source.getParent().resolve("remapped").resolve(VERSION);
        boolean validChecksum = false;
        try {
            validChecksum = checksumSource.checkChecksum(workingDirectory);
        } catch (IOException exception) {
            LOGGER.error("Failed to check checksum", exception);
        }
        Path destination = workingDirectory.resolve(source.getFileName().toString());
        if (validChecksum && Files.exists(destination, new LinkOption[0])) {
            return destination;
        }
        JarRemapper remapper = this.mappingService.jarRemapper(this.mappingProvider).entry(source, destination).library(PlatformEnvironment.getObfuscatedJarPath()).build();
        if (remapper instanceof FartJarRemapper) {
            FartJarRemapper fartRemapper = (FartJarRemapper) remapper;
            fartRemapper.addTransformer(new MixinTransformer(this.mappingProvider));
            fartRemapper.addTransformer(new AccessWidenerTransformer(this.mappingProvider));
        }
        try {
            LOGGER.debug(remapper.execute(), new Object[0]);
            return destination;
        } catch (Exception exception2) {
            LOGGER.error("Remapping failed", exception2);
            return source;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/AddonRemapper$ChecksumPath.class */
    public static final class ChecksumPath extends Record {
        private final Path source;

        public ChecksumPath(Path source) {
            this.source = source;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChecksumPath.class), ChecksumPath.class, "source", "FIELD:Lnet/labymod/core/addon/AddonRemapper$ChecksumPath;->source:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChecksumPath.class), ChecksumPath.class, "source", "FIELD:Lnet/labymod/core/addon/AddonRemapper$ChecksumPath;->source:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChecksumPath.class, Object.class), ChecksumPath.class, "source", "FIELD:Lnet/labymod/core/addon/AddonRemapper$ChecksumPath;->source:Ljava/nio/file/Path;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Path source() {
            return this.source;
        }

        public boolean checkChecksum(Path destination) throws IOException {
            Path checksumPath = getChecksumPath(destination);
            if (Files.exists(checksumPath, new LinkOption[0])) {
                String checksumValue = Files.readString(checksumPath);
                byte[] sourceBytes = Files.readAllBytes(this.source);
                String sourceChecksum = HashUtil.md5Hex(sourceBytes);
                if (!checksumValue.equals(sourceChecksum)) {
                    AddonRemapper.LOGGER.warn("Checksum mismatch for file: {}", this.source);
                    saveChecksum(checksumPath, sourceChecksum);
                    return false;
                }
                return true;
            }
            byte[] sourceBytes2 = Files.readAllBytes(this.source);
            String checksum = HashUtil.md5Hex(sourceBytes2);
            saveChecksum(checksumPath, checksum);
            return false;
        }

        private void saveChecksum(Path checksumPath, String checksum) throws IOException {
            Files.writeString(checksumPath, checksum, new OpenOption[0]);
        }

        public Path getChecksumPath(Path destination) {
            return destination.resolve(this.source.getFileName().toString() + ".checksum");
        }
    }
}

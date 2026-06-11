package net.labymod.core.mapping.remap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.remap.JarRemapEntry;
import net.labymod.api.mapping.remap.JarRemapper;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.minecraftforge.fart.api.Renamer;
import net.minecraftforge.fart.api.SignatureStripperConfig;
import net.minecraftforge.fart.api.Transformer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/remap/FartJarRemapper.class */
public class FartJarRemapper implements JarRemapper {
    private final FartMappingProvider mappingProvider;
    private final Collection<JarRemapEntry> entries;
    private final Collection<Path> libraries;
    private final Collection<Transformer> transformers;
    private final Path sourceRemapJarPath;

    public FartJarRemapper(FartMappingProvider mappingProvider, Collection<JarRemapEntry> entries, Collection<Path> libraries) {
        Path path;
        this.mappingProvider = mappingProvider;
        this.entries = entries;
        this.libraries = libraries;
        Path obfuscatedJarPath = PlatformEnvironment.getObfuscatedJarPath();
        if (obfuscatedJarPath == null || !Files.exists(obfuscatedJarPath, new LinkOption[0])) {
            throw new IllegalStateException("Cannot perform remap without obfuscated minecraft jar");
        }
        this.transformers = new HashSet();
        String sourceNamespace = mappingProvider.getSourceNamespace();
        if (MappingNamespace.MINECRAFT_OBFUSCATED.equals(sourceNamespace)) {
            path = obfuscatedJarPath;
        } else {
            path = Paths.get(String.format(Locale.ROOT, Constants.Files.REMAP_JAR_PATH, MinecraftVersions.current().getRawVersion(), sourceNamespace), new String[0]);
        }
        this.sourceRemapJarPath = path;
    }

    public void addTransformer(Transformer transformer) {
        this.transformers.add(transformer);
    }

    @Override // net.labymod.api.mapping.remap.JarRemapper
    @NotNull
    public MappingProvider mappingProvider() {
        return this.mappingProvider;
    }

    @Override // net.labymod.api.mapping.remap.JarRemapper
    @NotNull
    public Collection<JarRemapEntry> getRemapEntries() {
        return this.entries;
    }

    @Override // net.labymod.api.mapping.remap.JarRemapper
    @Nullable
    public Path findOutputPath(@NotNull Path inputPath) {
        for (JarRemapEntry entry : this.entries) {
            if (entry.getInputPath().equals(inputPath)) {
                return entry.getOutputPath();
            }
        }
        return null;
    }

    @Override // net.labymod.api.mapping.remap.JarRemapper
    @NotNull
    public StringBuilder execute() throws IOException {
        StringBuilder logBuilder = new StringBuilder();
        if (!Files.exists(this.sourceRemapJarPath, new LinkOption[0])) {
            MappingProvider provider = MappingService.instance().mappings(MappingNamespace.MINECRAFT_OBFUSCATED, this.mappingProvider.getSourceNamespace());
            if (provider == null) {
                throw new IllegalStateException("Source mappings are not registered");
            }
            StringBuilder childLogBuilder = MappingService.instance().jarRemapper(provider).entry(PlatformEnvironment.getObfuscatedJarPath(), this.sourceRemapJarPath).build().execute();
            logBuilder.append((CharSequence) childLogBuilder);
        }
        Renamer.Builder renamerBuilder = Renamer.builder().logger(line -> {
            logBuilder.append(line).append(System.lineSeparator());
        }).add(Transformer.renamerFactory(this.mappingProvider.getDelegate(), false)).add(Transformer.signatureStripperFactory(SignatureStripperConfig.ALL)).lib(this.sourceRemapJarPath.toFile());
        for (Transformer transformer : this.transformers) {
            renamerBuilder.add(transformer);
        }
        for (Path lib : this.libraries) {
            renamerBuilder.lib(lib.toFile());
        }
        Renamer renamer = renamerBuilder.build();
        try {
            for (JarRemapEntry entry : this.entries) {
                Files.deleteIfExists(entry.getOutputPath());
                renamer.run(entry.getInputPath().toFile(), entry.getOutputPath().toFile());
            }
            if (renamer != null) {
                renamer.close();
            }
            return logBuilder;
        } catch (Throwable th) {
            if (renamer != null) {
                try {
                    renamer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}

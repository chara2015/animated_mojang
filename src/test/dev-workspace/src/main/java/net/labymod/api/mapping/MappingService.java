package net.labymod.api.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.api.mapping.loader.MappingReader;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.remap.JarRemapperBuilder;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/MappingService.class */
@Referenceable
public interface MappingService {
    @NotNull
    Collection<String> getNamespaces();

    void registerReader(@NotNull MappingReader mappingReader);

    @Nullable
    MappingReader findReader(@NotNull MappingType mappingType);

    @NotNull
    MappingProvider registerMappings(@NotNull MappingProvider mappingProvider);

    @NotNull
    MappingProvider registerMappings(@NotNull InputStream inputStream, @NotNull String str, @NotNull String str2, @NotNull MappingType mappingType) throws IOException;

    @NotNull
    MappingProvider registerMappings(MappingLoader mappingLoader) throws IOException;

    @Nullable
    MappingProvider mappings(@NotNull String str, @NotNull String str2);

    @NotNull
    MappingProvider currentMappings();

    @NotNull
    IRemapper mixinRemapper(@NotNull MappingProvider mappingProvider);

    @Nullable
    IRemapper mixinRemapper(@NotNull String str, @NotNull String str2);

    @NotNull
    Remapper asmRemapper(@NotNull MappingProvider mappingProvider);

    @Nullable
    Remapper asmRemapper(@NotNull String str, @NotNull String str2);

    @NotNull
    JarRemapperBuilder jarRemapper(@NotNull MappingProvider mappingProvider);

    @Nullable
    JarRemapperBuilder jarRemapper(@NotNull String str, @NotNull String str2);

    @NotNull
    static MappingService instance() {
        return Laby.references().mappingService();
    }
}

package net.labymod.api.mapping.loader;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.provider.MappingProvider;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/loader/MappingReader.class */
public interface MappingReader {
    @NotNull
    MappingType[] supportedTypes();

    @NotNull
    MappingProvider read(@NotNull InputStream inputStream, @NotNull String str, @NotNull String str2, @NotNull MappingType mappingType) throws IOException;
}

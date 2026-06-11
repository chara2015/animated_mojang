package net.labymod.core.mapping.loader;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingReader;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/loader/FartMappingReader.class */
public class FartMappingReader implements MappingReader {
    @Override // net.labymod.api.mapping.loader.MappingReader
    @NotNull
    public MappingType[] supportedTypes() {
        return MappingType.values();
    }

    @Override // net.labymod.api.mapping.loader.MappingReader
    @NotNull
    public MappingProvider read(@NotNull InputStream stream, @NotNull String sourceNamespace, @NotNull String targetNamespace, @NotNull MappingType type) throws IOException {
        IMappingFile mappingFile = IMappingFile.load(stream);
        return new FartMappingProvider(sourceNamespace, targetNamespace, mappingFile);
    }
}

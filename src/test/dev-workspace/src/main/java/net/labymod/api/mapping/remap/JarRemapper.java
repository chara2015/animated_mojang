package net.labymod.api.mapping.remap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.mapping.provider.MappingProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/remap/JarRemapper.class */
public interface JarRemapper {
    @NotNull
    MappingProvider mappingProvider();

    @NotNull
    Collection<JarRemapEntry> getRemapEntries();

    @Nullable
    Path findOutputPath(@NotNull Path path);

    @NotNull
    StringBuilder execute() throws IOException;
}

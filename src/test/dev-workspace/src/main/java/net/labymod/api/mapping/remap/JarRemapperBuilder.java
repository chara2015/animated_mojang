package net.labymod.api.mapping.remap;

import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/remap/JarRemapperBuilder.class */
public interface JarRemapperBuilder {
    @NotNull
    JarRemapperBuilder entry(@NotNull Path path, @NotNull Path path2);

    @NotNull
    JarRemapperBuilder entry(@NotNull JarRemapEntry jarRemapEntry);

    @NotNull
    JarRemapperBuilder library(@NotNull Path path);

    @NotNull
    JarRemapper build();
}

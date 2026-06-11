package net.labymod.api.mapping.loader;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/loader/MappingLoader.class */
public interface MappingLoader {
    @NotNull
    String getSourceNamespace();

    @NotNull
    String getTargetNamespace();

    @NotNull
    MappingType type();

    @NotNull
    InputStream load() throws IOException;
}

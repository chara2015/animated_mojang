package net.labymod.api.mappings;

import java.io.IOException;
import net.labymod.api.mappings.io.MappingWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/Mapping.class */
public interface Mapping {
    String getOriginal();

    String getMapped();

    void write(MappingWriter mappingWriter, boolean z) throws IOException;
}

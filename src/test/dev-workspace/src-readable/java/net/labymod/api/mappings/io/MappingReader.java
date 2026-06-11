package net.labymod.api.mappings.io;

import java.io.InputStream;
import net.labymod.api.mappings.MappingFile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/io/MappingReader.class */
public interface MappingReader {
    MappingFile read(InputStream inputStream);
}

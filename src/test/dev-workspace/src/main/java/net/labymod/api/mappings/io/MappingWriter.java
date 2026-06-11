package net.labymod.api.mappings.io;

import java.io.IOException;
import net.labymod.api.mappings.ClassMapping;
import net.labymod.api.mappings.FieldMapping;
import net.labymod.api.mappings.MethodMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/io/MappingWriter.class */
public interface MappingWriter extends AutoCloseable {
    void writeClass(ClassMapping classMapping, boolean z) throws IOException;

    void writeField(FieldMapping fieldMapping, boolean z) throws IOException;

    void writeMethod(MethodMapping methodMapping, boolean z) throws IOException;
}

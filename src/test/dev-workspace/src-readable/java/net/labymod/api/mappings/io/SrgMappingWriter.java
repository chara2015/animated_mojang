package net.labymod.api.mappings.io;

import java.io.IOException;
import java.io.Writer;
import net.labymod.api.mappings.ClassMapping;
import net.labymod.api.mappings.FieldMapping;
import net.labymod.api.mappings.MethodMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/io/SrgMappingWriter.class */
public class SrgMappingWriter implements MappingWriter {
    private final Writer writer;

    public SrgMappingWriter(Writer writer) {
        this.writer = writer;
    }

    @Override // net.labymod.api.mappings.io.MappingWriter
    public void writeClass(ClassMapping mapping, boolean reversed) throws IOException {
        String original = mapping.getOriginal();
        String mapped = mapping.getMapped();
        this.writer.write("CL: " + String.valueOf(reversed ? mapping : original) + " " + (reversed ? original : mapped));
        this.writer.write(10);
        this.writer.flush();
    }

    @Override // net.labymod.api.mappings.io.MappingWriter
    public void writeField(FieldMapping mapping, boolean reversed) throws IOException {
        ClassMapping parent = mapping.getParent();
        if (reversed) {
            this.writer.write("FD: " + parent.getMapped() + "/" + mapping.getMapped() + " " + parent.getOriginal() + "/" + mapping.getOriginal());
        } else {
            this.writer.write("FD: " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + parent.getMapped() + "/" + mapping.getMapped());
        }
        this.writer.write(10);
        this.writer.flush();
    }

    @Override // net.labymod.api.mappings.io.MappingWriter
    public void writeMethod(MethodMapping mapping, boolean reversed) throws IOException {
        ClassMapping parent = mapping.getParent();
        if (reversed) {
            this.writer.write("MD: " + parent.getMapped() + "/" + mapping.getMapped() + " " + mapping.getMappedDescriptor() + " " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + mapping.getOriginalDescriptor());
        } else {
            this.writer.write("MD: " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + mapping.getOriginalDescriptor() + " " + parent.getMapped() + "/" + mapping.getMapped() + " " + mapping.getMappedDescriptor());
        }
        this.writer.write(10);
        this.writer.flush();
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        this.writer.close();
    }
}

package net.labymod.api.mappings;

import java.io.IOException;
import net.labymod.api.mappings.io.MappingWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/FieldMapping.class */
public class FieldMapping extends AbstractMapping implements ScopedMapping<ClassMapping> {
    private final String originalDescriptor;
    private final String mappedDescriptor;
    private ClassMapping parent;

    public FieldMapping(String original, String mapped) {
        this(original, null, mapped, null);
    }

    public FieldMapping(String original, String originalDescriptor, String mapped, String mappedDescriptor) {
        super(original, mapped);
        this.originalDescriptor = originalDescriptor;
        this.mappedDescriptor = mappedDescriptor;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.mappings.ScopedMapping
    public ClassMapping getParent() {
        return this.parent;
    }

    void setParent(ClassMapping parent) {
        this.parent = parent;
    }

    public String getOriginalDescriptor() {
        return this.originalDescriptor;
    }

    public String getMappedDescriptor() {
        return this.mappedDescriptor;
    }

    @Override // net.labymod.api.mappings.Mapping
    public void write(MappingWriter writer, boolean reversed) throws IOException {
        writer.writeField(this, reversed);
    }
}

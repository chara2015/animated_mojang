package net.labymod.api.mappings;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mappings.io.MappingWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/ClassMapping.class */
public class ClassMapping extends AbstractMapping {
    private final Map<String, FieldMapping> fields;
    private final Map<String, MethodMapping> methods;

    public ClassMapping(String original, String mapped) {
        super(original, mapped);
        this.fields = new HashMap();
        this.methods = new HashMap();
    }

    public void addField(FieldMapping fieldMapping) {
        fieldMapping.setParent(this);
        this.fields.put(fieldMapping.getOriginal(), fieldMapping);
    }

    public void addMethod(MethodMapping methodMapping) {
        methodMapping.setParent(this);
        this.methods.put(methodMapping.getOriginal() + methodMapping.getOriginalDescriptor(), methodMapping);
    }

    public Collection<FieldMapping> getFields() {
        return this.fields.values();
    }

    public Collection<MethodMapping> getMethods() {
        return this.methods.values();
    }

    public String remapField(String name) {
        FieldMapping fieldMapping = this.fields.get(name);
        return fieldMapping == null ? name : fieldMapping.getMapped();
    }

    public MethodMapping remapMethod(String name, String desc) {
        return this.methods.get(name + desc);
    }

    @Override // net.labymod.api.mappings.Mapping
    public void write(MappingWriter writer, boolean reversed) throws IOException {
        writer.writeClass(this, reversed);
        for (FieldMapping field : getFields()) {
            writer.writeField(field, reversed);
        }
        for (MethodMapping method : getMethods()) {
            writer.writeMethod(method, reversed);
        }
    }
}

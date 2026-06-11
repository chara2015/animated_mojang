package net.labymod.api.mappings;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mappings.io.MappingWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/MappingFile.class */
public class MappingFile {
    private final Map<String, ClassMapping> classes;

    public MappingFile() {
        this(new HashMap());
    }

    public MappingFile(Map<String, ClassMapping> classes) {
        this.classes = classes;
    }

    public ClassMapping getOrCreate(String original, String mapped) {
        ClassMapping cached = this.classes.get(original);
        if (cached != null) {
            return cached;
        }
        ClassMapping cached2 = new ClassMapping(original, mapped);
        addClass(cached2);
        return cached2;
    }

    public void addClass(ClassMapping classMapping) {
        this.classes.put(classMapping.getOriginal(), classMapping);
    }

    public ClassMapping getClass(String name) {
        return this.classes.get(name);
    }

    public Collection<ClassMapping> getClasses() {
        return this.classes.values();
    }

    public MappingFile reverse() {
        MappingFile mappingFile = new MappingFile();
        for (ClassMapping cls : getClasses()) {
            ClassMapping newClass = mappingFile.getOrCreate(cls.getMapped(), cls.getOriginal());
            for (FieldMapping field : cls.getFields()) {
                newClass.addField(new FieldMapping(field.getMapped(), field.getMappedDescriptor(), field.getOriginal(), field.getOriginalDescriptor()));
            }
            for (MethodMapping method : cls.getMethods()) {
                newClass.addMethod(new MethodMapping(method.getMapped(), method.getMappedDescriptor(), method.getOriginal(), method.getOriginalDescriptor()));
            }
        }
        return mappingFile;
    }

    public String remapClass(String cls) {
        ClassMapping classMapping = this.classes.get(cls);
        return classMapping == null ? cls : classMapping.getMapped();
    }

    public void write(MappingWriter writer, boolean reversed) throws IOException {
        for (ClassMapping cls : getClasses()) {
            cls.write(writer, reversed);
        }
    }
}

package net.labymod.core.mapping.transformer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraftforge.fart.api.Transformer;
import net.minecraftforge.fart.internal.EntryImpl;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/transformer/MixinTransformer.class */
public class MixinTransformer implements Transformer {
    private static final String MIXIN_DESC = "Lorg/spongepowered/asm/mixin/Mixin;";
    private final MappingProvider provider;

    public MixinTransformer(MappingProvider provider) {
        this.provider = provider.reverse();
    }

    public Transformer.ClassEntry process(Transformer.ClassEntry entry) {
        ClassReader reader = new ClassReader(entry.getData());
        ClassVisitor classNode = new ClassNode();
        reader.accept(classNode, 0);
        ClassMapping classMapping = findClassMapping(classNode);
        Map<String, String> mappings = generateMappings(classMapping, classNode);
        if (!mappings.isEmpty()) {
            classNode = new ClassNode();
            ClassRemapper remapper = new ClassRemapper(classNode, new SimpleRemapper(mappings));
            reader.accept(remapper, 0);
        }
        ClassWriter writer = new ClassWriter(reader, 0);
        classNode.accept(writer);
        return new EntryImpl.ClassEntry(entry.getName(), entry.getTime(), writer.toByteArray());
    }

    private ClassMapping findClassMapping(ClassNode node) {
        List<AnnotationNode> invisibleAnnotations = node.invisibleAnnotations;
        if (invisibleAnnotations == null) {
            return null;
        }
        for (AnnotationNode invisibleAnnotation : invisibleAnnotations) {
            if (invisibleAnnotation.desc.equals(MIXIN_DESC)) {
                Map<String, Object> values = ASMHelper.getAnnotationValues(invisibleAnnotation);
                ClassMapping mapping = findClassMappingFromValue(values.get("value"));
                if (mapping == null) {
                    mapping = findClassMappingFromTargets(values.get("targets"));
                }
                return mapping;
            }
        }
        return null;
    }

    private ClassMapping findClassMappingFromValue(Object object) {
        if (!(object instanceof List)) {
            return null;
        }
        List<?> list = (List) object;
        for (Object obj : list) {
            if (obj instanceof Type) {
                Type type = (Type) obj;
                String className = type.getClassName();
                return this.provider.getClassMapping(className.replace('.', '/'));
            }
        }
        return null;
    }

    private ClassMapping findClassMappingFromTargets(Object object) {
        if (!(object instanceof List)) {
            return null;
        }
        List<?> list = (List) object;
        for (Object obj : list) {
            if (obj instanceof String) {
                String className = (String) obj;
                return this.provider.getClassMapping(className.replace('.', '/'));
            }
        }
        return null;
    }

    private Map<String, String> generateMappings(ClassMapping classMapping, ClassNode classNode) {
        if (classMapping == null) {
            return Collections.emptyMap();
        }
        Map<String, String> mappings = new HashMap<>();
        for (MethodMapping methodMapping : classMapping.getMethodMappings()) {
            mappings.put(classNode.name + "." + methodMapping.getMappedName() + methodMapping.getDescriptor(), methodMapping.getName());
        }
        for (FieldMapping fieldMapping : classMapping.getFieldMappings()) {
            mappings.put(classNode.name + "." + fieldMapping.getMappedName(), fieldMapping.getName());
        }
        return mappings;
    }
}

package net.labymod.core.mapping.transformer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.labymod.accesswidener.AccessSpecifier;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.accesswidener.AccessWidenerReader;
import net.labymod.accesswidener.AccessWidenerWriter;
import net.labymod.accesswidener.access.Access;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import net.minecraftforge.fart.api.Transformer;
import org.objectweb.asm.Type;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/transformer/AccessWidenerTransformer.class */
public class AccessWidenerTransformer implements Transformer {
    private final MappingProvider provider;

    public AccessWidenerTransformer(MappingProvider provider) {
        this.provider = provider;
    }

    public Transformer.ResourceEntry process(Transformer.ResourceEntry entry) {
        if (!entry.getName().endsWith(".accesswidener")) {
            return entry;
        }
        return remapAccessWidener(entry);
    }

    private Transformer.ResourceEntry remapAccessWidener(Transformer.ResourceEntry entry) {
        AccessWidener accessWidener = new AccessWidener();
        accessWidener.setNamespace(VanillaTheme.ID);
        AccessWidenerReader reader = new AccessWidenerReader(accessWidener);
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(entry.getData());
            try {
                reader.read(inputStream);
                AccessWidener copiedWidener = accessWidener.copy();
                copiedWidener.setNamespace(MappingNamespace.NAMED);
                remapClasses(copiedWidener);
                remapMethods(copiedWidener);
                remapFields(copiedWidener);
                AccessWidenerWriter writer = new AccessWidenerWriter(copiedWidener);
                Transformer.ResourceEntry resourceEntryCreate = Transformer.ResourceEntry.create(entry.getName(), entry.getTime(), writer.write());
                inputStream.close();
                return resourceEntryCreate;
            } finally {
            }
        } catch (IOException e) {
            return entry;
        }
    }

    private void remapClasses(AccessWidener widener) {
        HashMap map = new HashMap();
        for (Map.Entry<String, Access> accessEntry : widener.getClassAccess().entrySet()) {
            String key = accessEntry.getKey();
            ClassMapping fileClass = this.provider.getClassMapping(key);
            if (fileClass != null) {
                map.put(fileClass.getMappedName(), accessEntry.getValue());
            }
        }
        widener.getClassAccess().clear();
        widener.getClassAccess().putAll(map);
    }

    private void remapMethods(AccessWidener widener) {
        Map<? extends AccessSpecifier, ? extends Access> map = new HashMap<>();
        Map<AccessSpecifier, Access> accesses = widener.getMethodAccess().getAccesses();
        for (Map.Entry<AccessSpecifier, Access> entry : accesses.entrySet()) {
            AccessSpecifier specifier = entry.getKey();
            String owner = specifier.getOwner();
            ClassMapping mappingClass = this.provider.getClassMapping(owner);
            if (mappingClass != null) {
                if (specifier.isWildcard()) {
                    map.put(AccessSpecifier.ofWildcard(mappingClass.getMappedName()), entry.getValue());
                } else {
                    String name = specifier.getName();
                    String descriptor = specifier.getDescriptor();
                    MethodMapping method = mappingClass.getMethodMapping(name, descriptor);
                    if (method != null) {
                        map.put(AccessSpecifier.of(mappingClass.getMappedName(), method.getMappedName(), method.getMappedDescriptor()), entry.getValue());
                    }
                }
            }
        }
        accesses.clear();
        accesses.putAll(map);
    }

    private void remapFields(AccessWidener widener) {
        Type type;
        Map<? extends AccessSpecifier, ? extends Access> map = new HashMap<>();
        Map<AccessSpecifier, Access> accesses = widener.getFieldAccess().getAccesses();
        for (Map.Entry<AccessSpecifier, Access> entry : accesses.entrySet()) {
            AccessSpecifier specifier = entry.getKey();
            String owner = specifier.getOwner();
            ClassMapping mappingClass = this.provider.getClassMapping(owner);
            if (mappingClass != null) {
                if (specifier.isWildcard()) {
                    map.put(AccessSpecifier.ofWildcard(mappingClass.getMappedName()), entry.getValue());
                } else {
                    String name = specifier.getName();
                    FieldMapping field = mappingClass.getFieldMapping(name);
                    if (field != null) {
                        String descriptor = specifier.getDescriptor();
                        if (descriptor != null && descriptor.endsWith(";") && (type = Type.getType(descriptor)) != null) {
                            boolean isArray = type.getSort() == 9;
                            String descriptor2 = isArray ? type.getElementType().getInternalName() : type.getInternalName();
                            ClassMapping cls = this.provider.getClassMapping(descriptor2);
                            if (cls != null) {
                                descriptor = "L" + cls.getMappedName() + ";";
                            } else {
                                descriptor = "L" + descriptor2 + ";";
                            }
                            if (isArray) {
                                descriptor = "[" + descriptor;
                            }
                        }
                        if (descriptor != null) {
                            map.put(AccessSpecifier.of(mappingClass.getMappedName(), field.getMappedName(), descriptor), entry.getValue());
                        }
                    }
                }
            }
        }
        accesses.clear();
        accesses.putAll(map);
    }
}

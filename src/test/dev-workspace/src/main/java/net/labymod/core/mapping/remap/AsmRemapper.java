package net.labymod.core.mapping.remap;

import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.provider.child.ClassMapping;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/remap/AsmRemapper.class */
public class AsmRemapper extends Remapper {
    private static final String JAVA_PREFIX = "java/";
    private final MappingProvider provider;
    private final MappingProvider reverseProvider;

    public AsmRemapper(MappingProvider provider) {
        this.provider = provider;
        this.reverseProvider = provider.reverse();
    }

    public String mapMethodName(String owner, String name, String descriptor) {
        ClassMapping classMapping = this.provider.getClassMapping(owner);
        String mappedName = classMapping == null ? name : classMapping.mapMethod(name, descriptor);
        if (name.equals(mappedName)) {
            ClassInfo classInfo = ClassInfo.forName(map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            String superName = classInfo.getSuperName();
            if (superName != null && !superName.startsWith(JAVA_PREFIX)) {
                String mappedSuperName = mapMethodName(unmap(superName), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (String interfaceName : classInfo.getInterfaces()) {
                if (!interfaceName.startsWith(JAVA_PREFIX)) {
                    String mappedInterfaceName = mapMethodName(unmap(interfaceName), name, descriptor);
                    if (!mappedInterfaceName.equals(name)) {
                        return mappedInterfaceName;
                    }
                }
            }
        }
        return mappedName;
    }

    public String mapRecordComponentName(String owner, String name, String descriptor) {
        return mapFieldName(owner, name, descriptor);
    }

    public String mapFieldName(String owner, String name, String descriptor) {
        ClassMapping classMapping = this.provider.getClassMapping(owner);
        String mappedName = classMapping == null ? name : classMapping.mapField(name);
        if (name.equals(mappedName)) {
            ClassInfo classInfo = ClassInfo.forName(map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            String superName = classInfo.getSuperName();
            if (superName != null && !superName.startsWith(JAVA_PREFIX)) {
                String mappedSuperName = mapFieldName(unmap(superName), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (String interfaceName : classInfo.getInterfaces()) {
                if (!interfaceName.startsWith(JAVA_PREFIX)) {
                    String mappedInterfaceName = mapFieldName(unmap(interfaceName), name, descriptor);
                    if (!mappedInterfaceName.equals(name)) {
                        return mappedInterfaceName;
                    }
                }
            }
        }
        return mappedName;
    }

    public String map(String internalName) {
        return this.provider.mapClass(internalName);
    }

    private String unmap(String internalName) {
        return this.reverseProvider.mapClass(internalName);
    }
}

package net.labymod.core.mapping.remap;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/remap/MixinRemapper.class */
public class MixinRemapper implements IRemapper {
    private static final String JAVA_PREFIX = "java/";
    private final MappingProvider provider;
    private final MappingProvider reverseProvider;
    private final Map<String, IRemapper.MappedMethod> methodNameMap = new HashMap();
    private final Map<String, IRemapper.MappedMethod> methodNameDescriptorMap = new HashMap();
    private final Map<String, IRemapper.MappedMethod> methodClassNameMap = new HashMap();
    private final Map<String, String> fieldNameMap = new HashMap();
    private final Map<String, String> fieldNameDescriptorMap = new HashMap();

    public MixinRemapper(MappingProvider provider) {
        this.provider = provider;
        this.reverseProvider = provider.reverse();
        for (ClassMapping classMapping : this.provider.getClassMappings()) {
            for (MethodMapping methodMapping : classMapping.getMethodMappings()) {
                String name = methodMapping.getName();
                IRemapper.MappedMethod mappedMethod = new IRemapper.MappedMethod(methodMapping.getMappedName(), methodMapping.getMappedDescriptor());
                this.methodNameMap.putIfAbsent(name, mappedMethod);
                this.methodNameDescriptorMap.putIfAbsent(name + methodMapping.getDescriptor(), mappedMethod);
                this.methodClassNameMap.putIfAbsent(classMapping.getName() + name, mappedMethod);
            }
            for (FieldMapping fieldMapping : classMapping.getFieldMappings()) {
                String name2 = fieldMapping.getName();
                String mappedName = fieldMapping.getMappedName();
                this.fieldNameMap.putIfAbsent(name2, mappedName);
                this.fieldNameDescriptorMap.putIfAbsent(name2 + fieldMapping.getDescriptor(), mappedName);
            }
        }
    }

    public IRemapper.MappedMethod mapMethod(String owner, String name, String desc) {
        IRemapper.MappedMethod mappedMethod;
        IRemapper.MappedMethod mappedMethod2;
        IRemapper.MappedMethod notMapped = new IRemapper.MappedMethod(name, desc);
        String unmappedDesc = desc == null ? null : unmapDesc(desc);
        if (owner != null) {
            String unmappedOwner = unmap(owner);
            ClassMapping classMapping = this.provider.getClassMapping(unmappedOwner);
            if (unmappedDesc == null || classMapping == null) {
                mappedMethod = this.methodClassNameMap.getOrDefault(unmappedOwner + name, notMapped);
            } else {
                MethodMapping methodMapping = classMapping.getMethodMapping(name, unmappedDesc);
                if (methodMapping == null) {
                    mappedMethod2 = notMapped;
                } else {
                    mappedMethod2 = new IRemapper.MappedMethod(methodMapping.getMappedName(), methodMapping.getMappedDescriptor());
                }
                mappedMethod = mappedMethod2;
            }
            if (mappedMethod.getName().equals(name)) {
                ClassInfo classInfo = ClassInfo.forName(map(owner));
                if (classInfo == null) {
                    return mappedMethod;
                }
                String superName = classInfo.getSuperName();
                if (superName != null && !superName.startsWith(JAVA_PREFIX)) {
                    IRemapper.MappedMethod mappedSuperMethod = mapMethod(unmap(superName), name, desc);
                    if (!mappedSuperMethod.getName().equals(name)) {
                        return mappedSuperMethod;
                    }
                }
                for (String interfaceName : classInfo.getInterfaces()) {
                    if (!interfaceName.startsWith(JAVA_PREFIX)) {
                        IRemapper.MappedMethod mappedInterfaceMethod = mapMethod(unmap(interfaceName), name, desc);
                        if (!mappedInterfaceMethod.getName().equals(name)) {
                            return mappedInterfaceMethod;
                        }
                    }
                }
            }
            return mappedMethod;
        }
        if (unmappedDesc == null) {
            return this.methodNameMap.getOrDefault(name, notMapped);
        }
        return this.methodNameDescriptorMap.getOrDefault(name + unmappedDesc, notMapped);
    }

    public String mapMethodName(String owner, String name, String desc) {
        return mapMethod(owner, name, desc).getName();
    }

    public String mapFieldName(String owner, String name, String desc) {
        String unmappedDesc = desc == null ? null : unmapDesc(desc);
        if (owner != null) {
            ClassMapping classMapping = this.provider.getClassMapping(unmap(owner));
            String mappedName = classMapping == null ? name : classMapping.mapField(name);
            if (mappedName.equals(name)) {
                ClassInfo classInfo = ClassInfo.forName(map(owner));
                if (classInfo == null) {
                    return mappedName;
                }
                String superName = classInfo.getSuperName();
                if (superName != null && !superName.startsWith(JAVA_PREFIX)) {
                    String mappedSuperName = mapFieldName(unmap(superName), name, desc);
                    if (!mappedSuperName.equals(name)) {
                        return mappedSuperName;
                    }
                }
                for (String interfaceName : classInfo.getInterfaces()) {
                    if (!interfaceName.startsWith(JAVA_PREFIX)) {
                        String mappedInterfaceName = mapFieldName(unmap(interfaceName), name, desc);
                        if (!mappedInterfaceName.equals(name)) {
                            return mappedInterfaceName;
                        }
                    }
                }
            }
            return mappedName;
        }
        if (unmappedDesc == null) {
            return this.fieldNameMap.getOrDefault(name, name);
        }
        return this.fieldNameDescriptorMap.getOrDefault(name + unmappedDesc, name);
    }

    public String map(String typeName) {
        return this.provider.mapClass(typeName);
    }

    public String unmap(String typeName) {
        return this.reverseProvider.mapClass(typeName);
    }

    public String mapDesc(String desc) {
        return this.provider.mapDescriptor(desc);
    }

    public String unmapDesc(String desc) {
        return this.reverseProvider.mapDescriptor(desc);
    }
}

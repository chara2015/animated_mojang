package net.labymod.api.volt.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.volt.asm.VoltClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/data/ClassData.class */
public class ClassData {
    private final String name;
    private final String superName;
    private final boolean isInterface;
    private ClassData superClassData;
    private final List<ClassData> targets = new ArrayList();
    private final List<String> interfaces = new ArrayList();

    public ClassData(ClassNode node) {
        this.name = node.name;
        this.superName = node.superName != null ? node.superName : null;
        this.interfaces.addAll(node.interfaces);
        this.isInterface = (node.access & 512) != 0;
        this.targets.add(this);
    }

    public static ClassData forResource(URL resourceUrl) {
        try {
            InputStream stream = resourceUrl.openStream();
            try {
                ClassReader reader = new ClassReader(stream.readAllBytes());
                ClassNode classNode = new ClassNode();
                reader.accept(classNode, 0);
                ClassData classData = new ClassData(classNode);
                if (stream != null) {
                    stream.close();
                }
                return classData;
            } finally {
            }
        } catch (IOException e) {
            return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getSuperName() {
        return this.superName;
    }

    public List<String> getInterfaces() {
        return this.interfaces;
    }

    public ClassData getSuperClassData() {
        if (this.superClassData == null && this.superName != null) {
            this.superClassData = VoltClassWriter.getClassData(this.superName);
        }
        return this.superClassData;
    }

    public boolean hasSuperClass(ClassData other) {
        return hasSuperclass(other, false);
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public List<ClassData> getTargets() {
        return this.targets;
    }

    private boolean hasSuperclass(ClassData other, boolean includeInterfaces) {
        return "java/lang/Object".equals(other.name) || findSuperclass(other.name, includeInterfaces) != null;
    }

    private ClassData findSuperclass(String name, boolean includeInterfaces) {
        if ("java/lang/Object".equals(name)) {
            return null;
        }
        ClassData superClassData = getSuperClassData();
        if (superClassData != null) {
            for (ClassData target : superClassData.getTargets()) {
                if (name.equals(target.getName())) {
                    return superClassData;
                }
                ClassData found = target.findSuperclass(name, includeInterfaces);
                if (found != null) {
                    return found;
                }
            }
        }
        if (includeInterfaces) {
            return findInterface(name);
        }
        return null;
    }

    private ClassData findInterface(String superClass) {
        ClassData superInterface;
        for (String interfaceName : getInterfaces()) {
            ClassData interfaceClassData = VoltClassWriter.getClassData(interfaceName);
            if (superClass.equals(interfaceName)) {
                return interfaceClassData;
            }
            if (interfaceClassData != null && (superInterface = interfaceClassData.findInterface(superClass)) != null) {
                return superInterface;
            }
        }
        return null;
    }

    public static String findCommonSuperClass(ClassData type1, ClassData type2) {
        return findCommonSuperClass(type1, type2, false);
    }

    public static String findCommonSuperClass(ClassData type1, ClassData type2, boolean includeInterfaces) {
        if (type1 == null || type2 == null) {
            return "java/lang/Object";
        }
        if (type1.hasSuperclass(type2, includeInterfaces)) {
            return type2.getName();
        }
        if (type2.hasSuperclass(type1, includeInterfaces)) {
            return type1.getName();
        }
        if (type1.isInterface() || type2.isInterface()) {
            return "java/lang/Object";
        }
        do {
            type1 = type1.getSuperClassData();
            if (type1 == null) {
                return "java/lang/Object";
            }
        } while (!type2.hasSuperclass(type1, includeInterfaces));
        return type1.getName();
    }
}

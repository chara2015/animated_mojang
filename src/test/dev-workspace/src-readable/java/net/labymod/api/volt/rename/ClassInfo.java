package net.labymod.api.volt.rename;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/rename/ClassInfo.class */
public class ClassInfo {
    private final String name;
    private final ClassInfo superClass;
    private final List<ClassInfo> interfaces;
    private final Map<String, FieldInfo> fields;
    private final Map<String, MethodInfo> methods;

    private ClassInfo(String name, ClassInfo superClass, List<ClassInfo> interfaces, Map<String, FieldInfo> fields, Map<String, MethodInfo> methods) {
        this.name = name;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
    }

    public static ClassInfo parse(ClassProvider provider, ClassNode node) {
        String name = node.name;
        ClassInfo superClass = provider.getOrLoad(node.superName);
        List<ClassInfo> interfaces = new ArrayList<>();
        for (String anInterface : node.interfaces) {
            ClassInfo classInfo = provider.getOrLoad(anInterface);
            if (classInfo != null) {
                interfaces.add(classInfo);
            }
        }
        return new ClassInfo(name, superClass, interfaces, parseFields(node), parseMethods(node));
    }

    private static Map<String, MethodInfo> parseMethods(ClassNode node) {
        Map<String, MethodInfo> mappedMethods = new HashMap<>();
        for (MethodNode method : node.methods) {
            mappedMethods.put(method.name + method.desc, new MethodInfo(method.name, method.desc));
        }
        return mappedMethods;
    }

    private static Map<String, FieldInfo> parseFields(ClassNode node) {
        Map<String, FieldInfo> mappedFields = new HashMap<>();
        for (FieldNode field : node.fields) {
            mappedFields.put(field.name + field.desc, new FieldInfo(field.name, field.desc));
        }
        return mappedFields;
    }

    public String getName() {
        return this.name;
    }

    public ClassInfo getSuperClass() {
        return this.superClass;
    }

    public List<ClassInfo> getInterfaces() {
        return this.interfaces;
    }

    public Map<String, FieldInfo> getFields() {
        return this.fields;
    }

    public Map<String, MethodInfo> getMethods() {
        return this.methods;
    }

    public String toString() {
        return this.name;
    }
}

package net.labymod.api.volt.asm;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.volt.asm.util.ASMContext;
import net.labymod.api.volt.data.ClassData;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/VoltClassWriter.class */
public class VoltClassWriter extends ClassWriter {
    private static final Map<String, ClassData> CLASS_DATA_CACHE = new HashMap(SubmissionOrders.DEBUG);
    private static ClassLoader classLoader;

    public VoltClassWriter(int flags) {
        super(flags);
    }

    public VoltClassWriter(ClassReader classReader, int flags) {
        super(classReader, flags);
    }

    protected String getCommonSuperClass(String type1, String type2) {
        if (type1 == null || type2 == null) {
            return "java/lang/Object";
        }
        return ClassData.findCommonSuperClass(getClassData(type1), getClassData(type2));
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? super.getClassLoader() : classLoader;
    }

    public void setClassLoader(ClassLoader classLoader2) {
        classLoader = classLoader2;
    }

    public static ClassData getClassData(String name) {
        ClassData classData = CLASS_DATA_CACHE.get(name);
        if (classData != null) {
            return classData;
        }
        String resourcePath = name.replace('.', '/').concat(".class");
        URL resource = findResource(resourcePath);
        if (resource == null) {
            return null;
        }
        ClassData classData2 = ClassData.forResource(resource);
        CLASS_DATA_CACHE.put(name, classData2);
        return classData2;
    }

    private static URL findResource(String path) {
        return ASMContext.findResource(path);
    }
}

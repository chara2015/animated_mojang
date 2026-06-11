package net.labymod.api.volt.asm.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.volt.asm.VoltClassWriter;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/util/ASMHelper.class */
public class ASMHelper {
    public static final int ASM_VERSION = 589824;

    public static byte[] transformClassData(byte[] classData, Consumer<ClassNode> callback) {
        return transformClassData(classData, callback, true);
    }

    public static byte[] transformClassData(byte[] classData, Consumer<ClassNode> callback, boolean computeFrames) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classData);
        classReader.accept(classNode, 0);
        if (callback != null) {
            callback.accept(classNode);
        }
        ClassWriter classWriter = newClassWriter(classReader, computeFrames);
        classNode.accept(classWriter);
        byte[] modifiedClassData = classWriter.toByteArray();
        writeClassData(classNode.name, modifiedClassData);
        return modifiedClassData;
    }

    public static void writeClassData(String name, byte[] classData) {
        writeClassData(name, classData, Boolean.getBoolean("net.labymod.debugging.asm"));
    }

    public static void writeClassData(String name, byte[] classData, boolean allowedToWrite) {
        if (!allowedToWrite) {
            return;
        }
        try {
            String classPath = name.replace('.', '/').concat(".class");
            Path modifiedClassDataPath = Paths.get("labymod-neo/debug/asm/" + classPath, new String[0]);
            Files.createDirectories(modifiedClassDataPath.getParent(), new FileAttribute[0]);
            Files.write(modifiedClassDataPath, classData, new OpenOption[0]);
        } catch (IOException e) {
        }
    }

    public static ClassNode getClassNode(String className) throws IOException {
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(className);
        reader.accept(classNode, 0);
        return classNode;
    }

    public static ClassNode getClassNode(InputStream stream) throws IOException {
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(stream);
        reader.accept(classNode, 0);
        return classNode;
    }

    public static MethodNode createStaticConstructor() {
        return new MethodNode(8, "<clinit>", "()V", (String) null, (String[]) null);
    }

    public static MethodNode createPublicConstructor() {
        return new MethodNode(1, "<init>", "()V", (String) null, (String[]) null);
    }

    public static ClassWriter newClassWriter() {
        return newClassWriter((Consumer<VoltClassWriter>) writer -> {
            writer.setClassLoader(ASMContext.getPlatformClassLoader());
        });
    }

    public static ClassWriter newClassWriter(Consumer<VoltClassWriter> writerConsumer) {
        VoltClassWriter writer = new VoltClassWriter(3);
        if (writerConsumer != null) {
            writerConsumer.accept(writer);
        }
        return writer;
    }

    public static ClassWriter newClassWriter(ClassReader reader) {
        return newClassWriter(reader, true);
    }

    public static ClassWriter newClassWriter(ClassReader reader, Consumer<VoltClassWriter> writerConsumer) {
        return newClassWriter(reader, writerConsumer, true);
    }

    public static ClassWriter newClassWriter(ClassReader reader, boolean computeFrames) {
        return newClassWriter(reader, writer -> {
            writer.setClassLoader(ASMContext.getPlatformClassLoader());
        }, computeFrames);
    }

    public static ClassWriter newClassWriter(ClassReader reader, Consumer<VoltClassWriter> writerConsumer, boolean computeFrames) {
        VoltClassWriter writer = new VoltClassWriter(reader, computeFrames ? 3 : 0);
        if (writerConsumer != null) {
            writerConsumer.accept(writer);
        }
        return writer;
    }

    public static boolean canLoadClass(int classVersion) {
        return canLoadClass(classVersion, getCurrentJavaClassVersion());
    }

    public static boolean canLoadClass(int classVersion, int javaVersion) {
        return classVersion <= javaVersion;
    }

    public static int getCurrentJavaClassVersion() {
        try {
            String property = System.getProperty("java.class.version");
            return (int) Double.parseDouble(property);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @NotNull
    public static String getMethodDescriptor(@NotNull MethodNode node) {
        return node.name + node.desc;
    }

    public static MethodNode createMethod(int access, String name, String descriptor, String signature, Collection<String> exceptions, Consumer<MethodNode> callback) {
        MethodNode method = new MethodNode(access, name, descriptor, signature, exceptions == null ? null : (String[]) exceptions.toArray(new String[0]));
        if (callback != null) {
            callback.accept(method);
        }
        return method;
    }

    public static Type getLastParameter(String methodDescriptor) {
        Type[] types = Type.getArgumentTypes(methodDescriptor);
        if (types.length == 0) {
            return null;
        }
        return types[types.length - 1];
    }

    public static String getNameWithDesc(MethodNode node) {
        return node.name + node.desc;
    }

    public static AbstractInsnNode findInstruction(AbstractInsnNode node, boolean next, Predicate<AbstractInsnNode> filter) {
        if (node == null) {
            return null;
        }
        if (filter.test(node)) {
            return node;
        }
        return findInstruction(next ? node.getNext() : node.getPrevious(), next, filter);
    }

    public static Map<String, Object> getAnnotationValues(AnnotationNode node) {
        if (node == null) {
            return new HashMap();
        }
        List<Object> values = node.values;
        if (values == null) {
            return new HashMap();
        }
        Map<String, Object> valueMap = new HashMap<>();
        int size = values.size();
        for (int index = 0; index < size; index += 2) {
            Object key = values.get(index);
            Object value = values.get(index + 1);
            valueMap.put(String.valueOf(key), value);
        }
        return valueMap;
    }
}

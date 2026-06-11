package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/GlStateTrackerTransformer.class */
public class GlStateTrackerTransformer implements IClassTransformer {
    private static final String GL_STATE_TRACKER_NAME = "net.labymod.core.laby3d.opengl.GlStateTracker";
    private static final String GL_STATE_TRACKER_INTERNAL_NAME = GL_STATE_TRACKER_NAME.replace('.', '/');
    private static final String OPENGL_PACKAGE_PREFIX = "org/lwjgl/opengl/";
    private final Set<String> methods = new HashSet();

    public GlStateTrackerTransformer() {
        URL resource = Launch.classLoader.getResource(GL_STATE_TRACKER_INTERNAL_NAME.concat(".class"));
        if (resource == null) {
            throw new IllegalStateException("Unable to find net.labymod.core.laby3d.opengl.GlStateTracker on the classpath!");
        }
        try {
            InputStream stream = resource.openStream();
            try {
                ClassReader reader = new ClassReader(stream);
                ClassNode node = new ClassNode();
                reader.accept(node, 0);
                for (MethodNode method : node.methods) {
                    if (!method.name.equals("<init>") && !method.name.equals("<clinit>")) {
                        this.methods.add(method.name + method.desc);
                    }
                }
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read net.labymod.core.laby3d.opengl.GlStateTracker", exception);
        }
    }

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.equals(GL_STATE_TRACKER_NAME)) {
            return classData;
        }
        ClassReader reader = new ClassReader(classData);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);
        for (MethodNode method : node.methods) {
            ListIterator it = method.instructions.iterator();
            while (it.hasNext()) {
                MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
                if (methodInsnNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode2 = methodInsnNode;
                    String key = methodInsnNode2.name + methodInsnNode2.desc;
                    if (methodInsnNode2.owner.startsWith(OPENGL_PACKAGE_PREFIX) && this.methods.contains(key)) {
                        methodInsnNode2.owner = GL_STATE_TRACKER_INTERNAL_NAME;
                    }
                }
            }
        }
        ClassWriter writer = ASMHelper.newClassWriter();
        node.accept(writer);
        return writer.toByteArray();
    }

    public int getPriority() {
        return super.getPriority() + 2000;
    }
}

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.core.laby3d.opengl.error.generated.GlErrorContextMethods;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/GlErrorContextTrackerTransformer.class */
public class GlErrorContextTrackerTransformer implements IClassTransformer {
    private static final String GL_ERROR_CONTEXT_PACKAGE = "net.labymod.core.laby3d.opengl.error";
    private static final String OPENGL_PACKAGE = "org.lwjgl.opengl";
    private static final String OPENGL_PACKAGE_PREFIX = "org/lwjgl/opengl/";
    private final Map<String, String> methods = new HashMap();

    public GlErrorContextTrackerTransformer() {
        this.methods.putAll(GlErrorContextMethods.buildMethods());
    }

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith(GL_ERROR_CONTEXT_PACKAGE) || name.startsWith(OPENGL_PACKAGE)) {
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
                    String owner = this.methods.get(key);
                    if (methodInsnNode2.owner.startsWith(OPENGL_PACKAGE_PREFIX) && owner != null) {
                        methodInsnNode2.owner = owner;
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

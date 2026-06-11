package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.function.Functional;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/OpenGLTransformer.class */
public class OpenGLTransformer implements IClassTransformer {
    private static final String GL11_ORIGINAL_CLASS = "org/lwjgl/opengl/GL11";
    private static final String GL30_ORIGINAL_CLASS = "org/lwjgl/opengl/GL30";
    private static final String DRAW_CALL_REPLACED_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/DrawCallTracker";
    private static final String DRAW_CALL_REPLACED_CLASS_REFLECTION = DRAW_CALL_REPLACED_CLASS.replace('/', '.');
    private static final String VAO_REPLACED_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/VertexArrayObjectTracker";
    private static final String VAO_REPLACED_CLASS_REFLECTION = VAO_REPLACED_CLASS.replace('/', '.');
    private static final Map<String, String> METHOD_MAPPINGS = (Map) Functional.of(new HashMap(), map -> {
        map.put("glBindVertexArray", "glBindVertexArray");
        map.put("glGenVertexArrays", "glGenVertexArrays");
        map.put("glDeleteVertexArrays", "glDeleteVertexArrays");
        map.put("glDrawElements", "glDrawElements");
    });

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("org/lwjgl/") || name.equals(DRAW_CALL_REPLACED_CLASS_REFLECTION) || name.equals(VAO_REPLACED_CLASS_REFLECTION)) {
            return classData;
        }
        ClassReader reader = new ClassReader(classData);
        ClassWriter writer = new ClassWriter(reader, 1);
        ClassVisitor visitor = new TransformerClassVisitor(writer);
        reader.accept(visitor, 8);
        return writer.toByteArray();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/OpenGLTransformer$TransformerClassVisitor.class */
    private static class TransformerClassVisitor extends ClassVisitor {
        protected TransformerClassVisitor(ClassVisitor classVisitor) {
            super(ASMHelper.ASM_VERSION, classVisitor);
        }

        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new TrackerMethodVisitor(this.api, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/lwjgl/OpenGLTransformer$TrackerMethodVisitor.class */
    private static class TrackerMethodVisitor extends MethodVisitor {
        protected TrackerMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (owner.equals(OpenGLTransformer.GL30_ORIGINAL_CLASS) && OpenGLTransformer.METHOD_MAPPINGS.containsKey(name)) {
                owner = OpenGLTransformer.VAO_REPLACED_CLASS;
                name = OpenGLTransformer.METHOD_MAPPINGS.get(name);
            }
            if (owner.equals(OpenGLTransformer.GL11_ORIGINAL_CLASS) && OpenGLTransformer.METHOD_MAPPINGS.containsKey(name)) {
                owner = OpenGLTransformer.DRAW_CALL_REPLACED_CLASS;
                name = OpenGLTransformer.METHOD_MAPPINGS.get(name);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}

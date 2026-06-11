package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.java;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/java/StringFormatTransformer.class */
public class StringFormatTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("net.minecraft.") || !name.contains(".")) {
            return replaceFormat(classData);
        }
        return classData;
    }

    private byte[] replaceFormat(byte[] classData) {
        ClassReader classReader = new ClassReader(classData);
        ClassWriter classWriter = ASMHelper.newClassWriter(classReader, false);
        ClassVisitor classVisitor = new ClassVisitor(this, ASMHelper.ASM_VERSION, classWriter) { // from class: net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.java.StringFormatTransformer.1
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new StringFormatMethodVisitor(this.api, methodVisitor);
            }
        };
        classReader.accept(classVisitor, 8);
        return classWriter.toByteArray();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/java/StringFormatTransformer$StringFormatMethodVisitor.class */
    private static class StringFormatMethodVisitor extends MethodVisitor {
        private static final String STRING_NAME = "java/lang/String";
        private static final String FORMAT_NAME = "format";
        private static final String FORMAT_DESC = "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;";

        protected StringFormatMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (opcode == 184 && owner.equals(STRING_NAME) && name.equals(FORMAT_NAME) && descriptor.equals(FORMAT_DESC)) {
                super.visitMethodInsn(184, "net/labymod/api/util/StringUtil", name, descriptor, false);
            } else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }
    }
}

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/guava/PreconditionsTransformer.class */
public class PreconditionsTransformer implements IClassTransformer {
    private static final String CHECK_ARGUMENT_NAME = "checkArgument";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_SINGLE_OBJECT = "(ZLjava/lang/String;Ljava/lang/Object;)V";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_TWO_OBJECTS = "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_THREE_OBJECTS = "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V";

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (!name.equals("com.google.common.base.Preconditions")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private void patch(ClassNode node) {
        boolean foundCheckArgumentSingleObject = false;
        boolean foundCheckArgumentTwoObjects = false;
        boolean foundCheckArgumentThreeObjects = false;
        for (MethodNode method : node.methods) {
            if (method.name.equals(CHECK_ARGUMENT_NAME) && method.desc.equals(CHECK_ARGUMENT_DESCRIPTOR_SINGLE_OBJECT)) {
                foundCheckArgumentSingleObject = true;
            } else if (method.name.equals(CHECK_ARGUMENT_NAME) && method.desc.equals(CHECK_ARGUMENT_DESCRIPTOR_TWO_OBJECTS)) {
                foundCheckArgumentTwoObjects = true;
            } else if (method.name.equals(CHECK_ARGUMENT_NAME) && method.desc.equals(CHECK_ARGUMENT_DESCRIPTOR_THREE_OBJECTS)) {
                foundCheckArgumentThreeObjects = true;
            }
        }
        if (!foundCheckArgumentSingleObject) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), CHECK_ARGUMENT_NAME, CHECK_ARGUMENT_DESCRIPTOR_SINGLE_OBJECT, null, null, methodNode -> {
                methodNode.visitCode();
                methodNode.visitVarInsn(21, 0);
                Label label = new Label();
                methodNode.visitJumpInsn(154, label);
                methodNode.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode.visitInsn(89);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitInsn(4);
                methodNode.visitTypeInsn(189, "java/lang/Object");
                methodNode.visitInsn(89);
                methodNode.visitInsn(3);
                methodNode.visitVarInsn(25, 2);
                methodNode.visitInsn(83);
                methodNode.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode.visitInsn(191);
                methodNode.visitLabel(label);
                methodNode.visitInsn(177);
                methodNode.visitEnd();
            }));
        }
        if (!foundCheckArgumentTwoObjects) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), CHECK_ARGUMENT_NAME, CHECK_ARGUMENT_DESCRIPTOR_TWO_OBJECTS, null, null, methodNode2 -> {
                methodNode2.visitCode();
                methodNode2.visitVarInsn(21, 0);
                Label label = new Label();
                methodNode2.visitJumpInsn(154, label);
                methodNode2.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode2.visitInsn(89);
                methodNode2.visitVarInsn(25, 1);
                methodNode2.visitInsn(5);
                methodNode2.visitTypeInsn(189, "java/lang/Object");
                methodNode2.visitInsn(89);
                methodNode2.visitInsn(3);
                methodNode2.visitVarInsn(25, 2);
                methodNode2.visitInsn(83);
                methodNode2.visitInsn(89);
                methodNode2.visitInsn(4);
                methodNode2.visitVarInsn(25, 3);
                methodNode2.visitInsn(83);
                methodNode2.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode2.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode2.visitInsn(191);
                methodNode2.visitLabel(label);
                methodNode2.visitInsn(177);
                methodNode2.visitEnd();
            }));
        }
        if (!foundCheckArgumentThreeObjects) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), CHECK_ARGUMENT_NAME, CHECK_ARGUMENT_DESCRIPTOR_THREE_OBJECTS, null, null, methodNode3 -> {
                methodNode3.visitCode();
                methodNode3.visitVarInsn(21, 0);
                Label label = new Label();
                methodNode3.visitJumpInsn(154, label);
                methodNode3.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode3.visitInsn(89);
                methodNode3.visitVarInsn(25, 1);
                methodNode3.visitInsn(5);
                methodNode3.visitTypeInsn(189, "java/lang/Object");
                methodNode3.visitInsn(89);
                methodNode3.visitInsn(3);
                methodNode3.visitVarInsn(25, 2);
                methodNode3.visitInsn(83);
                methodNode3.visitInsn(89);
                methodNode3.visitInsn(4);
                methodNode3.visitVarInsn(25, 3);
                methodNode3.visitInsn(83);
                methodNode3.visitInsn(89);
                methodNode3.visitInsn(5);
                methodNode3.visitVarInsn(25, 4);
                methodNode3.visitInsn(83);
                methodNode3.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode3.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode3.visitInsn(191);
                methodNode3.visitLabel(label);
                methodNode3.visitInsn(177);
                methodNode3.visitEnd();
            }));
        }
    }
}

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.gson;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/gson/TypeTokenTransformer.class */
public class TypeTokenTransformer implements IClassTransformer {
    private static final String PARAMETERIZED_NAME = "getParameterized";
    private static final String PARAMETERIZED_DESCRIPTOR = "(Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;";

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (!name.equals("com.google.gson.reflect.TypeToken")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private void patch(ClassNode node) {
        boolean foundParameterized = false;
        for (MethodNode method : node.methods) {
            if (method.name.equals(PARAMETERIZED_NAME) && method.desc.equals(PARAMETERIZED_DESCRIPTOR)) {
                foundParameterized = true;
            }
        }
        if (!foundParameterized) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), PARAMETERIZED_NAME, PARAMETERIZED_DESCRIPTOR, null, null, methodNode -> {
                methodNode.visitTypeInsn(187, "com/google/gson/reflect/TypeToken");
                methodNode.visitInsn(89);
                methodNode.visitInsn(1);
                methodNode.visitVarInsn(25, 0);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitMethodInsn(184, "com/google/gson/internal/$Gson$Types", "newParameterizedTypeWithOwner", "(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/ParameterizedType;", false);
                methodNode.visitMethodInsn(183, "com/google/gson/reflect/TypeToken", "<init>", "(Ljava/lang/reflect/Type;)V", false);
                methodNode.visitInsn(176);
            }));
        }
    }
}

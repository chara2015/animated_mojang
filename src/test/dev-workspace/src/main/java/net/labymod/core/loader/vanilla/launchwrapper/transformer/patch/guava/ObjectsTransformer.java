package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import java.util.Iterator;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/guava/ObjectsTransformer.class */
public class ObjectsTransformer implements IClassTransformer {
    private static final String FIRST_NON_NULL_NAME = "firstNonNull";
    private static final String FIRST_NON_NULL_DESCRIPTOR = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (!name.equals("com.google.common.base.Objects")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::addFirstNonNull);
    }

    private void addFirstNonNull(ClassNode classNode) {
        boolean found = false;
        Iterator it = classNode.methods.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MethodNode method = (MethodNode) it.next();
            if (method.name.equals(FIRST_NON_NULL_NAME) && method.desc.equals(FIRST_NON_NULL_DESCRIPTOR)) {
                found = true;
                break;
            }
        }
        if (found) {
            return;
        }
        MethodNode firstNonNullMethod = new MethodNode(9, FIRST_NON_NULL_NAME, FIRST_NON_NULL_DESCRIPTOR, "<T:Ljava/lang/Object;>(TT;TT;)TT;", (String[]) null);
        firstNonNullMethod.visitCode();
        Label label0 = new Label();
        firstNonNullMethod.visitLabel(label0);
        firstNonNullMethod.visitLineNumber(56, label0);
        firstNonNullMethod.visitVarInsn(25, 0);
        Label label1 = new Label();
        firstNonNullMethod.visitJumpInsn(198, label1);
        firstNonNullMethod.visitVarInsn(25, 0);
        Label label2 = new Label();
        firstNonNullMethod.visitJumpInsn(ColorUtil.LEGACY_COLOR_CHAR_PREFIX, label2);
        firstNonNullMethod.visitLabel(label1);
        firstNonNullMethod.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
        firstNonNullMethod.visitVarInsn(25, 1);
        firstNonNullMethod.visitMethodInsn(184, "com/google/common/base/Preconditions", "checkNotNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
        firstNonNullMethod.visitLabel(label2);
        firstNonNullMethod.visitFrame(4, 0, (Object[]) null, 1, new Object[]{"java/lang/Object"});
        firstNonNullMethod.visitInsn(176);
        Label label3 = new Label();
        firstNonNullMethod.visitLabel(label3);
        firstNonNullMethod.visitLocalVariable("first", "Ljava/lang/Object;", "TT;", label0, label3, 0);
        firstNonNullMethod.visitLocalVariable("second", "Ljava/lang/Object;", "TT;", label0, label3, 1);
        firstNonNullMethod.visitMaxs(1, 2);
        firstNonNullMethod.visitEnd();
        classNode.methods.add(firstNonNullMethod);
    }
}

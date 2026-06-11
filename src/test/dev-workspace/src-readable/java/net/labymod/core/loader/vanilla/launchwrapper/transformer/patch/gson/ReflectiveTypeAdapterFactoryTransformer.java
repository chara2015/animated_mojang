package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.gson;

import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/gson/ReflectiveTypeAdapterFactoryTransformer.class */
public class ReflectiveTypeAdapterFactoryTransformer implements IClassTransformer {
    private static final String NAME = "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1";
    private static final String GSON_2_11_0_NAME = "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$2";
    private static final String READ_NAME = "read";
    private static final String READ_DESC = "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V";
    private static final String READ_INTO_FIELD = "readIntoField";
    private static final String READ_INFO_FIELD_DESC = "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V";
    private static final Function<MethodNode, Boolean> READ_FUNCTION = method -> {
        return Boolean.valueOf(Objects.equals(method.name, READ_NAME) && Objects.equals(method.desc, "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V"));
    };
    private static final Function<MethodNode, Boolean> READ_INFO_FIELD_FUNCTION = methodNode -> {
        return Boolean.valueOf(Objects.equals(methodNode.name, READ_INTO_FIELD) && Objects.equals(methodNode.desc, "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V"));
    };

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (!Objects.equals(name, NAME) && !Objects.equals(name, GSON_2_11_0_NAME)) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private void patch(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            if (READ_FUNCTION.apply(method).booleanValue() || READ_INFO_FIELD_FUNCTION.apply(method).booleanValue()) {
                AbstractInsnNode lastGetFieldInstruction = null;
                ListIterator it = method.instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode instruction = (AbstractInsnNode) it.next();
                    if (instruction.getOpcode() == 182) {
                        lastGetFieldInstruction = instruction;
                    }
                }
                if (lastGetFieldInstruction != null) {
                    method.instructions.set(lastGetFieldInstruction, new MethodInsnNode(184, "net/labymod/core/util/gson/ReflectiveTypeAdapterFactoryExtension", "handleCustomField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"));
                }
            }
        }
    }
}

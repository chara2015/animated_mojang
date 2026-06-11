package net.labymod.core.loader.vanilla.launchwrapper.transformer;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.volt.asm.instruction.InstructionFinder;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/GameScreenTransformer.class */
@Deprecated(forRemoval = true, since = "4.1.12")
public class GameScreenTransformer implements IClassTransformer {
    private static final String GAME_SCREEN = "net/labymod/api/client/gui/screen/game/GameScreen";
    private static final String TAGGED_OBJECT = "net/labymod/api/tag/TaggedObject";
    private static final String TAGGED_OBJECT_DESCRIPTOR = "Lnet/labymod/api/tag/TaggedObject;";
    private static final String TAGGED_OBJECT_METHOD_NAME = "taggedObject";
    private static final String TAGGED_OBJECT_METHOD_DESCRIPTOR = "()Lnet/labymod/api/tag/TaggedObject;";
    private static final String GENERATED_FIELD_NAME = "dynamic_generated_taggedObject";
    private final InstructionFinder instructionFinder = new InstructionFinder();

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        return ASMHelper.transformClassData(classData, this::patch, false);
    }

    private void patch(ClassNode classNode) {
        if (!hasGameScreenInterface(classNode.interfaces)) {
            return;
        }
        boolean hasTaggedObjectMethod = false;
        Iterator it = classNode.methods.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MethodNode method = (MethodNode) it.next();
            if (method.name.equals(TAGGED_OBJECT_METHOD_NAME) && method.desc.equals(TAGGED_OBJECT_METHOD_DESCRIPTOR)) {
                hasTaggedObjectMethod = true;
                break;
            }
        }
        if (hasTaggedObjectMethod) {
            return;
        }
        classNode.fields.add(new FieldNode(18, GENERATED_FIELD_NAME, TAGGED_OBJECT_DESCRIPTOR, (String) null, (Object) null));
        for (MethodNode method2 : classNode.methods) {
            if (method2.name.equals("<init>")) {
                initializeTaggedObjectField(classNode, method2);
            }
        }
        MethodNode taggedObjectMethod = new MethodNode(1, TAGGED_OBJECT_METHOD_NAME, TAGGED_OBJECT_METHOD_DESCRIPTOR, (String) null, (String[]) null);
        InsnListBuilder builder = InsnListBuilder.create();
        builder.addVar(25, 0);
        builder.addField(180, classNode.name, GENERATED_FIELD_NAME, TAGGED_OBJECT_DESCRIPTOR);
        builder.addInstruction(176);
        taggedObjectMethod.instructions.add(builder.build());
        classNode.methods.add(taggedObjectMethod);
    }

    private void initializeTaggedObjectField(ClassNode classNode, MethodNode method) {
        InsnListBuilder builder = InsnListBuilder.create();
        builder.addVar(25, 0);
        builder.addType(187, TAGGED_OBJECT);
        builder.addInstruction(89);
        builder.addMethod(183, TAGGED_OBJECT, "<init>", "()V");
        builder.addField(181, classNode.name, GENERATED_FIELD_NAME, TAGGED_OBJECT_DESCRIPTOR);
        AbstractInsnNode lastReturnInstruction = this.instructionFinder.findLastReturnInstruction(method);
        method.instructions.insertBefore(lastReturnInstruction, builder.build());
    }

    private boolean hasGameScreenInterface(List<String> interfaces) {
        if (interfaces == null) {
            return false;
        }
        for (String anInterface : interfaces) {
            if (GAME_SCREEN.equals(anInterface)) {
                return true;
            }
        }
        return false;
    }
}

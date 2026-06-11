package net.labymod.api.volt.asm.instruction;

import java.util.ListIterator;
import java.util.function.BiConsumer;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/instruction/InstructionFinder.class */
public final class InstructionFinder {
    public AbstractInsnNode findFirstInstruction(MethodNode node, int opcode) {
        ListIterator it = node.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode instruction = (AbstractInsnNode) it.next();
            if (instruction.getOpcode() == opcode) {
                return instruction;
            }
        }
        return null;
    }

    public AbstractInsnNode findLastReturnInstruction(MethodNode methodNode) {
        AbstractInsnNode target = null;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode node = (AbstractInsnNode) it.next();
            if (OpcodesUtil.isReturnOpcode(node.getOpcode())) {
                target = node;
            }
        }
        return target;
    }

    public void findConstructor(ClassNode node, BiConsumer<MethodNode, Boolean> callback) {
        boolean found = false;
        for (MethodNode method : node.methods) {
            String methodName = ASMHelper.getNameWithDesc(method);
            if (methodName.startsWith("<init>")) {
                callback.accept(method, false);
                found = true;
            }
        }
        if (found) {
            return;
        }
        MethodNode newPublicConstructor = ASMHelper.createPublicConstructor();
        node.methods.add(newPublicConstructor);
        callback.accept(newPublicConstructor, true);
    }

    public void findStaticConstructor(ClassNode node, BiConsumer<MethodNode, Boolean> callback) {
        boolean found = false;
        for (MethodNode method : node.methods) {
            String methodName = ASMHelper.getNameWithDesc(method);
            if (methodName.startsWith("<clinit>")) {
                callback.accept(method, false);
                found = true;
            }
        }
        if (found) {
            return;
        }
        MethodNode newStaticConstructor = ASMHelper.createStaticConstructor();
        node.methods.add(newStaticConstructor);
        callback.accept(newStaticConstructor, true);
    }
}

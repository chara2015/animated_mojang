package net.labymod.api.volt.asm.tree;

import org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/asm/tree/VoltFieldInsnNode.class */
public class VoltFieldInsnNode {
    private final FieldInsnNode field;

    public VoltFieldInsnNode(int opcode, String owner, String name, String desc) {
        this(new FieldInsnNode(opcode, owner, name, desc));
    }

    public VoltFieldInsnNode(FieldInsnNode field) {
        this.field = field;
    }

    public FieldInsnNode getField() {
        return this.field;
    }

    public FieldInsnNode copy() {
        return new FieldInsnNode(this.field.getOpcode(), this.field.owner, this.field.name, this.field.desc);
    }
}

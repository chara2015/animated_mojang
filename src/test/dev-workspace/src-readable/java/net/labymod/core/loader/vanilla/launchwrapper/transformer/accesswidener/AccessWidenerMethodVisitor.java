package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import net.labymod.accesswidener.access.Access;
import net.labymod.accesswidener.access.accesses.MethodAccess;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerMethodVisitor.class */
public class AccessWidenerMethodVisitor extends MethodVisitor {
    private final AccessWidenerVisitor visitor;
    private final Access access;

    public AccessWidenerMethodVisitor(int api, MethodVisitor methodVisitor, AccessWidenerVisitor visitor, Access access) {
        super(api, methodVisitor);
        this.visitor = visitor;
        this.access = access;
    }

    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (opcode == 183 && owner.equals(this.visitor.className) && !name.equals("<init>") && this.access != MethodAccess.DEFAULT) {
            opcode = 182;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}

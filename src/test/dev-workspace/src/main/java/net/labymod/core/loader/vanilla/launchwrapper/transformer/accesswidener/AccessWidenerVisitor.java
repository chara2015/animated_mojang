package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import net.labymod.accesswidener.AccessSpecifier;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.accesswidener.AccessWidenerStorage;
import net.labymod.accesswidener.access.Access;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerVisitor.class */
public class AccessWidenerVisitor extends ClassVisitor {
    protected final AccessWidener accessWidener;
    protected String className;
    private int classAccess;

    private AccessWidenerVisitor(int api, ClassVisitor classVisitor, AccessWidener accessWidener) {
        super(api, classVisitor);
        this.accessWidener = accessWidener;
    }

    public static AccessWidenerVisitor of(int api, ClassVisitor visitor, AccessWidener widener) {
        return new AccessWidenerVisitor(api, visitor, widener);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        this.classAccess = access;
        super.visit(version, this.accessWidener.getClassAccess(name).apply(access, name, this.classAccess), name, signature, superName, interfaces);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        int newAccess = this.accessWidener.getClassAccess(name).apply(access, name, this.classAccess);
        if ((newAccess & 1) != 0) {
            newAccess |= 8;
        }
        super.visitInnerClass(name, outerName, innerName, newAccess);
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        AccessWidenerStorage methodAccess = this.accessWidener.getFieldAccess();
        Access newFieldAccess = methodAccess.getWildcardAccessFromOwner(this.className);
        if (newFieldAccess == null) {
            AccessSpecifier specifier = AccessSpecifier.of(this.className, name, descriptor);
            newFieldAccess = this.accessWidener.getFieldAccess(specifier);
        }
        return super.visitField(newFieldAccess.apply(access, name, this.classAccess), name, descriptor, signature, value);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        AccessWidenerStorage methodAccess = this.accessWidener.getMethodAccess();
        Access newMethodAccess = methodAccess.getWildcardAccessFromOwner(this.className);
        if (newMethodAccess == null) {
            AccessSpecifier specifier = AccessSpecifier.of(this.className, name, descriptor);
            newMethodAccess = this.accessWidener.getMethodAccess(specifier);
        }
        return new AccessWidenerMethodVisitor(this.api, super.visitMethod(newMethodAccess.apply(access, name, this.classAccess), name, descriptor, signature, exceptions), this, newMethodAccess);
    }
}

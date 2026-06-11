package net.labymod.api.volt.injector.insert;

import java.util.UUID;
import java.util.function.BiConsumer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.volt.asm.exception.VoltException;
import net.labymod.api.volt.asm.instruction.InstructionFinder;
import net.labymod.api.volt.asm.primitive.Primitive;
import net.labymod.api.volt.asm.primitive.Primitives;
import net.labymod.api.volt.asm.tree.VoltFieldInsnNode;
import net.labymod.api.volt.asm.tree.VoltInsnList;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import net.labymod.api.volt.callback.JumpStatement;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/injector/insert/InsertInjector.class */
public class InsertInjector extends Injector {
    private static final String FAST_CALLBACK_INFO_RETURNABLE_NAME = "net/labymod/api/volt/callback/InsertInfoReturnable";
    private final InstructionFinder instructionFinder;
    private Target.Extension invokeExtension;
    private final boolean cancellable;
    private final JumpStatement jumpStatement;

    public InsertInjector(InjectionInfo info, boolean cancellable, JumpStatement jumpStatement) {
        super(info, "@Insert");
        this.cancellable = cancellable;
        this.jumpStatement = jumpStatement;
        this.instructionFinder = new InstructionFinder();
    }

    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        this.invokeExtension = target.extendStack();
        this.invokeExtension.add(target.arguments.length);
        injectAt(target, this.info.getMethod(), node);
        this.invokeExtension.apply();
    }

    private void injectAt(Target target, MethodNode instruction, InjectionNodes.InjectionNode injectionNode) {
        ClassNode targetClassNode = target.classNode;
        MethodNode targetMethod = target.method;
        String methodNameWithDesc = instruction.name + instruction.desc;
        String invokeMethodDescriptor = methodNameWithDesc.substring(methodNameWithDesc.indexOf(40));
        Type type = ASMHelper.getLastParameter(invokeMethodDescriptor);
        if (type == null) {
            throw new VoltException("The method to be invoked is invalid. Missing parameters.");
        }
        String insertInfoName = type.getInternalName();
        String fieldName = generateCallbackInfoReturnableFieldName(instruction.name);
        targetClassNode.fields.add(0, new FieldNode(OpcodesUtil.privateAccess(this.isStatic), fieldName, "L" + insertInfoName + ";", (String) null, (Object) null));
        initializeGeneratedField(this.isStatic, targetClassNode, fieldName, insertInfoName, this.cancellable);
        VoltInsnList injection = new VoltInsnList(this.isStatic);
        injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
        Type[] argumentTypes = Type.getArgumentTypes(targetMethod.desc);
        int currentIndex = 0;
        for (Type argumentType : argumentTypes) {
            currentIndex++;
            VarInsnNode node = new VarInsnNode(argumentType.getOpcode(21), this.isStatic ? currentIndex - 1 : currentIndex);
            injection.add((AbstractInsnNode) node);
            String className = argumentType.getClassName();
            if (className.equals("double") || className.equals("long")) {
                currentIndex++;
            }
        }
        VoltFieldInsnNode generatedField = new VoltFieldInsnNode(OpcodesUtil.getField(this.isStatic), targetClassNode.name, fieldName, "L" + insertInfoName + ";");
        injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
        injection.add((AbstractInsnNode) generatedField.copy());
        injection.add((AbstractInsnNode) new MethodInsnNode(OpcodesUtil.invokeMethod(this.isStatic), targetClassNode.name, methodNameWithDesc.substring(0, methodNameWithDesc.indexOf(40)), invokeMethodDescriptor));
        if (this.jumpStatement == JumpStatement.BREAK || this.jumpStatement == JumpStatement.CONTINUE) {
            LabelNode continueLabel = findLabel(this.jumpStatement, injectionNode);
            if (continueLabel != null) {
                injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode) generatedField.copy());
                injection.add((AbstractInsnNode) new MethodInsnNode(182, insertInfoName, "isJumping", "()Z"));
                LabelNode label = new LabelNode();
                injection.add((AbstractInsnNode) new JumpInsnNode(153, label));
                injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode) generatedField.copy());
                injection.add((AbstractInsnNode) new MethodInsnNode(182, insertInfoName, "reset", "()V"));
                injection.add((AbstractInsnNode) new JumpInsnNode(ColorUtil.LEGACY_COLOR_CHAR_PREFIX, continueLabel));
                injection.add((AbstractInsnNode) label);
                injection.add((AbstractInsnNode) new FrameNode(3, 0, (Object[]) null, 1, (Object[]) null));
            }
        } else if (this.cancellable) {
            injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
            injection.add((AbstractInsnNode) generatedField.copy());
            injection.add((AbstractInsnNode) new MethodInsnNode(182, insertInfoName, "isCancelled", "()Z"));
            LabelNode label2 = new LabelNode();
            injection.add((AbstractInsnNode) new JumpInsnNode(153, label2));
            if (isReturnable(insertInfoName)) {
                injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode) generatedField.copy());
                injection.add((AbstractInsnNode) new MethodInsnNode(182, insertInfoName, "getReturnValue", "()Ljava/lang/Object;"));
            }
            injection.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
            injection.add((AbstractInsnNode) generatedField.copy());
            injection.add((AbstractInsnNode) new MethodInsnNode(182, insertInfoName, "reset", "()V"));
            Type returnType = Type.getReturnType(targetMethod.desc);
            if (Primitives.isPrimitive(returnType)) {
                primitiveCheckCast(injection, Primitives.getPrimitive(returnType));
            } else {
                String descriptor = returnType.getDescriptor();
                if (!descriptor.startsWith("L")) {
                    throw new VoltException("Illegal return type: " + String.valueOf(returnType));
                }
                objectCheckCast(injection, returnType.getInternalName());
            }
            injection.add((AbstractInsnNode) label2);
        }
        this.invokeExtension.add(injection.stackSize());
        invokeHandler(injection.getList(), injectionNode, targetMethod);
    }

    private LabelNode findLabel(JumpStatement jumpStatement, InjectionNodes.InjectionNode injectionNode) {
        LabelNode label;
        if (jumpStatement == JumpStatement.RETURN) {
            throw new IllegalArgumentException("Jump statement RETURN is not allowed");
        }
        AbstractInsnNode currentTarget = injectionNode.getCurrentTarget();
        if (jumpStatement == JumpStatement.CONTINUE) {
            JumpInsnNode jumpInsnNodeFindInstruction = ASMHelper.findInstruction(currentTarget, true, node -> {
                return node.getOpcode() == 167;
            });
            if (!(jumpInsnNodeFindInstruction instanceof JumpInsnNode)) {
                throw new IllegalStateException("Could not find a jump instruction");
            }
            JumpInsnNode jumpInsnNode = jumpInsnNodeFindInstruction;
            label = jumpInsnNode.label;
        } else {
            AbstractInsnNode frameInstruction = ASMHelper.findInstruction(currentTarget, false, node2 -> {
                return node2 instanceof FrameNode;
            });
            if (frameInstruction == null) {
                throw new IllegalStateException("Could not find any FrameNode");
            }
            JumpInsnNode jumpInsnNodeFindInstruction2 = ASMHelper.findInstruction(frameInstruction, true, node3 -> {
                return node3 instanceof JumpInsnNode;
            });
            if (!(jumpInsnNodeFindInstruction2 instanceof JumpInsnNode)) {
                throw new IllegalStateException("Could not find a jump instruction");
            }
            JumpInsnNode jumpInsnNode2 = jumpInsnNodeFindInstruction2;
            label = jumpInsnNode2.label;
        }
        return label;
    }

    @NotNull
    public AbstractInsnNode invokeHandler(@NotNull InsnList list, @NotNull InjectionNodes.InjectionNode node, @NotNull MethodNode handler) {
        int i;
        handler.instructions.insertBefore(node.getCurrentTarget(), list);
        boolean isPrivate = (handler.access & 2) != 0;
        if (this.isStatic) {
            i = 184;
        } else {
            i = isPrivate ? 183 : 182;
        }
        int invokeOpcode = i;
        MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
        list.add(insn);
        this.info.addCallbackInvocation(handler);
        return insn;
    }

    private void primitiveCheckCast(VoltInsnList injection, Primitive primitive) {
        if (!primitive.equals(Primitives.VOID)) {
            injection.add((AbstractInsnNode) new TypeInsnNode(192, primitive.getInternalName()));
            injection.add((AbstractInsnNode) new MethodInsnNode(182, primitive.getInternalName(), primitive.getMethodName(), primitive.getMethodDescriptor()));
        }
        injection.add((AbstractInsnNode) new InsnNode(primitive.getReturnOpcode()));
    }

    private void objectCheckCast(VoltInsnList injection, String internalName) {
        injection.add((AbstractInsnNode) new TypeInsnNode(192, internalName));
        injection.add((AbstractInsnNode) new InsnNode(176));
    }

    private boolean isReturnable(String internalName) {
        return internalName.equals(FAST_CALLBACK_INFO_RETURNABLE_NAME);
    }

    private void initializeGeneratedField(boolean isStatic, ClassNode classNode, String fieldName, String internalName, boolean cancellable) {
        if (isStatic) {
            this.instructionFinder.findStaticConstructor(classNode, initializeField(classNode, fieldName, internalName, true, cancellable));
        } else {
            this.instructionFinder.findConstructor(classNode, initializeField(classNode, fieldName, internalName, false, cancellable));
        }
    }

    private BiConsumer<MethodNode, Boolean> initializeField(ClassNode classNode, String fieldName, String internalName, boolean isStatic, boolean cancellable) {
        return (methodNode, created) -> {
            VoltInsnList list = new VoltInsnList(isStatic);
            list.addIfNotStatic((AbstractInsnNode) new VarInsnNode(25, 0));
            list.add((AbstractInsnNode) new TypeInsnNode(187, internalName));
            list.add((AbstractInsnNode) new InsnNode(89));
            list.add((AbstractInsnNode) new InsnNode(cancellable ? 4 : 3));
            list.add((AbstractInsnNode) new MethodInsnNode(183, internalName, "<init>", "(Z)V"));
            list.add((AbstractInsnNode) new FieldInsnNode(OpcodesUtil.putField(isStatic), classNode.name, fieldName, "L" + internalName + ";"));
            if (created.booleanValue()) {
                list.add((AbstractInsnNode) new InsnNode(177));
                methodNode.instructions.insert(list.getList());
            } else {
                AbstractInsnNode instruction = this.instructionFinder.findFirstInstruction(methodNode, 183);
                if (instruction == null) {
                    methodNode.instructions.insertBefore(this.instructionFinder.findLastReturnInstruction(methodNode), list.getList());
                    increaseConstructorMaxStackSize(methodNode, list.stackSize());
                    return;
                }
                methodNode.instructions.insert(instruction, list.getList());
            }
            increaseConstructorMaxStackSize(methodNode, list.stackSize());
        };
    }

    private void increaseConstructorMaxStackSize(MethodNode node, int count) {
        node.maxStack += count;
    }

    private String generateCallbackInfoReturnableFieldName(String name) {
        return name + UUID.randomUUID().toString().replace("-", "").substring(16);
    }
}

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/guava/GuavaJreTransformer.class */
public class GuavaJreTransformer implements IClassTransformer {
    private final Map<String, String> names;
    private final Map<String, List<String>> excludes = new HashMap();

    public GuavaJreTransformer() {
        addExclude("com/google/common/base/Objects", "equal(Ljava/lang/Object;Ljava/lang/Object;)Z");
        addExclude("com/google/common/base/Objects", "hashCode([Ljava/lang/Object;)I");
        this.names = new HashMap();
        this.names.put("com/google/common/base/Objects", "com/google/common/base/MoreObjects");
        this.names.put("com/google/common/base/Objects$ToStringHelper", "com/google/common/base/MoreObjects$ToStringHelper");
    }

    private void addExclude(String owner, String methodDescriptor) {
        this.excludes.computeIfAbsent(owner, l -> {
            return new ArrayList();
        }).add(methodDescriptor);
    }

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private void patch(ClassNode node) {
        for (MethodNode method : node.methods) {
            for (int i = 0; i < method.instructions.size(); i++) {
                MethodInsnNode methodInsnNode = method.instructions.get(i);
                if (methodInsnNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode2 = methodInsnNode;
                    String name = methodInsnNode2.name + methodInsnNode2.desc;
                    if (!shouldExclude(methodInsnNode2.owner, name)) {
                        for (Map.Entry<String, String> entry : this.names.entrySet()) {
                            if (methodInsnNode2.owner.equals(entry.getKey())) {
                                methodInsnNode2.owner = entry.getValue();
                                methodInsnNode2.desc = replace(methodInsnNode2.desc);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean shouldExclude(String owner, String name) {
        List<String> list = this.excludes.get(owner);
        if (list == null) {
            return false;
        }
        for (String exclude : list) {
            if (exclude.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private String replace(String value) {
        for (Map.Entry<String, String> entry : this.names.entrySet()) {
            if (value.contains(entry.getKey())) {
                return value.replace(entry.getKey(), entry.getValue());
            }
        }
        return value;
    }
}

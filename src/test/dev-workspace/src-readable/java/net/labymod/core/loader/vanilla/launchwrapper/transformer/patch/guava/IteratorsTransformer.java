package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/guava/IteratorsTransformer.class */
public class IteratorsTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte... classData) {
        if (!name.equals("com.google.common.collect.Iterators")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::handleClassNode);
    }

    private void handleClassNode(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            if (!method.name.equals("<init>") && !method.name.equals("<clinit>")) {
                method.access = 9;
            }
        }
    }
}

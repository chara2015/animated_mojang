package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.optifine;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/optifine/PlayerConfigurationsTransformer.class */
public class PlayerConfigurationsTransformer implements IClassTransformer {
    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null || !name.equals("net.optifine.player.PlayerConfigurations")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }

    private void patch(ClassNode node) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals("<init>") && !method.name.equals("<clinit>")) {
                Type returnType = Type.getReturnType(method.desc);
                method.instructions.clear();
                if (returnType == Type.VOID_TYPE) {
                    method.instructions.add(new InsnNode(177));
                } else {
                    method.instructions.add(new InsnNode(1));
                    method.instructions.add(new InsnNode(176));
                }
            }
        }
    }
}

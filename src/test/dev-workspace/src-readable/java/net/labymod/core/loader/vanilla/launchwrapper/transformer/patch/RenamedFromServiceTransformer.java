package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.core.rename.RenamedFromService;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/RenamedFromServiceTransformer.class */
public class RenamedFromServiceTransformer implements IClassTransformer {
    private final RenamedFromService renamedFromService = new RenamedFromService("labymod", Launch.classLoader, name -> {
        return Launch.classLoader.loadResource(name);
    });

    public RenamedFromServiceTransformer() {
        this.renamedFromService.load();
    }

    public byte[] transform(String name, String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        Remapper remapperMappings = this.renamedFromService.getRemapper();
        if (remapperMappings == null) {
            return classData;
        }
        ClassReader reader = new ClassReader(classData);
        ClassNode node = new ClassNode();
        ClassRemapper remapper = new ClassRemapper(node, remapperMappings);
        reader.accept(remapper, 0);
        ClassWriter writer = ASMHelper.newClassWriter(reader, false);
        node.accept(writer);
        return writer.toByteArray();
    }
}

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.compatibility;

import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.rename.ClassInfo;
import net.labymod.api.volt.rename.ClassProvider;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/patch/compatibility/WorldObjectTransformer.class */
public class WorldObjectTransformer implements IClassTransformer {
    private static final String WORLD_OBJECT_NAME = "net.labymod.api.client.world.object.WorldObject".replace('.', '/');
    private static final String RENDER_IN_WORLD_NAME = "renderInWorld";
    private static final String RENDER_IN_WORLD_DESCRIPTOR_OLD = "(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;FFFFZ)V";
    private static final String RENDER_IN_WORLD_DESCRIPTOR_NEW = "(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;DDDFZ)V";
    private final ClassProvider classProvider = ClassProvider.getSingleton(name -> {
        return Launch.classLoader.loadResource(name);
    });

    public byte[] transform(String name, String transformedName, byte[] classData) {
        if (classData == null) {
            return null;
        }
        ClassInfo classInfo = this.classProvider.getOrLoad(name);
        if (isInHierarchy(classInfo, WORLD_OBJECT_NAME)) {
            ClassReader reader = new ClassReader(classData);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);
            for (MethodNode method : classNode.methods) {
                if (method.name.equals(RENDER_IN_WORLD_NAME) && method.desc.equals(RENDER_IN_WORLD_DESCRIPTOR_OLD)) {
                    method.desc = RENDER_IN_WORLD_DESCRIPTOR_NEW;
                    method.maxLocals += 24;
                    System.out.println("[Compatibility Patcher] WorldObjectPatch was applied to " + name);
                }
            }
            ClassWriter writer = ASMHelper.newClassWriter();
            classNode.accept(writer);
            return writer.toByteArray();
        }
        return classData;
    }

    public boolean isInHierarchy(ClassInfo classInfo, String name) {
        ClassInfo superClass = classInfo.getSuperClass();
        if (superClass != null && (superClass.getName().equals(name) || isInHierarchy(superClass, name))) {
            return true;
        }
        for (ClassInfo anInterface : classInfo.getInterfaces()) {
            if (anInterface.getName().equals(name) || isInHierarchy(anInterface, name)) {
                return true;
            }
        }
        return false;
    }
}
